package org.beanband.arranger.basic;

import javax.sound.midi.InvalidMidiDataException;

import org.beanband.arranger.Arranger;
import org.beanband.model.music.ScaleAnnotation;
import org.beanband.model.song.Bar;
import org.beanband.model.song.Chord;
import org.beanband.model.song.Note;
import org.beanband.model.song.Song;

/**
 * Calculates Scale Notes and other functions based on a given {@code Chord}.
 * 
 * @author Michael Niemeck
 * @see ScaleAnnotation
 */
public class ScaleArranger extends Arranger {

	@Override
	public void annotate(Song song) throws InvalidMidiDataException {
		for (Bar bar : song.getBars()) {
			for (Chord chord : bar.getChords()) {
				Note bassNote = (chord.getBass() != null ? chord.getBass() : chord.getRoot());
				if (bassNote != null) {
					chord.getOrCreateAnnotation(ScaleAnnotation.class).setBassNote(bassNote);
				}
			}
		}
		// TODO Add chord function notes ordered by priority (for VoicingArrangers) (3,
		// 7, 9, 11, 13, 5 (except dim), 1). Maybe move to ChordArranger.

		// TODO Add scale notes starting from root note, constructed by functions.
		// Default tones based on 3 and 5, altered by chord functions

		// TODO Enhance to include various function (e.g. lead) notes based on context
	}

	@Override
	protected int getPriority() {
		return Integer.MAX_VALUE - 20;
	}
}
