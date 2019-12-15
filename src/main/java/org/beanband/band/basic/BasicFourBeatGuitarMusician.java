package org.beanband.band.basic;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.sound.midi.InvalidMidiDataException;

import org.beanband.band.Musician;
import org.beanband.model.midi.InstrumentPatch;
import org.beanband.model.midi.MidiNoteElement;
import org.beanband.model.midi.MidiProgramChangeElement;
import org.beanband.model.midi.NotePitch;
import org.beanband.model.music.VoicingAnnotation;
import org.beanband.model.music.VoicingAnnotation.Type;
import org.beanband.model.song.Bar;
import org.beanband.model.song.Chord;

/**
 * The guitar player of our showcase band. It will play just the basic voicing
 * in a slightly varying rhythm based on the number of changes per bar, and add
 * some randomized fret noises.
 * 
 * @author Michael Niemeck
 *
 */
public class BasicFourBeatGuitarMusician extends Musician {
	private Random random = new Random();

	@Override
	protected void createElements(Bar bar) throws InvalidMidiDataException {
		// TODO Change fret noise probability based on average chords per bar
		addElement(new MidiProgramChangeElement(InstrumentPatch.ACOUSTIC_GUITAR_STEEL, 0.0));
		switch (bar.getChords().size()) {
		case 1:
			addFullBar(bar.getChords().get(0), 0.0);
			break;
		case 2:
			addHalfBar(bar.getChords().get(0), 0.0);
			addHalfBar(bar.getChords().get(1), 0.5);
			break;
		case 4:
			addQuarterBar(bar.getChords().get(0), 0.0);
			addQuarterBar(bar.getChords().get(1), 0.25);
			addQuarterBar(bar.getChords().get(2), 0.5);
			addQuarterBar(bar.getChords().get(3), 0.75);
			break;
		}
	}

	private void addFullBar(Chord chord, double start) throws InvalidMidiDataException {
		List<NotePitch> voicing = extractGuitarVoicing(chord);
		for (NotePitch notePitch : voicing) {
			addElement(new MidiNoteElement(notePitch, start + 0.0, 0.5 * 0.75, 80, 127));
			addElement(new MidiNoteElement(notePitch, start + 0.5, 0.5 * 0.75, 65, 127));
		}
		if (random.nextInt(25) == 0) {
			addFretNoise(start + 0.9375, 0.02);
		}
	}

	private void addHalfBar(Chord chord, double start) throws InvalidMidiDataException {
		List<NotePitch> voicing = extractGuitarVoicing(chord);
		for (NotePitch notePitch : voicing) {
			addElement(new MidiNoteElement(notePitch, start + 0.0, 0.25 * 0.75, 82, 127));
			addElement(new MidiNoteElement(notePitch, start + 0.25, 0.25 * 0.75, 50, 127));
		}
		addFretNoise(start + 0.375, 0.02);
	}
	
	private void addQuarterBar(Chord chord, double start) throws InvalidMidiDataException {
		List<NotePitch> voicing = extractGuitarVoicing(chord);
		for (NotePitch notePitch : voicing) {
			addElement(new MidiNoteElement(notePitch, start + 0.0, 0.25 * 0.75, 87, 127));
		}
		addFretNoise(start + 0.21875, 0.01);
	}

	private void addFretNoise(double start, double variation) throws InvalidMidiDataException {
		double actualStart = start + random.nextGaussian() * variation;
		double duration = 0.1 + random.nextGaussian() * 0.001;
		NotePitch notePitch = new NotePitch(random.nextInt(128));
		addElement(new MidiProgramChangeElement(InstrumentPatch.GUITAR_FRET_NOISE, actualStart - 0.01));
		addElement(new MidiNoteElement(notePitch, actualStart, duration, 64, 0));
		addElement(new MidiProgramChangeElement(InstrumentPatch.ACOUSTIC_GUITAR_STEEL, actualStart + 0.01));
	}

	private List<NotePitch> extractGuitarVoicing(Chord chord) {
		VoicingAnnotation voicingAnnotation = chord.getAnnotation(VoicingAnnotation.class);
		if (voicingAnnotation == null) {
			return Collections.emptyList();
		}
		return voicingAnnotation.getVoicing(Type.GUITAR_BASIC);
	}

}
