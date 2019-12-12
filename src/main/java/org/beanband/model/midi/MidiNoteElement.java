package org.beanband.model.midi;

import javax.sound.midi.InvalidMidiDataException;


/**
 * A class representing a {@code MidiPitchElement} for a non-percussion instrument.
 * 
 * @author Michael Niemeck
 *
 */
public class MidiNoteElement extends MidiPitchElement {

	/**
	 * Constructs a new {@code MidiNoteElement}.
	 * 
	 * @param pitch       The {@code NotePitch} of this event.
	 * @param start       The start of the event in fractions of a whole bar. Can be
	 *                    below 0 or above 1 if necessary (use with caution).
	 * @param duration    The duration of the event in fractions of a whole bar. Can
	 *                    be above 1 if necessary (use with caution).
	 * @param onVelocity  The on-velocity to specify for this {@code MidiElement}.
	 *                    Must be in the range of 0-127.
	 * @param offVelocity The off-velocity to specify for this {@code MidiElement}.
	 *                    Must be in the range of 0-127.
	 * @throws InvalidMidiDataException When a parameter is outside the valid range.
	 */
	public MidiNoteElement(NotePitch pitch, double start, double duration, int onVelocity, int offVelocity)
			throws InvalidMidiDataException {
		super(pitch, start, duration, onVelocity, offVelocity);
	}

}
