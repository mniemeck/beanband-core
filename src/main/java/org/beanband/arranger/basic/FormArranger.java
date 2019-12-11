package org.beanband.arranger.basic;

import javax.sound.midi.InvalidMidiDataException;

import org.beanband.arranger.Arranger;
import org.beanband.model.music.FormAnnotation;
import org.beanband.model.song.Bar;
import org.beanband.model.song.Song;

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
