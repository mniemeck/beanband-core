package org.beanband.band.basic;

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
 * The bass player of our showcase band. It will play just the bass note in a
 * slightly varying rhythm based on the number of changes per bar.
 * 
 * @author Michael Niemeck
 *
 */
public class BasicFourBeatBassMusician extends Musician {

	private static final double START_DEVIATION = 0.003;
	private static final double DURATION_DEVIATION = 0.003;
	private static final double ON_VELOCITY_DEVIATION = 2.0;
	private static final int OFF_VELOCITY = 127;
	
	private final Random random = new Random();
	
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
		NotePitch bassNote = extractBassNote(chord);
		if (bassNote != null) {
			addRandomizedElement(bassNote, start + 0.0, 0.375 * 0.75, 120);
			addRandomizedElement(bassNote, start + 0.375, 0.125 * 0.75, 100);
		}
	}

	private void addWeakHalfBar(Chord chord, double start) throws InvalidMidiDataException {
		NotePitch bassNote = extractBassNote(chord);
		if (bassNote != null) {
			addRandomizedElement(bassNote, start + 0.0, 0.375 * 0.75, 122);
		}
	}
	
	private void addQuarterBar(Chord chord, double start) throws InvalidMidiDataException {
		NotePitch bassNote = extractBassNote(chord);
		if (bassNote != null) {
			addRandomizedElement(bassNote, start + 0.0, 0.25 * 0.75, 125);
		}
	}

	private void addRandomizedElement(NotePitch pitch, double start, double duration, int onVelocity)
			throws InvalidMidiDataException {
		double actualStart = Math.max(0.00025, random.nextGaussian() * START_DEVIATION + start);
		double actualDuration = random.nextGaussian() * DURATION_DEVIATION + duration;
		int actualOnVelocity = (int) Math.round(random.nextGaussian() * ON_VELOCITY_DEVIATION + onVelocity);
		actualOnVelocity = Math.max(0, actualOnVelocity);
		actualOnVelocity = Math.min(127, actualOnVelocity);
		int actualOffVelocity = OFF_VELOCITY;

		addElement(new MidiNoteElement(pitch, actualStart, actualDuration, actualOnVelocity, actualOffVelocity));
	}

	private NotePitch extractBassNote(Chord chord) {
		VoicingAnnotation voicingAnnotation = chord.getAnnotation(VoicingAnnotation.class);
		if (voicingAnnotation == null) {
			return null;
		}
		List<NotePitch> voicing = voicingAnnotation.getVoicing(Type.BASS_BASIC);
		if (voicing.isEmpty()) {
			return null;
		}
		return voicing.get(0);
	}

}
