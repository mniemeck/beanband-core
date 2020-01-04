package org.beanband.arranger.basic;

import java.util.ArrayList;
import java.util.List;

import javax.sound.midi.InvalidMidiDataException;

import org.beanband.arranger.Arranger;
import org.beanband.model.midi.NotePitch;
import org.beanband.model.music.ScaleAnnotation;
import org.beanband.model.music.ScaleAnnotation.ScaleType;
import org.beanband.model.music.VoicingAnnotation;
import org.beanband.model.music.VoicingAnnotation.VoicingType;
import org.beanband.model.song.Bar;
import org.beanband.model.song.Chord;
import org.beanband.model.song.Note;
import org.beanband.model.song.Song;

/**
 * Calculates pitches to be played on a guitar for a given chord. For every
 * given chord the algorithm will calculate a large number of anatomically
 * possible fingerings (only barre type fingerings are considered) and score
 * them by various criteria:
 * <ul>
 * <li>The number of chord notes contained in the fingering and their importance
 * as defined in the {@code ScaleAnnotation}.
 * <li>Notes not part of the chord notes are not tolerated, except when they can
 * be avoided by not sounding lower strings.
 * <li>The distance of the current fingering from the last one.
 * </ul>
 * The highest-scoring fingering is then stored in the annotation.
 * 
 * @author Michael Niemeck
 * @see VoicingAnnotation
 * @see VoicingAnnotation.VoicingType#GUITAR_BASIC
 * @see ScaleAnnotation.ScaleType#CHORD_NOTES
 */
public class AlgorithmicGuitarVoicingArranger extends Arranger {
	private static final int[] BASIC_TUNING = { 40, 45, 50, 55, 59, 64 };
	private static final int MIN_BARRE = 1;
	private static final int MAX_BARRE = 9;
	private static final int FIRST_FINGER_MIN_FRET = 1;
	private static final int FIRST_FINGER_MAX_FRET = 1;
	private static final int SECOND_FINGER_MIN_FRET = 1;
	private static final int SECOND_FINGER_MAX_FRET = 2;
	private static final int THIRD_FINGER_MIN_FRET = 1;
	private static final int THIRD_FINGER_MAX_FRET = 3;
	private static final int THIRD_FINGER_MIN_STRING = 2;
	private static final int MAX_DEAD_STRINGS = 2;

	private static final double SCORE_FIRST_CHORD_NOTE = 1000;
	private static final double SCORE_CHORD_NOTE_FACTOR = 0.6;
	private static final double SCORE_WRONG_NOTE = -10000.0;
	private static final double SCORE_DISTANCE_PENALTY_FACTOR = 2.0;

	@Override
	public void annotate(Song song) throws InvalidMidiDataException {
		List<NotePitch> previousFingering = null;
		for (Bar bar : song.getBars()) {
			for (Chord chord : bar.getChords()) {
				double bestScore = 0.0;
				List<NotePitch> bestFingering = null;

				for (List<NotePitch> fingering : getFingerings()) {
					double score = scoreFingering(fingering, chord, previousFingering);
					if (score > bestScore) {
						bestScore = score;
						bestFingering = fingering;
					}
				}
				if (bestFingering != null) {
					VoicingAnnotation voicingAnnotation = chord.getOrCreateAnnotation(VoicingAnnotation.class);
					for (NotePitch notePitch : bestFingering) {
						voicingAnnotation.addNotePitch(VoicingType.GUITAR_BASIC, notePitch);
					}
				}
				previousFingering = bestFingering;
			}
		}
	}

	private List<List<NotePitch>> getFingerings() throws InvalidMidiDataException {
		List<List<NotePitch>> fingerings = new ArrayList<>();
		for (int barre = MIN_BARRE; barre <= MAX_BARRE; barre++) {
			for (int deadStrings = 0; deadStrings <= MAX_DEAD_STRINGS; deadStrings++) {
				for (int[] fingerPosition : getFingerPositions(deadStrings)) {
					List<NotePitch> fingering = new ArrayList<>();
					for (int string = deadStrings; string < BASIC_TUNING.length; string++) {
						fingering.add(new NotePitch(BASIC_TUNING[string] + barre + fingerPosition[string]));
					}
					fingerings.add(fingering);
				}
			}
		}
		return fingerings;
	}

