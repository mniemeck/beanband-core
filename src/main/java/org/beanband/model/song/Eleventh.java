package org.beanband.model.song;

/**
 * Enumeration of options for the Eleventh of a chord. Note that in contrast to
 * common chord notation, the addition of an {@code Eleventh} to a {@code Chord}
 * does <em>not</em> imply the addition of a {@code Seventh} and a
 * {@code Ninth}, so this option is the equivalent of an <em>add11</em> from
 * common chord notation. All other options must be explicitly set on a
 * {@code Chord}.
 * 
 * @author Michael Niemeck
 * @see Chord
 */
public enum Eleventh {
	/**
	 * Denotes a chord with a diminished eleventh.
	 */
	DIM(16),
	/**
	 * Denotes a chord with a perfect eleventh.
	 */
	PERFECT(17),
	/**
	 * Denotes a chord with an augmented eleventh.
	 */
	AUG(18);

	private final int number;

	/**
	 * Constructs a new value and associates it with the specified number. The
	 * number indicates the number of semitone steps the chord component is above
	 * the root note.
	 * 
	 * @param number The numeric value of the component.
	 * @see Note
	 */
	private Eleventh(int number) {
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
