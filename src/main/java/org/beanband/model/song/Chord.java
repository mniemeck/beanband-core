package org.beanband.model.song;

import org.beanband.model.music.Annotatable;
import org.beanband.model.music.MusicAnnotation;

/**
 * Class representing a chord. The class specifies all possible options of a
 * chord as properties. To indicate that a chord should not have a specific
 * option, set the corresponding property to {@code null}.
 * 
 * @author Michael Niemeck
 */
public class Chord extends Annotatable {
	private Note root;
	private Third third;
	private Fifth fifth;
	private Seventh seventh;
	private Ninth ninth;
	private Eleventh eleventh;
	private Thirteenth thirteenth;
	private Note bass;

	/**
	 * Gets the root {@code Note} of the chord.
	 * 
	 * @return The root {@code Note} of the chord, or {@code null} if it was not
	 *         specified.
	 */
	public Note getRoot() {
		return this.root;
	}

	/**
	 * Sets the root {@code Note} of the chord.
	 * 
	 * @param root The root {@code Note} of the chord, or {@code null} to leave it
	 *             unspecified.
	 */
	public void setRoot(Note root) {
		this.root = root;
	}

	/**
	 * Gets the {@code Third} of the chord.
	 * 
	 * @return The {@code Third} of the chord, or {@code null} if it was not
	 *         specified.
	 */
	public Third getThird() {
		return this.third;
	}

	/**
	 * Sets the {@code Third} of the chord.
	 * 
	 * @param third The {@code Third} of the chord, or {@code null} to leave it
	 *              unspecified.
	 */
	public void setThird(Third third) {
		this.third = third;
	}

	/**
	 * Gets the {@code Fifth} of the chord.
	 * 
	 * @return The {@code Fifth} of the chord, or {@code null} if it was not
	 *         specified.
	 */
	public Fifth getFifth() {
		return this.fifth;
	}

	/**
	 * Sets the {@code Fifth} of the chord.
	 * 
	 * @param fifth The {@code Fifth} of the chord, or {@code null} to leave it
	 *              unspecified.
	 */
	public void setFifth(Fifth fifth) {
		this.fifth = fifth;
	}

	/**
	 * Gets the {@code Seventh} of the chord.
	 * 
	 * @return The {@code Seventh} of the chord, or {@code null} if it was not
	 *         specified.
	 */
	public Seventh getSeventh() {
		return this.seventh;
	}

	/**
	 * Sets the {@code Seventh} of the chord.
	 * 
	 * @param seventh The {@code Seventh} of the chord, or {@code null} to leave it
	 *                unspecified.
	 */
	public void setSeventh(Seventh seventh) {
		this.seventh = seventh;
	}

	/**
	 * Gets the {@code Ninth} of the chord.
	 * 
	 * @return The {@code Ninth} of the chord, or {@code null} if it was not
	 *         specified.
	 */
	public Ninth getNinth() {
		return this.ninth;
	}

	/**
	 * Sets the {@code Ninth} of the chord.
	 * 
	 * @param ninth The {@code Ninth} of the chord, or {@code null} to leave it
	 *              unspecified.
	 */
	public void setNinth(Ninth ninth) {
		this.ninth = ninth;
	}

	/**
	 * Gets the {@code Eleventh} of the chord.
	 * 
	 * @return The {@code Eleventh} of the chord, or {@code null} if it was not
	 *         specified.
	 */
	public Eleventh getEleventh() {
		return this.eleventh;
	}

	/**
	 * Sets the {@code Eleventh} of the chord.
	 * 
	 * @param eleventh The {@code Eleventh} of the chord, or {@code null} to leave
	 *                 it unspecified.
	 */
	public void setEleventh(Eleventh eleventh) {
		this.eleventh = eleventh;
	}

	/**
	 * Gets the {@code Thirteenth} of the chord.
	 * 
	 * @return The {@code Thirteenth} of the chord, or {@code null} if it was not
	 *         specified.
	 */
	public Thirteenth getThirteenth() {
		return this.thirteenth;
	}

	/**
	 * Sets the {@code Thirteenth} of the chord.
	 * 
	 * @param thirteenth The {@code Thirteenth} of the chord, or {@code null} to
	 *                   leave it unspecified.
	 */
	public void setThirteenth(Thirteenth thirteenth) {
		this.thirteenth = thirteenth;
	}

	/**
	 * Gets the bass {@code Note} of the chord. Most Arrangers should require or
	 * even consider this option only if it differs from the root.
	 * 
	 * @return The bass {@code Note} of the chord, or {@code null} if it was not
	 *         specified.
	 */
	public Note getBass() {
		return this.bass;
	}

