package org.beanband.model.song;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.beanband.model.music.Annotatable;

/**
 * {@code SongElement} representing one bar on music within a {@code Song}.
 * Aggregates zero to many {@code Chord} objects.
 * 
 * @author Michael Niemeck
 */
public class Bar extends SongElement {
	private final List<Chord> chords = new ArrayList<>();

	/**
	 * Adds a new {@code Chord} object to this {@code Bar}. Chords retain the order
	 * in which they were added tp a bar. There are no methods to (re-)move them
	 * after they have been added.
	 * 
	 * @return The newly constructed {@code Chord}
	 */
	public Chord addChord() {
		Chord newChord = new Chord();

		chords.add(newChord);

		return newChord;
	}

	/**
	 * Returns all {@code Chord} objects in the order in which they were added to
	 * this {@code Bar}.
	 * 
	 * @return An unmodifiable {@code List} of all {@code Chord} objects in the
	 *         correct order.
	 */
	public List<Chord> getChords() {
		return Collections.unmodifiableList(chords);
	}

	/**
	 * Returns a textual representation of this {@code Bar} including all chords as
	 * well as any attached {@code MusicAnnotation} objects.
	 * 
	 * @return A multi-line Unicode {@code String} representing this {@code Bar}.
	 * @see Chord#toString()
	 * @see Annotatable#toString()
	 */
	@Override
	public String toString() {
		String string = "Bar (" + chords.size() + " chord" + (chords.size() > 1 ? "s" : "") + ")";
		string += super.toString();
		for (Chord chord : chords) {
			string += "\n  " + chord.toString().replaceAll("(\\v+)(\\h+)", "$1$2$2");
		}
		return string;
	}
}
