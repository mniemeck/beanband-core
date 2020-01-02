package org.beanband.arranger.basic;

import java.util.ArrayList;
import java.util.List;

import javax.sound.midi.InvalidMidiDataException;

import org.beanband.arranger.Arranger;
import org.beanband.model.music.FormAnnotation;
import org.beanband.model.music.ProgressionAnnotation;
import org.beanband.model.song.Bar;
import org.beanband.model.song.Chord;
import org.beanband.model.song.Song;

/**
 * The {@code FormArranger} should analyze the song (e.g. by counting bars,
 * analyzing the chord changes, finding repetitions, maybe even doing a
 * functional analysis of the harmonies) and annotate formal features on a
 * bar-level.
 * 
 * @author Michael Niemeck
 * @see FormAnnotation
 *
 */
public class FormArranger extends Arranger {

	@Override
	public void annotate(Song song) throws InvalidMidiDataException {
		// TODO Enhance to actually detect formal features of the leadsheet (intros,
		// chorusses, bridges, breaks, etc.)

		annotateChordChanges(song);
		annotateLastBar(song);
	}

	private void annotateChordChanges(Song song) {
		int numBars = 0;
		int numChanges = 0;
		for (Bar bar : song.getBars()) {
			if (isNoChord(bar)) {
				bar.getOrCreateAnnotation(FormAnnotation.class).setNoChord(true);
			} else {
				numBars++;
				for (Chord chord : bar.getChords()) {
					if (isChangeAfter(chord)) {
						numChanges++;
					}
				}
			}
		}
		numChanges++;
		double averageChanges = Math.rint((double) numChanges / (double) numBars);
		List<Bar> barsToAnnotate = new ArrayList<>();
		for (Bar bar : song.getBars()) {
			if ((bar.getChords().size() != 1) || isChangeBefore(bar.getChords().get(0))) {
				for (Bar barToAnnotate : barsToAnnotate) {
					barToAnnotate.getOrCreateAnnotation(FormAnnotation.class)
					.setChangeRatio(barToAnnotate.getChords().size() / (averageChanges * barsToAnnotate.size()));
				}
				barsToAnnotate.clear();
			}
			barsToAnnotate.add(bar);
		}
		for (Bar barToAnnotate : barsToAnnotate) {
			barToAnnotate.getOrCreateAnnotation(FormAnnotation.class)
			.setChangeRatio(barToAnnotate.getChords().size() / (averageChanges * barsToAnnotate.size()));
		}
	}

	private boolean isNoChord(Bar bar) {
		for (Chord chord : bar.getChords()) {
			if (chord.getRoot() != null) {
				return false;
			}
		}
		return true;
	}
	
	private boolean isChangeAfter(Chord chord) {
		ProgressionAnnotation progressionAnnotation = chord.getAnnotation(ProgressionAnnotation.class);
		if (progressionAnnotation == null) {
			return false;
		}
		return progressionAnnotation.isChangeAfter();
	}

	private boolean isChangeBefore(Chord chord) {
		ProgressionAnnotation progressionAnnotation = chord.getAnnotation(ProgressionAnnotation.class);
		if (progressionAnnotation == null) {
			return false;
		}
		return progressionAnnotation.isChangeBefore();
	}

	private void annotateLastBar(Song song) {
		song.getBars().get(song.getBars().size() - 1).getOrCreateAnnotation(FormAnnotation.class).setLastBar(true);
	}

	@Override
	protected int getPriority() {
		return Integer.MAX_VALUE - 20;
	}

}
