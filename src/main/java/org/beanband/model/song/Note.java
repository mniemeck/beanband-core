package org.beanband.model.song;

/**
 * Enumeration of note names.
 * 
 * @author Michael Niemeck
 * @see Chord
 */
public enum Note {
	/**
	 * The note "Cb". Enharmonic of {@code B}. Note that the value returned by
	 * {@link Note#getNumber} is also the same as that of B, and so is eleven
	 * semitones <em>above</em> {@code C} rather than one semitone below.
	 */
	Cb(11, "C\u266d"),
	/**
	 * The note "C"
	 */
	C(0, "C"),
	/**
	 * The note "C#". Enharmonic of {@code Db}.
	 */
	Cs(1, "C\u266f"),
	/**
	 * The note "Db". Enharmonic of {@code Cs}.
	 */
	Db(1, "D\u266d"),
	/**
	 * The note "D"
	 */
	D(2, "D"),
	/**
	 * The note "D#". Enharmonic of {@code Eb}.
	 */
	Ds(3, "D\u266f"),
	/**
	 * The note "Eb". Enharmonic of {@code Ds}.
	 */
	Eb(3, "E\u266d"),
	/**
	 * The note "E"
	 */
	E(4, "E"),
	/**
	 * The note "E#". Enharmonic of {@code F}.
	 */
	Es(5, "E\u266f"),
	/**
	 * The note "Fb". Enharmonic of {@code E}.
	 */
	Fb(4, "F\u266d"),
	/**
	 * The note "F"
	 */
	F(5, "F"),
	/**
	 * The note "F#". Enharmonic of {@code Gb}.
	 */
	Fs(6, "F\u266f"),
	/**
	 * The note "Gb". Enharmonic of {@code Fs}.
	 */
	Gb(6, "G\u266d"),
	/**
	 * The note "G"
	 */
	G(7, "G"),
	/**
	 * The note "G#". Enharmonic of {@code Ab}.
	 */
	Gs(8, "G\u266f"),
	/**
	 * The note "Ab". Enharmonic of {@code Gs}.
	 */
	Ab(8, "A\u266d"),
	/**
	 * The note "A"
	 */
	A(9, "A"),
	/**
	 * The note "A#". Enharmonic of {@code Bb}.
	 */
	As(10, "A\u266f"),
	/**
	 * The note "Bb". Enharmonic of {@code As}.
	 */
	Bb(10, "B\u266d"),
	/**
	 * The note "B"
	 */
	B(11, "B"),
	/**
	 * The note "B#". Enharmonic of {@code C}. Note that the value returned by
	 * {@link Note#getNumber} is also the same as that of {@code C}, and so is
	 * eleven semitones <em>below</em> {@code B} rather than one semitone above.
	 */
	Bs(0, "B\u266f");

	private final int number;
	private final String string;

	/**
	 * Constructs a new value and associates it with the specified number. The
	 * number of a note indicates the number of semitone steps it is above
	 * {@code C}.
	 * 
	 * @param number The numeric value of the note.
	 */
	private Note(int number, String string) {
		this.number = number;
		this.string = string;
	}

	/**
	 * Gets the numeric value of the note. The number of a note indicates the number
	 * of semitone steps it is above {@code C}.
	 * 
	 * @return The numeric value of the note.
	 */
	public int getNumber() {
		return this.number;
	}
	
	/**
	 * Returns a textual representation of the value of this {@code Note}.
	 * 
	 * @return A Unicode {@code String} representing this {@code Note}.
	 */
	@Override
	public String toString() {
		return string;
	}
}
