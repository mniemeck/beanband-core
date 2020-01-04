package org.beanband.band;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import javax.sound.midi.InvalidMidiDataException;

import org.beanband.model.midi.MidiElement;
import org.beanband.model.song.Bar;

/**
 * A group of {@code Musician} objects is needed to form a {@code Band}. Each
 * {@code Musician} is responsible for creating music, represented by
 * {@code MidiElement} objects, based on the information from the Song Model and
 * the Music Model, presented to the {@code Musician} in the form of {@code Bar}
 * objects. The music is usually performed on a single instrument.
 * 
 * @author Michael Niemeck
 *
 */
public abstract class Musician {

	private final Collection<MidiElement> elements = new ArrayList<>();

	/**
	 * Returns the music rendered for one {@code Bar} of the Song Model.
	 * 
	 * @param bar The {@code Bar} for which to render the music.
	 * @return A {@code Collection} of {@code MidiElement} objects. Note that the
	 *         {@code MidiEvent} objects wrapped inside the {@code MidiEvent} are
	 *         technically not confined to this bar of music, but usually should be.
	 * @throws InvalidMidiDataException When the creation of the {@code MidiElement}
	 *                                  runs into an illegal state.
	 */
	public final Collection<MidiElement> play(Bar bar) throws InvalidMidiDataException {
		elements.clear();
		createElements(bar);
		return Collections.unmodifiableCollection(elements);
	}

	protected abstract void createElements(Bar bar) throws InvalidMidiDataException;

	protected final void addElement(MidiElement element) {
		elements.add(element);
	}
	
	protected final void addElement(Collection<MidiElement> element) {
		elements.addAll(element);
	}
}
