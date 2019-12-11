package org.beanband.model.midi;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.ShortMessage;


public abstract class MidiPitchElement extends MidiElement {
	private final Pitch pitch;
	private final double start;
	private final double duration;
	private final int onVelocity;
	private final int offVelocity;
	
	/*
	 * start and duration not checked on purpose
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
