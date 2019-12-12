package org.beanband.model.midi;

import javax.sound.midi.InvalidMidiDataException;

/**
 * A class representing a {@code MidiPitchElement} for a percussion instrument.
 * 
 * @author Michael Niemeck
 *
 */
public class MidiPercussionElement extends MidiPitchElement {
	/**
	 * @param key         The {@code PercussionKey} of this event.
	 * @param start       The start of the event in fractions of a whole bar. Can be
	 *                    below 0 or above 1 if necessary (use with caution).
	 * @param onVelocity  The on-velocity to specify for this {@code MidiElement}.
	 *                    Must be in the range of 0-127.
	 * @param offVelocity The off-velocity to specify for this {@code MidiElement}.
	 *                    Must be in the range of 0-127.
	 * @throws InvalidMidiDataException When a parameter is outside the valid range.
	 */
	public MidiPercussionElement(PercussionKey key, double start, int onVelocity, int offVelocity)
			throws InvalidMidiDataException {
		super(new PercussionPitch(key), start, 0.0, onVelocity, offVelocity);

	}

}
