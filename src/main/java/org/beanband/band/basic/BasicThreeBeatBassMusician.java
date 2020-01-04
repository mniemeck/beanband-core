package org.beanband.band.basic;

import java.util.List;

import javax.sound.midi.InvalidMidiDataException;

import org.beanband.band.Musician;
import org.beanband.model.midi.InstrumentPatch;
import org.beanband.model.midi.MidiProgramChangeElement;
import org.beanband.model.midi.NotePitch;
import org.beanband.model.music.ProgressionAnnotation;
import org.beanband.model.music.VoicingAnnotation;
import org.beanband.model.music.VoicingAnnotation.VoicingType;
import org.beanband.model.song.Bar;
import org.beanband.model.song.Chord;

/**
 * The bass player of our showcase band. It will play just the bass note in a
 * slightly varying rhythm based on the number of changes per bar.
 * 
 * @author Michael Niemeck
 *
 */
public class BasicThreeBeatBassMusician extends Musician {

	private final RandomizedMusicianService service = new RandomizedMusicianService();

	@Override
	protected void createElements(Bar bar) throws InvalidMidiDataException {
		addElement(new MidiProgramChangeElement(InstrumentPatch.ACOUSTIC_BASS, 0.0));
		switch (bar.getChords().size()) {
		case 1:
			if (bar.getChords().get(0).getAnnotationDefault(ProgressionAnnotation.class).isChangeAfter()) {

				addStrongBar(bar.getChords().get(0), 0.0);
			} else {
				addWeakBar(bar.getChords().get(0), 0.0);
			}
			break;
		case 3:
			addThirdBar(bar.getChords().get(0), 0.0);
			addThirdBar(bar.getChords().get(1), 1.0 / 3.0);
			addThirdBar(bar.getChords().get(2), 2.0 / 3.0);
			break;
		}
	}

	private void addStrongBar(Chord chord, double start) throws InvalidMidiDataException {
		List<NotePitch> voicing = chord.getAnnotationDefault(VoicingAnnotation.class)
				.getVoicing(VoicingType.BASS_BASIC);
		if (!voicing.isEmpty()) {
			addElement(service.createRandomizedElement(voicing.get(0), start, 4.0 / 7.0, 120));
			addElement(service.createRandomizedElement(voicing.get(0), start + 2.0 / 3.0, 2.0 / 7.0, 100));
		}
	}

	private void addWeakBar(Chord chord, double start) throws InvalidMidiDataException {
		List<NotePitch> voicing = chord.getAnnotationDefault(VoicingAnnotation.class)
				.getVoicing(VoicingType.BASS_BASIC);
		if (!voicing.isEmpty()) {
			addElement(service.createRandomizedElement(voicing.get(0), start, 6.0 / 7.0, 120));
		}
	}

	private void addThirdBar(Chord chord, double start) throws InvalidMidiDataException {
		List<NotePitch> voicing = chord.getAnnotationDefault(VoicingAnnotation.class)
				.getVoicing(VoicingType.BASS_BASIC);
		if (!voicing.isEmpty()) {
			addElement(service.createRandomizedElement(voicing.get(0), start, 2.0 / 7.0, 110));
		}
	}
}
