package org.beanband.arranger.basic;

import javax.sound.midi.InvalidMidiDataException;

import org.beanband.arranger.Arranger;
import org.beanband.model.midi.NotePitch;
import org.beanband.model.music.VoicingAnnotation;
import org.beanband.model.music.VoicingAnnotation.Type;
import org.beanband.model.song.Bar;
import org.beanband.model.song.Chord;
import org.beanband.model.song.Eleventh;
import org.beanband.model.song.Fifth;
import org.beanband.model.song.Ninth;
import org.beanband.model.song.Note;
import org.beanband.model.song.Seventh;
import org.beanband.model.song.Song;
import org.beanband.model.song.Third;
import org.beanband.model.song.Thirteenth;

/**
 * Calculates pitches to be played on a guitar for a given chord. The algorithm is still rather
 * simplistic, but it will produce chords that sound reasonably realistic. It is
 * based on three basic chords (Fmaj, Bbmaj, Ebmaj) with the root at the first,
 * second or third string respectively. Based on the root of the desired chord the
 * nearest standard basic chord is picked and transposed by sliding upwards.
 * Then the strings are processed lowest to highest to change ptich to some of
 * the options of the chord in a way that is anatomically at least somewhat
 * reasonable (not all have been tested by an actual gutarist). Also, little
 * musicological preferences are implemented, on which options to realize and
 * which to leave out.
 * 
 * @author Michael Niemeck
 * @see VoicingAnnotation
 * @see VoicingAnnotation.Type#GUITAR_BASIC
 */
public class GuitarVoicingArranger extends Arranger {

	@Override
	public void annotate(Song song) throws InvalidMidiDataException {
		for (Bar bar : song.getBars()) {
			for (Chord chord : bar.getChords()) {
				if (chord.getRoot() != null) {
					if (chord.getRoot().getNumber() <= Note.D.getNumber()) {
						createType2Voicing(chord, 12 + chord.getRoot().getNumber() - Note.A.getNumber());
					} else if (chord.getRoot().getNumber() <= Note.E.getNumber()) {
						createType3Voicing(chord, chord.getRoot().getNumber() - Note.D.getNumber());
					} else if (chord.getRoot().getNumber() <= Note.A.getNumber()) {
						createType1Voicing(chord, chord.getRoot().getNumber() - Note.E.getNumber());
					} else {
						createType2Voicing(chord, chord.getRoot().getNumber() - Note.A.getNumber());
					}
				}
			}
		}
	}

	private void createType1Voicing(Chord chord, int offset) throws InvalidMidiDataException {
		VoicingAnnotation voicingAnnotation = chord.getOrCreateAnnotation(VoicingAnnotation.class);
		int currentStringPitch = 40 + offset;
		voicingAnnotation.addNotePitch(Type.GUITAR_BASIC, new NotePitch(currentStringPitch));
		currentStringPitch += 5;
		if (chord.getFifth() == Fifth.DIM) {
			voicingAnnotation.addNotePitch(Type.GUITAR_BASIC, new NotePitch(currentStringPitch + 1));
		} else if (chord.getFifth() == Fifth.PERFECT) {
			voicingAnnotation.addNotePitch(Type.GUITAR_BASIC, new NotePitch(currentStringPitch + 2));
		} else if (chord.getFifth() == Fifth.AUG) {
			voicingAnnotation.addNotePitch(Type.GUITAR_BASIC, new NotePitch(currentStringPitch + 3));
		}
		currentStringPitch += 5;
		if (chord.getSeventh() == Seventh.MIN) {
			voicingAnnotation.addNotePitch(Type.GUITAR_BASIC, new NotePitch(currentStringPitch));
		} else if (chord.getSeventh() == Seventh.MAJ) {
			voicingAnnotation.addNotePitch(Type.GUITAR_BASIC, new NotePitch(currentStringPitch + 1));
		} else {
			voicingAnnotation.addNotePitch(Type.GUITAR_BASIC, new NotePitch(currentStringPitch + 2));
		}
		currentStringPitch += 5;
		if (chord.getThird() == Third.MIN) {
			voicingAnnotation.addNotePitch(Type.GUITAR_BASIC, new NotePitch(currentStringPitch));
		} else if ((chord.getThird() == Third.MAJ) || (chord.getEleventh() == Eleventh.DIM)) {
			voicingAnnotation.addNotePitch(Type.GUITAR_BASIC, new NotePitch(currentStringPitch + 1));
		} else if ((chord.getThird() == Third.SUS4) || (chord.getEleventh() == Eleventh.PERFECT)) {
			voicingAnnotation.addNotePitch(Type.GUITAR_BASIC, new NotePitch(currentStringPitch + 2));
		} else if (chord.getEleventh() == Eleventh.AUG) {
			voicingAnnotation.addNotePitch(Type.GUITAR_BASIC, new NotePitch(currentStringPitch + 3));
		}
		currentStringPitch += 4;
		if ((chord.getFifth() == Fifth.AUG) || (chord.getThirteenth() == Thirteenth.MIN)) {
			voicingAnnotation.addNotePitch(Type.GUITAR_BASIC, new NotePitch(currentStringPitch + 1));
		} else if ((chord.getSeventh() == Seventh.SIXTH) || (chord.getThirteenth() == Thirteenth.MAJ)) {
			voicingAnnotation.addNotePitch(Type.GUITAR_BASIC, new NotePitch(currentStringPitch + 2));
		} else if (chord.getThirteenth() == Thirteenth.AUG) {
			voicingAnnotation.addNotePitch(Type.GUITAR_BASIC, new NotePitch(currentStringPitch + 3));
		} else if (chord.getFifth() == Fifth.PERFECT) {
			voicingAnnotation.addNotePitch(Type.GUITAR_BASIC, new NotePitch(currentStringPitch));
		}
		currentStringPitch += 5;
		if (chord.getNinth() == Ninth.MIN) {
			voicingAnnotation.addNotePitch(Type.GUITAR_BASIC, new NotePitch(currentStringPitch + 1));
		} else if ((chord.getThird() == Third.SUS2) || (chord.getNinth() == Ninth.MAJ)) {
			voicingAnnotation.addNotePitch(Type.GUITAR_BASIC, new NotePitch(currentStringPitch + 2));
		} else if ((chord.getThird() == Third.MIN) || (chord.getNinth() == Ninth.AUG)) {
			voicingAnnotation.addNotePitch(Type.GUITAR_BASIC, new NotePitch(currentStringPitch + 3));
		} else {
			voicingAnnotation.addNotePitch(Type.GUITAR_BASIC, new NotePitch(currentStringPitch));
		}
	}

