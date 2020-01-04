package org.beanband.band.basic;

import java.util.List;

import javax.sound.midi.InvalidMidiDataException;

import org.beanband.band.Musician;
import org.beanband.model.midi.InstrumentPatch;
import org.beanband.model.midi.MidiProgramChangeElement;
import org.beanband.model.midi.NotePitch;
import org.beanband.model.music.VoicingAnnotation;
import org.beanband.model.music.VoicingAnnotation.VoicingType;
import org.beanband.model.song.Bar;
import org.beanband.model.song.Chord;

/**
 * The piano player of our showcase waltz band. It will play just the basic
 * voicing in a slightly varying rhythm based on the number of changes per bar.
 * 
 * @author Michael Niemeck
 *
 */
public class BasicThreeBeatPianoMusician extends Musician {
	private final RandomizedMusicianService service = new RandomizedMusicianService();

	@Override
	protected void createElements(Bar bar) throws InvalidMidiDataException {

		addElement(new MidiProgramChangeElement(InstrumentPatch.ACOUSTIC_GRAND_PIANO, 0.0));
		switch (bar.getChords().size()) {
		case 1:
			addFullBar(bar.getChords().get(0), 0.0);
			break;
		case 3:
			addThirdBar(bar.getChords().get(0), 0.0);
			addThirdBar(bar.getChords().get(1), 1.0 / 3.0);
			addThirdBar(bar.getChords().get(2), 2.0 / 3.0);
			break;
		}
	}

	private void addFullBar(Chord chord, double start) throws InvalidMidiDataException {
		List<NotePitch> voicing = chord.getAnnotationDefault(VoicingAnnotation.class)
				.getVoicing(VoicingType.PIANO_BASIC);
		addElement(service.createRandomizedElement(voicing, start + 1.0 / 3.0, 2.0 / 7.0, 90));
		addElement(service.createRandomizedElement(voicing, start + 5.0 / 6.0, 1.0 / 7.0, 70));
	}

	private void addThirdBar(Chord chord, double start) throws InvalidMidiDataException {
		List<NotePitch> voicing = chord.getAnnotationDefault(VoicingAnnotation.class)
				.getVoicing(VoicingType.PIANO_BASIC);
		addElement(service.createRandomizedElement(voicing, start, 2.0 / 7.0, 80));
	}

}
