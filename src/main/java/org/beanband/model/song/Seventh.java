package org.beanband.model.song;

/**
 * Enumeration of options for the Seventh of a chord.
 * 
 * @author Michael Niemeck
 * @see Chord
 */
public enum Seventh {
	/**
	 * Denotes a chord with major sixth in place of the seventh.
	 */
	SIXTH(9),
	/**
	 * Denotes a chord with a minor seventh.
	 */
	MIN(10),
	/**
	 * Denotes a chord with a major seventh.
	 */
	MAJ(11);

	private final int number;

	/**
	 * Constructs a new value and associates it with the specified number. The
	 * number indicates the number of semitone steps the chord component is above
	 * the root note.
	 * 
	 * @param number The numeric value of the component.
	 * @see Note
	 */
	private Seventh(int number) {
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