	private List<int[]> getFingerPositions(int deadStrings) {
		List<int[]> fingerPositions = new ArrayList<>();
		for (int fret = FIRST_FINGER_MIN_FRET; fret <= FIRST_FINGER_MAX_FRET; fret++) {
			for (int string = deadStrings; string < BASIC_TUNING.length; string++) {
				int[] fingerPosition = new int[BASIC_TUNING.length];
				fingerPosition[string] += fret;
				fingerPositions.add(fingerPosition);
				fingerPositions.addAll(getSecondFingerPositions(deadStrings, string, fret));
			}
		}
		return fingerPositions;
	}

	private List<int[]> getSecondFingerPositions(int deadStrings, int firstFingerString, int firstFingerFret) {
		List<int[]> fingerPositions = new ArrayList<>();
		for (int fret = SECOND_FINGER_MIN_FRET; fret <= SECOND_FINGER_MAX_FRET; fret++) {
			for (int string = deadStrings; string < BASIC_TUNING.length; string++) {
				if (string != firstFingerString) {
					int[] fingerPosition = new int[BASIC_TUNING.length];
					fingerPosition[firstFingerString] += firstFingerFret;
					fingerPosition[string] += fret;
					fingerPositions.add(fingerPosition);
					fingerPositions.addAll(
							getThirdFingerPositions(deadStrings, firstFingerString, firstFingerFret, string, fret));
				}
			}
		}
		return fingerPositions;
	}

	private List<int[]> getThirdFingerPositions(int deadStrings, int firstFingerString, int firstFingerFret,
			int secondFingerString, int secondFingerFret) {
		List<int[]> fingerPositions = new ArrayList<>();
		for (int fret = THIRD_FINGER_MIN_FRET; fret <= THIRD_FINGER_MAX_FRET; fret++) {
			for (int string = Math.max(deadStrings, THIRD_FINGER_MIN_STRING); string < BASIC_TUNING.length; string++) {
				if (string != firstFingerString) {
					int[] fingerPosition = new int[BASIC_TUNING.length];
					fingerPosition[firstFingerString] += firstFingerFret;
					fingerPosition[secondFingerString] += secondFingerFret;
					fingerPosition[string] += fret;
					fingerPositions.add(fingerPosition);
				}
			}
		}
		return fingerPositions;
	}

	private double scoreFingering(List<NotePitch> fingering, Chord chord, List<NotePitch> previousFingering) {
		double score = 0.0;

		for (NotePitch notePitch : fingering) {
			score += scoreChordNotes(notePitch,
					chord.getAnnotationDefault(ScaleAnnotation.class).getScale(ScaleType.CHORD_NOTES));
		}
		if (previousFingering != null) {
			score += scoreDistance(fingering, previousFingering);
		}

		return score;
	}

	private double scoreChordNotes(NotePitch notePitch, List<Note> chordNotes) {
		double score = SCORE_FIRST_CHORD_NOTE;
		for (Note note : chordNotes) {
			if (notePitch.getNote().getNumber() == note.getNumber()) {
				return score;
			}
			score = score * SCORE_CHORD_NOTE_FACTOR;
		}
		return SCORE_WRONG_NOTE;
	}

	private double scoreDistance(List<NotePitch> fingering, List<NotePitch> previousFingering) {
		NotePitch topNote = fingering.get(fingering.size() - 1);
		NotePitch previousTopNote = previousFingering.get(previousFingering.size() - 1);
		return Math.pow(SCORE_DISTANCE_PENALTY_FACTOR, Math.abs(topNote.getPitch() - previousTopNote.getPitch()));
	}
}