	private void createType2Voicing(Chord chord, int offset) throws InvalidMidiDataException {
		VoicingAnnotation voicingAnnotation = chord.getOrCreateAnnotation(VoicingAnnotation.class);
		int currentStringPitch = 40 + offset;
		if (chord.getFifth() == Fifth.PERFECT) {
			voicingAnnotation.addNotePitch(Type.GUITAR_BASIC, new NotePitch(currentStringPitch));
		}
		currentStringPitch += 5;
		voicingAnnotation.addNotePitch(Type.GUITAR_BASIC, new NotePitch(currentStringPitch));
		currentStringPitch += 5;
		if ((chord.getFifth() == Fifth.DIM) || (chord.getEleventh() == Eleventh.AUG)) {
			voicingAnnotation.addNotePitch(Type.GUITAR_BASIC, new NotePitch(currentStringPitch + 1));
		} else if ((chord.getFifth() == Fifth.AUG) || (chord.getThirteenth() == Thirteenth.MIN)) {
			voicingAnnotation.addNotePitch(Type.GUITAR_BASIC, new NotePitch(currentStringPitch + 3));
		} else if (chord.getFifth() == Fifth.PERFECT) {
			voicingAnnotation.addNotePitch(Type.GUITAR_BASIC, new NotePitch(currentStringPitch + 2));
		}
		currentStringPitch += 5;
		if (chord.getSeventh() == Seventh.MIN) {
			voicingAnnotation.addNotePitch(Type.GUITAR_BASIC, new NotePitch(currentStringPitch));
		} else if (chord.getSeventh() == Seventh.MAJ) {
			voicingAnnotation.addNotePitch(Type.GUITAR_BASIC, new NotePitch(currentStringPitch + 1));
		} else if (chord.getNinth() == Ninth.MIN) {
			voicingAnnotation.addNotePitch(Type.GUITAR_BASIC, new NotePitch(currentStringPitch + 3));
		} else {
			voicingAnnotation.addNotePitch(Type.GUITAR_BASIC, new NotePitch(currentStringPitch + 2));
		}
		currentStringPitch += 4;
		if ((chord.getThird() == Third.SUS2) || (chord.getNinth() == Ninth.MAJ)) {
			voicingAnnotation.addNotePitch(Type.GUITAR_BASIC, new NotePitch(currentStringPitch));
		} else if ((chord.getThird() == Third.MIN) || (chord.getNinth() == Ninth.AUG)) {
			voicingAnnotation.addNotePitch(Type.GUITAR_BASIC, new NotePitch(currentStringPitch + 1));
		} else if ((chord.getThird() == Third.MAJ) || (chord.getEleventh() == Eleventh.DIM)) {
			voicingAnnotation.addNotePitch(Type.GUITAR_BASIC, new NotePitch(currentStringPitch + 2));
		} else if ((chord.getThird() == Third.SUS4) || (chord.getEleventh() == Eleventh.PERFECT)) {
			voicingAnnotation.addNotePitch(Type.GUITAR_BASIC, new NotePitch(currentStringPitch + 3));
		}
		currentStringPitch += 5;
		if ((chord.getFifth() == Fifth.AUG) || (chord.getThirteenth() == Thirteenth.MIN)) {
			voicingAnnotation.addNotePitch(Type.GUITAR_BASIC, new NotePitch(currentStringPitch + 1));
		} else if ((chord.getSeventh() == Seventh.SIXTH) || (chord.getThirteenth() == Thirteenth.MAJ)) {
			voicingAnnotation.addNotePitch(Type.GUITAR_BASIC, new NotePitch(currentStringPitch + 2));
		} else if (chord.getThirteenth() == Thirteenth.AUG) {
			voicingAnnotation.addNotePitch(Type.GUITAR_BASIC, new NotePitch(currentStringPitch + 3));
		} else if (chord.getFifth() == Fifth.PERFECT) {
			voicingAnnotation.addNotePitch(Type.GUITAR_BASIC, new NotePitch(currentStringPitch));
		}
	}

