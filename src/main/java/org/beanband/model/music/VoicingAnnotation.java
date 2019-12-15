package org.beanband.model.music;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.beanband.model.midi.NotePitch;
import org.beanband.model.song.Chord;

/**
 * A {@code MusicAnnotation} attachable to {@code Chord} objects that contains
 * <em>voicings</em> calculated for each chord. A <em>voicing</em> is a set of
 * notes that should be played to sound the chord denoted by the chord symbol.
 * Voicings contains absolute pitches, so they are not only instrument-specific
 * (the pitches available, as well as the pitches that can sound at the same
 * time are very specific to every instrument), but also subject to very
 * sophisticated rules about the transition from one voicing to the next. This
 * means that voicings for the exact same chord symbol not only vary based on
 * instrument but also depending on context.
 * <p>
 * Different voicing calculations for the same instrument are also possible, for
 * example a <em>closed</em> voicing and an <em>open voicing</em>, but be
 * careful not to differentiate too much according to style. <em>Different
 * styles are the domain of the {@code Band}, not of the {@code Arranger}</em>.
 * 
 * @author Michael Niemeck
 */
public class VoicingAnnotation extends MusicAnnotation<Chord> {

	/**
	 * The different types of voicings supported by the {@code VoicingAnnotation}.
	 * The {@code VoicingAnnotation} will only keep one voicing per {@code Type}.
	 * 
	 * @author Michael Niemeck
	 *
	 */
	public enum Type {
		/**
		 * A very basic voicing for (double-) bass. Currently only contains the bass
		 * note.
		 */
		BASS_BASIC,
		/**
		 * A basic voicing for guitar. Contains up to six pitches.
		 */
		GUITAR_BASIC;
	}

	private final Map<Type, List<NotePitch>> voicings = new HashMap<>();

	/**
	 * Adds a pitch to the specified type of voicing. The order in which the pitches
	 * are added is kept. Only one voicing prt type is stored in the annotation.
	 * 
	 * @param type      The {@code Type} of voicing to which to add the pitch.
	 * @param notePitch The {@code NotePitch} to add to the voicing.
	 */
	public void addNotePitch(Type type, NotePitch notePitch) {
		if (!voicings.containsKey(type)) {
			voicings.put(type, new ArrayList<>());
		}
		voicings.get(type).add(notePitch);
	}

	/**
	 * Returns the voicing of the specified type (only one voicing per {@code Type}
	 * is stored in the annotation.
	 * 
	 * @param type The {@code Type} for which the voicing should be returned.
	 * @return A {@code List} containing the {@code NotePitch} objects in the order
	 *         in which they were added to the voicing of this {@code Type}. Returns
	 *         an empty {@code List}, if no pitches have been added yet.
	 */
	public List<NotePitch> getVoicing(Type type) {
		if (voicings.get(type) == null) {
			return Collections.emptyList();
		}
		return Collections.unmodifiableList(voicings.get(type));
	}

	@Override
	public String toString() {
		String string = super.toString();
		for (Type type : voicings.keySet()) {
			string += "[" + type.toString();
			for (NotePitch notePitch : voicings.get(type)) {
				string += "," + notePitch.getNote().toString() + notePitch.getOctave();
			}
			string += "]";

		}
		return string;
	}

}
