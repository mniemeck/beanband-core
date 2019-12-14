package org.beanband.model.midi;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.ShortMessage;

/**
 * A {@code MidiElement} encapsulating a MIDI program change event. This sets an
 * {@code InstrumentPatch} on the specified channel. Note that this element can
 * be safely added multiple times to a channel. The {@code Engineer} will filter
 * out any duplicate calls to the same instrument, leaving only the actual
 * changes in the final {@code Sequence}
 * 
 * @author Michael Niemeck
 */
public class MidiProgramChangeElement extends MidiElement {

	private final InstrumentPatch instrumentPatch;
	private final double start;

	/**
	 * Constructs a new {@code MidiProgramChangeElement}.
	 * 
	 * @param instrumentPatch The {@code InstrumentPatch} of this event.
	 * @param start           The start of the event in fractions of a whole bar.
	 *                        Can be below 0 or above 1 if necessary (use with
	 *                        caution).
	 */
	public MidiProgramChangeElement(InstrumentPatch instrumentPatch, double start) {
		this.instrumentPatch = instrumentPatch;
		this.start = start;
	}

	@Override
	public Collection<MidiEvent> getMidiEvents(int channel, long ticksPerBar) throws InvalidMidiDataException {
		List<MidiEvent> midiEvents = new ArrayList<>();

		long ticks = calculateTicks(start, ticksPerBar);
		MidiMessage midiMessage = new ShortMessage(ShortMessage.PROGRAM_CHANGE, channel, instrumentPatch.getNumber(),
				0);
		MidiEvent midiEvent = new MidiEvent(midiMessage, ticks);
		midiEvents.add(midiEvent);

		return midiEvents;
	}

}