	private void createType3Voicing(Chord chord, int offset) throws InvalidMidiDataException {
		VoicingAnnotation voicingAnnotation = chord.getOrCreateAnnotation(VoicingAnnotation.class);
		int currentStringPitch = 40 + offset;
		currentStringPitch += 5;
		if (chord.getFifth() == Fifth.PERFECT) {
			voicingAnnotation.addNotePitch(Type.GUITAR_BASIC, new NotePitch(currentStringPitch));
		}
		currentStringPitch += 5;
		voicingAnnotation.addNotePitch(Type.GUITAR_BASIC, new NotePitch(currentStringPitch));
		currentStringPitch += 5;
		if ((chord.getFifth() == Fifth.DIM) || (chord.getEleventh() == Eleventh.AUG)) {
			voicingAnnotation.addNotePitch(Type.GUITAR_BASIC, new NotePitch(currentStringPitch + 1));
		} else if ((chord.getFifth() == Fifth.AUG) || (chord.getThirteenth() == Thirteenth.MIN)) {
			voicingAnnotation.addNotePitch(Type.GUITAR_BASIC, new NotePitch(currentStringPitch + 3));
		} else if (chord.getFifth() == Fifth.PERFECT) {
			voicingAnnotation.addNotePitch(Type.GUITAR_BASIC, new NotePitch(currentStringPitch + 2));
		}
		currentStringPitch += 4;
		if ((chord.getSeventh() == Seventh.SIXTH) || (chord.getThirteenth() == Thirteenth.MAJ)) {
			voicingAnnotation.addNotePitch(Type.GUITAR_BASIC, new NotePitch(currentStringPitch));
		} else if ((chord.getSeventh() == Seventh.MIN) || (chord.getThirteenth() == Thirteenth.AUG)) {
			voicingAnnotation.addNotePitch(Type.GUITAR_BASIC, new NotePitch(currentStringPitch + 1));
		} else if (chord.getSeventh() == Seventh.MAJ) {
			voicingAnnotation.addNotePitch(Type.GUITAR_BASIC, new NotePitch(currentStringPitch + 2));
		} else {
			voicingAnnotation.addNotePitch(Type.GUITAR_BASIC, new NotePitch(currentStringPitch + 3));
		}
		currentStringPitch += 5;
		if ((chord.getThird() == Third.SUS2) || (chord.getNinth() == Ninth.MAJ)) {
			voicingAnnotation.addNotePitch(Type.GUITAR_BASIC, new NotePitch(currentStringPitch));
		} else if ((chord.getThird() == Third.MIN) || (chord.getNinth() == Ninth.AUG)) {
			voicingAnnotation.addNotePitch(Type.GUITAR_BASIC, new NotePitch(currentStringPitch + 1));
		} else if ((chord.getThird() == Third.MAJ) || (chord.getEleventh() == Eleventh.DIM)) {
			voicingAnnotation.addNotePitch(Type.GUITAR_BASIC, new NotePitch(currentStringPitch + 2));
		} else if ((chord.getThird() == Third.SUS4) || (chord.getEleventh() == Eleventh.PERFECT)) {
			voicingAnnotation.addNotePitch(Type.GUITAR_BASIC, new NotePitch(currentStringPitch + 3));
		}
	}
	
	@Override
	protected int getPriority() {
		return Integer.MAX_VALUE - 2;
	}
}
