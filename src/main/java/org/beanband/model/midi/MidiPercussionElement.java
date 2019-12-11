package org.beanband.model.midi;

import javax.sound.midi.InvalidMidiDataException;

public class MidiPercussionElement extends MidiPitchElement {

	public MidiPercussionElement(PercussionKey key, double start, int onVelocity, int offVelocity)
			throws InvalidMidiDataException {
		super(new PercussionPitch(key), start, 0.0, onVelocity, offVelocity);

	}

}
