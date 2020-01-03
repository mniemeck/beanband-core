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
import org.beanband.model.music.ProgressionAnnotation;
import org.beanband.model.music.VoicingAnnotation;
import org.beanband.model.music.VoicingAnnotation.VoicingType;
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
	
	private static final double FRET_PROBABILITY_MEAN = 125.0;
	private static final double FRET_PROBABILIY_DEV = 175.0;
	private static final int FRET_PITCH_MIN = 20;
	private static final int FRET_PITCH_MAX = 100;
	
	private static final double START_DEVIATION = 0.003;
	private static final double DURATION_DEVIATION = 0.003;
	private static final double ON_VELOCITY_DEVIATION = 2.0;
	private static final int OFF_VELOCITY = 127;
	
	private final Random random = new Random();

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
		addRandomizedElement(voicing, start, 0.5 * 0.75, 80);
		addRandomizedElement(voicing, start + 0.5, 0.5 * 0.75, 65);
		if (isChangeAfter(chord)) {
			addFretNoise(start + 0.5 + 0.5 * 0.75, 0.5 * 0.25, msPerBar);
		}
	}

	private void addHalfBar(Chord chord, double start, long msPerBar) throws InvalidMidiDataException {
		List<NotePitch> voicing = extractGuitarVoicing(chord);
		addRandomizedElement(voicing, start, 0.25 * 0.75, 85);
		addRandomizedElement(voicing, start + 0.25, 0.25 * 0.75, 50);
		if (isChangeAfter(chord)) {
			addFretNoise(start + 0.25 + 0.25 * 0.75, 0.25 * 0.25, msPerBar);
		}
	}
	
	private void addQuarterBar(Chord chord, double start, long msPerBar) throws InvalidMidiDataException {
		List<NotePitch> voicing = extractGuitarVoicing(chord);
		addRandomizedElement(voicing, start, 0.25 * 0.75, 85);
		if (isChangeAfter(chord)) {
			addFretNoise(start + 0.25 * 0.75, 0.25 * 0.25, msPerBar);
		}
	}

	private boolean isChangeAfter(Chord chord) {
		ProgressionAnnotation progressionAnnotation = chord.getAnnotation(ProgressionAnnotation.class);
		if (progressionAnnotation == null) {
			return false;
		}
		return progressionAnnotation.isChangeAfter();
	}
	
	private long getMsPerBar(Bar bar) {
		BandAnnotation bandAnnotation = bar.getAnnotation(BandAnnotation.class);
		if (bandAnnotation == null) {
			return Long.MAX_VALUE;
		}
		return Math.round(60000 / bandAnnotation.getTempo() * bandAnnotation.getBand().getBeatsPerBar());
	}
	
	private void addRandomizedElement(List<NotePitch> voicing, double start, double duration, int onVelocity)
			throws InvalidMidiDataException {
		double actualStart = random.nextGaussian() * START_DEVIATION + start;
		double actualDuration = random.nextGaussian() * DURATION_DEVIATION + duration;
		int actualOnVelocity = (int) Math.round(random.nextGaussian() * ON_VELOCITY_DEVIATION + onVelocity);
		actualOnVelocity = Math.max(0, actualOnVelocity);
		actualOnVelocity = Math.min(127, actualOnVelocity);
		int actualOffVelocity = OFF_VELOCITY;

		for (NotePitch notePitch : voicing) {
			addElement(new MidiNoteElement(notePitch, actualStart, actualDuration, actualOnVelocity, actualOffVelocity));
		}
	}

	private void addFretNoise(double start, double duration, long msPerBar) throws InvalidMidiDataException {
		if (random.nextGaussian() * FRET_PROBABILIY_DEV + FRET_PROBABILITY_MEAN <= (duration * msPerBar)) {
			return;
		}
		
		double startMean = start + duration / 2;
		double startDev = duration / 6;
				
		double actualStart = Math.max(0.00025, random.nextGaussian() * startDev + startMean);
		
		double durationMean = duration / 2;
		double durationDev = duration / 100;
		
		double actualDuration = random.nextGaussian() * durationDev + durationMean;
		
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
		return voicingAnnotation.getVoicing(VoicingType.GUITAR_BASIC);
	}

}
