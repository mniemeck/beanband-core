package org.beanband.model.midi;

import javax.sound.midi.InvalidMidiDataException;


public class MidiNoteElement extends MidiPitchElement {

	public MidiNoteElement(NotePitch pitch, double start, double duration, int onVelocity, int offVelocity)
			throws InvalidMidiDataException {
		super(pitch, start, duration, onVelocity, offVelocity);
	}

}
