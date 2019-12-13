package org.beanband.arranger.basic;

import javax.sound.midi.InvalidMidiDataException;

import org.beanband.arranger.Arranger;
import org.beanband.model.song.Song;

/**
 * TODO Implement ScaleArranger
 * 
 * @author Michael Niemeck
 */
public class ScaleArranger extends Arranger {

	@Override
	public void annotate(Song song) throws InvalidMidiDataException {
		/*
		  *** +ScaleAnnotation+ contains Notes, not NotePitches (cf. +VoicingAnnotation+)
		  *** Bass note (for BasslineArranger)
		  *** Chord function notes ordered by priority (for VoicingArrangers)
		  *** Scale notes starting from root note
		  *** Lead notes based on next bar
		  *** Possible algorithm:
		   **** Pre-define well-known scales
		   **** case or if/else search tree based on chord functions
		   **** This will ignore chord functions that have no impact on the scale, but rather act as tensions
		   **** Unfortunately it will also pick the wrong scale if not all chord functions are declared
		  */
	}

}
