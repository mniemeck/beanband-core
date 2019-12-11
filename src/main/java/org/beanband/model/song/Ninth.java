package org.beanband.model.song;

/**
 * Enumeration of options for the Ninth of a chord. Note that in contrast to
 * common chord notation, the addition of a {@code Ninth} to a {@code Chord}
 * does <em>not</em> imply the addition of a {@code Seventh}, so this option is
 * the equivalent of an <em>add9</em> from common chord notation.
 * 
 * @author Michael Niemeck
 * @see Chord
 */
public enum Ninth {
	/**
	 * Denotes a chord with a minor ninth.
	 */
	MIN(13),
	/**
	 * Denotes a chord with a major ninth.
	 */
	MAJ(14),
	/**
	 * Denotes a chord with an augmented ninth.
	 */
	AUG(15);

	private final int number;

	/**
	 * Constructs a new value and associates it with the specified number. The
	 * number indicates the number of semitone steps the chord component is above
	 * the root note.
	 * 
	 * @param number The numeric value of the component.
	 * @see Note
	 */
	private Ninth(int number) {
		this.number = number;
	}

	/**
	 * Gets the numeric value of the chord component. The number of a component
	 * indicates the number of semitone steps it is above the root note.
	 * 
	 * @return The numeric value of the component.
	 * @see Note#getNumber()
	 */
	public int getNumber() {
		return this.number;
	}

}
