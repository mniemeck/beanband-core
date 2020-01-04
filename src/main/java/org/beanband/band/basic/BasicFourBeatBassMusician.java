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
 * The bass player of our showcase band. It will play just the bass note in a
 * slightly varying rhythm based on the number of changes per bar.
 * 
 * @author Michael Niemeck
 *
 */
public class BasicFourBeatBassMusician extends Musician {
	
	private final RandomizedMusicianService service = new RandomizedMusicianService();

	@Override
	protected void createElements(Bar bar) throws InvalidMidiDataException {
		addElement(new MidiProgramChangeElement(InstrumentPatch.ACOUSTIC_BASS, 0.0));
		switch (bar.getChords().size()) {
		case 1:
			addStrongHalfBar(bar.getChords().get(0), 0.0);
			addWeakHalfBar(bar.getChords().get(0), 0.5);
			break;
		case 2:
			addStrongHalfBar(bar.getChords().get(0), 0.0);
			addStrongHalfBar(bar.getChords().get(1), 0.5);
			break;
		case 4:
			addQuarterBar(bar.getChords().get(0), 0.0);
			addQuarterBar(bar.getChords().get(1), 0.25);
			addQuarterBar(bar.getChords().get(2), 0.5);
			addQuarterBar(bar.getChords().get(3), 0.75);
		}
	}

	private void addStrongHalfBar(Chord chord, double start) throws InvalidMidiDataException {
		List<NotePitch> voicing = chord.getAnnotationDefault(VoicingAnnotation.class).getVoicing(VoicingType.BASS_BASIC);
		if (!voicing.isEmpty()) {
			addElement(service.createRandomizedElement(voicing.get(0), start + 0.0, 0.375 * 0.75, 120));
			addElement(service.createRandomizedElement(voicing.get(0), start + 0.375, 0.125 * 0.75, 100));
		}
	}

	private void addWeakHalfBar(Chord chord, double start) throws InvalidMidiDataException {
		List<NotePitch> voicing = chord.getAnnotationDefault(VoicingAnnotation.class).getVoicing(VoicingType.BASS_BASIC);
		if (!voicing.isEmpty()) {
			addElement(service.createRandomizedElement(voicing.get(0), start + 0.0, 0.375 * 0.75, 122));
		}
	}
	
	private void addQuarterBar(Chord chord, double start) throws InvalidMidiDataException {
		List<NotePitch> voicing = chord.getAnnotationDefault(VoicingAnnotation.class).getVoicing(VoicingType.BASS_BASIC);
		if (!voicing.isEmpty()) {
			addElement(service.createRandomizedElement(voicing.get(0), start + 0.0, 0.25 * 0.75, 125));
		}
	}
}
