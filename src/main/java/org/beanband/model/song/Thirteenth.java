package org.beanband.model.song;

/**
 * Enumeration of options for the Thirteenth of a chord. Note that in contrast
 * to common chord notation, the addition of a {@code Thirteenth} to a
 * {@code Chord} does <em>not</em> imply the addition of a {@code Seventh} and a
 * {@code Ninth}, so this option is the equivalent of an <em>add13</em> from
 * common chord notation. All other options must be explicitly set on a
 * {@code Chord}.
 * 
 * @author Michael Niemeck
 * @see Chord
 */
public enum Thirteenth {
	/**
	 * Denotes a chord with a minor thirteenth.
	 */
	MIN(20),
	/**
	 * Denotes a chord with a major thirteenth.
	 */
	MAJ(21),
	/**
	 * Denotes a chord with an augmented thirteenth.
	 */
	AUG(22);

	private final int number;

	/**
	 * Constructs a new value and associates it with the specified number. The
	 * number indicates the number of semitone steps the chord component is above
	 * the root note.
	 * 
	 * @param number The numeric value of the component.
	 * @see Note
	 */
	private Thirteenth(int number) {
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
