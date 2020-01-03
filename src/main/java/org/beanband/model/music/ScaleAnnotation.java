package org.beanband.model.music;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.beanband.model.song.Chord;
import org.beanband.model.song.Note;

/**
 * A {@code MusicAnnotation} attachable to a {@code Chord} that contains
 * information about the scale that can be inferred by the chord information. In
 * contrast to the {@code VoicingAnnotation}, the {@code ScaleAnnotation}
 * contains not absolute {@code NotePitch} objects, designed to be playable on a
 * specific instrument, but rather {@code Note} objects, that denote the tones
 * making up the scale matching the {@code Chord}. The {@code ScaleAnnotation}
 * provides different scales that can be used in various contexts.
 * 
 * @author Michael Niemeck
 * @see VoicingAnnotation
 */
public class ScaleAnnotation extends MusicAnnotation<Chord> {

	/**
	 * The different types of scales supported by the {@code ScaleAnnotation}. The
	 * {@code ScaleAnnotation} will only keep one voicing per {@code Type}.
	 * 
	 * @author Michael Niemeck
	 *
	 */
	public enum ScaleType {
		/**
		 * The bass note of this chord. Although defaulting to the root note, i.e. the
		 * first note of the scale, this can be overridden by
		 * {@code Chord.setBass(Note)} to any arbitrary {@code Note} that can be even
		 * outside the scale
		 */
		BASS_NOTE,
		/**
		 * The notes forming the chord. The notes are added by priority, i.e. on how
		 * important it is for them to be sounded by chord instruments.
		 */
		CHORD_NOTES,
		/**
		 * The notes forming the scale. The notes are added in ascending order starting
		 * from the chord's root.
		 */
		SCALE_NOTES;
	}

	private final Map<ScaleType, List<Note>> scales = new HashMap<>();

	/**
	 * Adds a note to the specified type of scale. The order in which the notes are
	 * added is kept, and has a specific semantic depending on the type. Only one
	 * scale per type is stored in the annotation.
	 * 
	 * @param type The {@code Type} of voicing to which to add the pitch.
	 * @param note The {@code Note} to add to the voicing.
	 */
	public void addNote(ScaleType type, Note note) {
		if (!scales.containsKey(type)) {
			scales.put(type, new ArrayList<>());
		}
		scales.get(type).add(note);
	}

	/**
	 * Returns the scale of the specified type (only one scale per {@code Type} is
	 * stored in the annotation.
	 * 
	 * @param type The {@code Type} for which the scale should be returned.
	 * @return A {@code List} containing the {@code Note} objects in the order in
	 *         which they were added to the scale of this {@code Type}. Returns an
	 *         empty {@code List}, if no notes have been added yet.
	 */
	public List<Note> getScale(ScaleType type) {
		if (scales.get(type) == null) {
			return Collections.emptyList();
		}
		return Collections.unmodifiableList(scales.get(type));
	}

	@Override
	public String toString() {
		String string = super.toString();
		for (ScaleType type : scales.keySet()) {
			string += "[" + type.toString();
			for (Note note : scales.get(type)) {
				string += "," + note.toString();
			}
			string += "]";

		}
		return string;
	}

}
