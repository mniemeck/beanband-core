package org.beanband.band;

import java.util.Collection;

import javax.sound.midi.InvalidMidiDataException;

import org.beanband.model.midi.MidiElement;

/**
 * A {@code LeadMusician}. This musician has the additional capability of
 * performing a <em>count-in</em> before the first bar. A typical example is a
 * drummer counting one bar of quarter beats by banging the drumsticks together.
 * 
 * @author Michael Niemeck
 *
 */
public interface LeadMusician {

	/**
	 * The music making up the <em>count-in</em> before the first bar. They will be
	 * put at the beginning of the generated {@code Sequence} and should stay within
	 * the limit specified by {@code getCountInBeats}.
	 * 
	 * @return A {@code Collection} of {@code MidiElement} objects representing the
	 *         count-in.
	 * @throws InvalidMidiDataException When the creation of the {@code MidiElement}
	 *                                  runs into an illegal state.
	 */
	Collection<MidiElement> getCountIn() throws InvalidMidiDataException;

	/**
	 * Returns the length of the count-in in <em>beats</em>
	 * 
	 * @return The length of the count-in in beats. Although this is typically an
	 *         integer, it is also possible to return any {@code double} value.
	 */
	double getCountInBeats();

}
