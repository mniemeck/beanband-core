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
import org.beanband.model.music.BandAnnotation;
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
	
	// TODO FretNoise logic more generic
	
	// - Only when changeAfter (cf. FormArranger)
	// - Probability based on time to change (Already done? - Better document variables)
	
	private static final double FRET_PROBABILITY_MEAN = 125.0;
	private static final double FRET_PROBABILIY_DEV = 175.0;
	private static final double FRET_DURATION_MEAN = 0.1;
	private static final double FRET_DURATION_DEV = 0.001;
	private static final int FRET_PITCH_MIN = 20;
	private static final int FRET_PITCH_MAX = 100;
	
	private Random random = new Random();

	@Override
	protected void createElements(Bar bar) throws InvalidMidiDataException {
		long msPerBar = getMsPerBar(bar);
		
		addElement(new MidiProgramChangeElement(InstrumentPatch.ACOUSTIC_GUITAR_STEEL, 0.0));
		switch (bar.getChords().size()) {
		case 1:
			addFullBar(bar.getChords().get(0), 0.0, msPerBar);
			break;
		case 2:
			addHalfBar(bar.getChords().get(0), 0.0, msPerBar);
			addHalfBar(bar.getChords().get(1), 0.5, msPerBar);
			break;
		case 4:
			addQuarterBar(bar.getChords().get(0), 0.0, msPerBar);
			addQuarterBar(bar.getChords().get(1), 0.25, msPerBar);
			addQuarterBar(bar.getChords().get(2), 0.5, msPerBar);
			addQuarterBar(bar.getChords().get(3), 0.75, msPerBar);
			break;
		}
	}

	private void addFullBar(Chord chord, double start, long msPerBar) throws InvalidMidiDataException {
		List<NotePitch> voicing = extractGuitarVoicing(chord);
		for (NotePitch notePitch : voicing) {
			addElement(new MidiNoteElement(notePitch, start, 0.5 * 0.75, 80, 127));
			addElement(new MidiNoteElement(notePitch, start + 0.5, 0.5 * 0.75, 65, 127));
		}
		addFretNoise(start + 0.5 + 0.5 * 0.75, 0.5 * 0.25, msPerBar);
	}

	private void addHalfBar(Chord chord, double start, long msPerBar) throws InvalidMidiDataException {
		List<NotePitch> voicing = extractGuitarVoicing(chord);
		for (NotePitch notePitch : voicing) {
			addElement(new MidiNoteElement(notePitch, start, 0.25 * 0.75, 82, 127));
			addElement(new MidiNoteElement(notePitch, start + 0.25, 0.25 * 0.75, 50, 127));
		}
		addFretNoise(start + 0.25 + 0.25 * 0.75, 0.25 * 0.25, msPerBar);
	}
	
	private void addQuarterBar(Chord chord, double start, long msPerBar) throws InvalidMidiDataException {
		List<NotePitch> voicing = extractGuitarVoicing(chord);
		for (NotePitch notePitch : voicing) {
			addElement(new MidiNoteElement(notePitch, start, 0.25 * 0.75, 87, 127));
		}
		addFretNoise(start + 0.25 * 0.75, 0.25 * 0.25, msPerBar);
	}

	private long getMsPerBar(Bar bar) {
		BandAnnotation bandAnnotation = bar.getAnnotation(BandAnnotation.class);
		if (bandAnnotation == null) {
			return Long.MAX_VALUE;
		}
		return Math.round(60000 / bandAnnotation.getTempo() * bandAnnotation.getBand().getBeatsPerBar());
	}
	
	private void addFretNoise(double start, double duration, long msPerBar) throws InvalidMidiDataException {
		if (random.nextGaussian() * FRET_PROBABILIY_DEV + FRET_PROBABILITY_MEAN <= (duration * msPerBar)) {
			return;
		}
		double startMean = start + duration / 2;
		double startDev = duration / 6;
				
		double actualStart = random.nextGaussian() * startDev + startMean;
		double actualDuration = random.nextGaussian() * FRET_DURATION_DEV + FRET_DURATION_MEAN;
		NotePitch actualPitch = new NotePitch(FRET_PITCH_MIN + random.nextInt(FRET_PITCH_MAX - FRET_PITCH_MIN));
		
		addElement(new MidiProgramChangeElement(InstrumentPatch.GUITAR_FRET_NOISE, actualStart - 0.01));
		addElement(new MidiNoteElement(actualPitch, actualStart, actualDuration, 64, 0));
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
