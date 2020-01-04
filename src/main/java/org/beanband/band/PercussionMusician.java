package org.beanband.band;

import java.util.Collection;

import javax.sound.midi.InvalidMidiDataException;

import org.beanband.model.midi.MidiElement;

/**
 * A {@code PercussionMusician} if a {@code Musician} that will play on the
 * provided drumset, therefore all {@code MidiProgramChangeElement} created by
 * them will be ignored by the {@code Engineer}. In addition every
 * {@code PercussionMusician} has the capability of performing a
 * <em>count-in</em> before the first bar. A typical example is counting one bar
 * of quarter beats by banging the drumsticks together.
 * 
 * @author Michael Niemeck
 *
 */
public abstract class PercussionMusician extends Musician {

	/**
	 * The music making up the <em>count-in</em> before the first bar. They will be
	 * put at the beginning of the generated {@code Sequence} and should stay within
	 * the limit specified by {@code getCountInBeats}. Note that the count-in will
	 * be generated as one {@code MidiBar} of the specified length, so duration
	 * fractions are always relative to the entire count-in.
	 * 
	 * @return A {@code Collection} of {@code MidiElement} objects representing the
	 *         count-in.
	 * @throws InvalidMidiDataException When the creation of the {@code MidiElement}
	 *                                  runs into an illegal state.
	 */
	public abstract Collection<MidiElement> getCountIn() throws InvalidMidiDataException;

	/**
	 * Returns the length of the count-in bar in <em>beats</em>
	 * 
	 * @return The length of the count-in in beats. Although this is typically an
	 *         integer, it is also possible to return any {@code double} value.
	 */
	public abstract double getCountInBeats();
}
