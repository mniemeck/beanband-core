package org.beanband.arranger.basic;

import javax.sound.midi.InvalidMidiDataException;

import org.beanband.arranger.Arranger;
import org.beanband.model.song.Song;

/**
 * TODO Implement
 * 
 * @author Michael Niemeck
 */
public class PianoVoicingArranger extends Arranger {

	@Override
	public void annotate(Song song) throws InvalidMidiDataException {
		// Calculate one or more piano voicings.
	}
	
	@Override
	protected int getPriority() {
		return Integer.MAX_VALUE - 2;
	}

}
