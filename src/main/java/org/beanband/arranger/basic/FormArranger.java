package org.beanband.arranger.basic;

import javax.sound.midi.InvalidMidiDataException;

import org.beanband.arranger.Arranger;
import org.beanband.model.music.FormAnnotation;
import org.beanband.model.song.Bar;
import org.beanband.model.song.Song;

/**
 * The {@code Form Arranger} should analyze the song (e.g. by counting bars,
 * analyzing the chord changes, finding repetitions, maybe even doing a
 * functional analysis of the harmonies) and annotate formal features on a
 * bar-level. Currently, only the last bar of the song is annotated, and bars
 * with no chords are marked.
 * 
 * @author Michael Niemeck
 * @see FormAnnotation
 *
 */
public class FormArranger extends Arranger {

	@Override
	public void annotate(Song song) throws InvalidMidiDataException {
		for (Bar bar : song.getBars()) {
			if (bar.getChords().isEmpty() || (bar.getChords().get(0).getRoot() == null)) {
				bar.getOrCreateAnnotation(FormAnnotation.class).setNoChord();
			}
		}
		song.getBars().get(song.getBars().size() - 1).getOrCreateAnnotation(FormAnnotation.class).setIsLastBar();
	}

}
