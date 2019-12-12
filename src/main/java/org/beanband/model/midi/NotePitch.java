package org.beanband.model.midi;

import javax.sound.midi.InvalidMidiDataException;

import org.beanband.model.song.Note;

/**
 * Extension of the {@code Pitch} class, representing an absolute MIDI pitch by
 * a combination of a {@code Note} an a numerical value representing the octave.
 * 
 * @author Michael Niemeck
 */
public class NotePitch extends Pitch {

	private static final Note[] standardNotes = { Note.C, Note.Cs, Note.D, Note.Ds, Note.E, Note.F, Note.Fs, Note.G,
			Note.Gs, Note.A, Note.As, Note.B };

	private final Note note;
	private final int octave;

	/**
	 * Constructs an new {@code NotePitch} object.
	 * 
	 * @param note   The {@code Note} of this {@code Pitch}. Specifying {@code null}
	 *               or a Note above G when the octave is 12 will result in an
	 *               {@code InvalidMidiDataException}.
	 * @param octave The octave value of this {@code Pitch}. The valid range is from
	 *               0 to 12, although octave 12 only supports a {@code Note} up
	 *               unto G. Trying to set a value outside of the allowed range will
	 *               result in an {@code InvalidMidiDataException}.
	 * @throws InvalidMidiDataException When the resulting pitch would be outside
	 *                                  the valid MIDI range (0-127)
	 */
	public NotePitch(Note note, int octave) throws InvalidMidiDataException {
		if (note == null) {
			throw new InvalidMidiDataException("The Note must be specified to construct a NotePitch.");
		}
		if ((octave < 0) || (octave > 12)) {
			throw new InvalidMidiDataException("The octave must be between 0 and 12 to construct a NotePitch.");
		}
		if ((octave == 12) && (note.getNumber() > 7)) {
			throw new InvalidMidiDataException("Notes above G12 (127) are not supported by the MIDI standard.");
		}

		this.note = note;
		this.octave = octave;
	}

	/**
	 * Constructs a new {@code NotePitch} object based on a pitch value. The
	 * {@code Note} and octave value are calculated. Note that for enharmonics,
	 * always the sharp {@code Note} will be chosen.
	 * 
	 * @param pitch The pitch value.
	 * @throws InvalidMidiDataException When the pitch value is outside the allowed
	 *                                  range (0-127).
	 */
	public NotePitch(int pitch) throws InvalidMidiDataException {
		if ((pitch < 0) && (pitch > 127)) {
			throw new InvalidMidiDataException(
					"Notes below C0 (0) or above G12 (127) are not supported by the MIDI standard.");
		}
		this.note = standardNotes[pitch % 12];
		this.octave = pitch / 12;
	}

	@Override
	public int getPitch() {
		return this.note.getNumber() + (this.octave * 12);
	}

	/**
	 * Returns the {@code Note} value defining this {@code NotePitch}.
	 * 
	 * @return The {@code Note} value defining this {@code NotePitch}.
	 */
	public Note getNote() {
		return note;
	}

	/**
	 * Returns the octave value defining this {@code NotePitch}.
	 * 
	 * @return The octave value defining this {@code NotePitch}.
	 */
	public int getOctave() {
		return octave;
	}

}
