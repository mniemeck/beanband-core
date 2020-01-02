package org.beanband.model.music;

import org.beanband.model.song.Chord;
import org.beanband.model.song.Note;

/**
 * A {@code MusicAnnotation} attachable to a {@code Chord} that contains
 * information about the sore scale that can be inferred by the chord
 * information. In contrast to the {@code VoicingAnnotation}, the
 * {@code ScaleAnnotation} contains not absolute {@code NotePitch} objects,
 * designed to be playable on a specific instrument, but rather {@code Note}
 * objects, that denote the tones making up the scale matching the
 * {@code Chord}. In addition, the {@code ScaleAnnotation} provides some other,
 * scale-related information.
 * 
 * @author Michael Niemeck
 * @see VoicingAnnotation
 */
public class ScaleAnnotation extends MusicAnnotation<Chord> {
	
	// TODO Make more generic (enum like in VoicingAnnotation)

	private Note bassNote;

	/**
	 * Sets the bass note of this chord. Although defaulting to the root note, i.e.
	 * the first note of the scale, this can be overridden by
	 * {@code Chord.setBass(Note)} to any arbitrary {@code Note} that can be even
	 * outside the scale
	 * 
	 * @param bassNote The bass {@code Note} of the {@code Chord}
	 * @see Chord#setBass(Note)
	 */
	public void setBassNote(Note bassNote) {
		this.bassNote = bassNote;
	}

	/**
	 * Returns the bass {@code Note} of the {@code Chord}.
	 * 
	 * @return The bass {@code Note} of the {@code Chord}.
	 * @see #setBassNote(Note)
	 */
	public Note getBassNote() {
		return bassNote;
	}
	
	@Override
	public String toString() {
		String string = super.toString();
		string += "[BASS," + bassNote.toString() + "]";
		return string;
	}
}
