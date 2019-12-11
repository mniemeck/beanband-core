package org.beanband.model.song;

/**
 * Enumeration of options for the Third of a chord.
 * 
 * @author Michael Niemeck
 * @see Chord
 */
public enum Third {
	/**
	 * Denotes a chord with a suspended second in place of the third.
	 */
	SUS2(2),
	/**
	 * Denotes a chord with a minor third.
	 */
	MIN(3),
	/**
	 * Denotes a chord with a major third.
	 */
	MAJ(4),
	/**
	 * Denotes a chord with a suspended fourth in place of the third.
	 */
	SUS4(5);

	private final int number;

	/**
	 * Constructs a new value and associates it with the specified number. The
	 * number indicates the number of semitone steps the chord component is above
	 * the root note.
	 * 
	 * @param number The numeric value of the component.
	 * @see Note
	 */
	private Third(int number) {
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
