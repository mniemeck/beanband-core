package org.beanband.model.song;

/**
 * Enumeration of options for the Fifth of a chord.
 * 
 * @author Michael Niemeck
 * @see Chord
 */
public enum Fifth {
	/**
	 * Denotes a chord with a diminished fifth.
	 */
	DIM(6),
	/**
	 * Denotes a chord with a perfect fifth.
	 */
	PERFECT(7),
	/**
	 * Denotes a chord with an augmented fifth.
	 */
	AUG(8);

	private final int number;

	/**
	 * Constructs a new value and associates it with the specified number. The
	 * number indicates the number of semitone steps the chord component is above
	 * the root note.
	 * 
	 * @param number The numeric value of the component.
	 * @see Note
	 */
	private Fifth(int number) {
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
