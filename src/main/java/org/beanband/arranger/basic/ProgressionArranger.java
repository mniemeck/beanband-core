package org.beanband.arranger.basic;

import javax.sound.midi.InvalidMidiDataException;

import org.beanband.arranger.Arranger;
import org.beanband.model.music.ProgressionAnnotation;
import org.beanband.model.song.Bar;
import org.beanband.model.song.Chord;
import org.beanband.model.song.Song;

/**
 * The {@code ProgressionArranger} should analyze the song and annotate harmonic
 * features on a chord-level.
 * 
 * @author Michael Niemeck
 * @see ProgressionAnnotation
 *
 */
public class ProgressionArranger extends Arranger {

	@Override
	public void annotate(Song song) throws InvalidMidiDataException {
		// TODO Enhance to detect more sophisticated harmonic function information
		// (roman numeral annotations, ...)
		annotateChordChanges(song);
	}

	private void annotateChordChanges(Song song) {
		Chord previousChord = null;

		for (Bar bar : song.getBars()) {
			for (Chord chord : bar.getChords()) {
				if ((previousChord != null) && (!previousChord.equals(chord))) {
					chord.getOrCreateAnnotation(ProgressionAnnotation.class).setChangeBefore(true);
					previousChord.getOrCreateAnnotation(ProgressionAnnotation.class).setChangeAfter(true);
				}
				previousChord = chord;
			}
		}
	}

	@Override
	protected int getPriority() {
		return Integer.MAX_VALUE - 10;
	}

}