	/**
	 * Sets the bass {@code Note} of the chord. Most Arrangers should require or
	 * even consider this option only if it differs from the root.
	 * 
	 * @param bass The bass {@code Note} of the chord, or {@code null} to leave it
	 *             unspecified.
	 */
	public void setBass(Note bass) {
		this.bass = bass;
	}

	/**
	 * Returns a textual representation of this {@code Chord} in common chord
	 * notation.
	 * 
	 * @return A multi-line Unicode {@code String} representing this {@code Chord}.
	 * @see Note#toString()
	 */
	public String getSymbol() {
		String string = "";
		if (root == null) {
			string = "N.C.";
		} else {
			string = root.toString();
		}

		if ((getThird() == Third.MIN) && ((getFifth() != Fifth.DIM) || (getSeventh() == Seventh.MAJ))) {
			string += "m";
		}

		if ((getThird() != null) && (getFifth() == Fifth.AUG)) {
			string += "+";
		}

		if (getSeventh() != null) {
			if ((getThird() != Third.MIN) || (getFifth() != Fifth.DIM)) {
				if (getFifth() == null) {
					string += "add";
					if (getSeventh() == Seventh.MAJ) {
						string += "\u266f";
					}
				}
				if (getSeventh() == Seventh.SIXTH) {
					string += "6";
				} else if (((getSeventh() == Seventh.MIN) && (getNinth() == null)) || (getFifth() == null)) {
					string += "7";
				} else if (getSeventh() == Seventh.MAJ) {
					string += "\u0394";
				}
			}
		}

		if (getFifth() == Fifth.DIM) {
			if (getThird() == Third.MIN) {
				if (getSeventh() == Seventh.SIXTH) {
					string += "o7";
				} else if (getSeventh() == Seventh.MIN) {
					string += "\u00f8";
				} else if (getSeventh() == Seventh.MAJ) {
					string += "\u0394u\266d5";
				} else {
					string += "o";
				}
			} else {
				if (getThird() == null) {
					string += "add";
				}
				string += "\u266d5";
			}
		} else if ((getFifth() == Fifth.PERFECT) && (getThird() == null)) {
			if (getSeventh() != null) {
				string += "add";
			}
			string += "5";
		} else if (getFifth() == Fifth.AUG) {
			if (getThird() == null) {
				string += "add\u266f5";
			}
		}

		if (getNinth() != null) {
			if (getSeventh() == null) {
				string += "add";
			}
			if (getNinth() == Ninth.MIN) {
				string += "\u266d9";
			} else if (getNinth() == Ninth.AUG) {
				string += "\u266f9";
			} else if ((getSeventh() == null) || (getEleventh() == null)) {
				string += "9";
			}
		}

		if (getEleventh() != null) {
			if (getNinth() == null) {
				string += "add";
			}
			if (getEleventh() == Eleventh.DIM) {
				string += "\u266d11";
			} else if (getEleventh() == Eleventh.AUG) {
				string += "\u266f11";
			} else if ((getNinth() == null) || (getThirteenth() == null)) {
				string += "11";
			}
		}

		if (getThirteenth() != null) {
			if (getEleventh() == null) {
				string += "add";
			}
			if (getThirteenth() == Thirteenth.MIN) {
				string += "\u266d";
			} else if (getThirteenth() == Thirteenth.AUG) {
				string += "\u266f";
			}
			string += "13";
		}

		if (getThird() == Third.SUS2) {
			string += "sus2";
		} else if (getThird() == Third.SUS4) {
			string += "sus4";
		}

		if ((getBass() != null) && !getBass().equals(getRoot())) {
			string += "/" + getBass().toString();
		}

		return string;
	}

	@Override
	public boolean equals(Object obj) {
		if ((obj == null) || !(obj instanceof Chord)) {
			return false;
		}
		Chord chord = (Chord) obj;
		return equals(root, chord.root) && equals(third, chord.third) && equals(fifth, chord.fifth)
				&& equals(seventh, chord.seventh) && equals(ninth, chord.ninth) && equals(eleventh, chord.eleventh)
				&& equals(thirteenth, chord.thirteenth) && equals(bass, chord.bass);
	}

	private boolean equals(Enum<?> a, Enum<?> b) {
		if (a == null) {
			return b == null;
		}
		return a.equals(b);
	}

	/**
	 * Returns a textual representation of this {@code Chord} in common chord
	 * notation. In addition, all {@link MusicAnnotation} objects linked to the
	 * {@code Chord} are also appended to the {@code String}.
	 * 
	 * @return A multi-line Unicode {@code String} representing this {@code Chord}.
	 * @see Note#toString()
	 * @see Annotatable#toString()
	 */
	@Override
	public String toString() {
		return getSymbol() + super.toString();
	}

}
