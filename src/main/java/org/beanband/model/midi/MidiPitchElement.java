package org.beanband.model.midi;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.ShortMessage;

/**
 * Abstract super class for {@code MidiElement} objects that represent a pitched
 * event, i.e. an event associated with a specified pitch.
 * 
 * @author Michael Niemeck
 *
 */
public abstract class MidiPitchElement extends MidiElement {
	private final Pitch pitch;
	private final double start;
	private final double duration;
	private final int onVelocity;
	private final int offVelocity;

	/**
	 * Constructs a new {@code MidiPitchElement}.
	 * 
	 * @param pitch       The {@code Pitch} of this event.
	 * @param start       The start of the event in fractions of a whole bar. Can be below 0 or above 1 if necessary (use with caution).
	 * @param duration    The duration of the event in fractions of a whole bar. Can be above 1 if necessary (use with caution).
	 * @param onVelocity  The on-velocity to specify for this {@code MidiElement}.
	 *                    Must be in the range of 0-127.
	 * @param offVelocity The off-velocity to specify for this {@code MidiElement}.
	 *                    Must be in the range of 0-127.
	 * @throws InvalidMidiDataException When a parameter is outside the valid range.
	 */
	public MidiPitchElement(Pitch pitch, double start, double duration, int onVelocity, int offVelocity)
			throws InvalidMidiDataException {
		if ((onVelocity < 0) || (onVelocity > 127)) {
			throw new InvalidMidiDataException("The onVelocity must be between 0 and 127.");
		}
		if ((offVelocity < 0) || (offVelocity > 127)) {
			throw new InvalidMidiDataException("The offVelocity must be between 0 and 127.");
		}
		this.pitch = pitch;
		this.start = start;
		this.duration = duration;
		this.onVelocity = onVelocity;
		this.offVelocity = offVelocity;
	}

	@Override
	public Collection<MidiEvent> getMidiEvents(int channel, long ticksPerBar) throws InvalidMidiDataException {
		List<MidiEvent> midiEvents = new ArrayList<>();

		long onTicks = calculateTicks(start, ticksPerBar);
		MidiMessage onMessage = new ShortMessage(ShortMessage.NOTE_ON, channel, pitch.getPitch(), onVelocity);
		MidiEvent onEvent = new MidiEvent(onMessage, onTicks);
		midiEvents.add(onEvent);

		long offTicks = calculateTicks(duration, onTicks, ticksPerBar);
		MidiMessage offMessage = new ShortMessage(ShortMessage.NOTE_OFF, channel, pitch.getPitch(), offVelocity);
		MidiEvent offEvent = new MidiEvent(offMessage, offTicks);
		midiEvents.add(offEvent);

		return midiEvents;
	}

}
