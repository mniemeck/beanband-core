package org.beanband.arranger.basic;

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
 * Calculates pitches to be played on a (double) bass to form a bass line.
 * Currently, only the bass note is calculated (i.e. the bass note or the root
 * note is taken from the chord) in a way that it stays within a defined range
 * and minimizes large intervals.
 * 
 * @author Michael Niemeck
 * @see VoicingAnnotation
 * @see VoicingAnnotation.VoicingType#BASS_BASIC
 */
public class BassLineArranger extends Arranger {

	@Override
	public void annotate(Song song) throws InvalidMidiDataException {
		// TODO Enhance to include more than the bass note (fifth, fourth, lead tones,
		// walking lines)
		NotePitch lowestPitch = new NotePitch(Note.E, 2);
		NotePitch highestPitch = new NotePitch(Note.B, 3);
		NotePitch referencePitch = new NotePitch(Note.B, 2);

		for (Bar bar : song.getBars()) {
			for (Chord chord : bar.getChords()) {
				Note note = getBassNote(chord);
				if (note != null) {
					NotePitch notePitch = movePitchInRange(getNearestPitch(note, referencePitch), lowestPitch,
							highestPitch);
					chord.getOrCreateAnnotation(VoicingAnnotation.class).addNotePitch(VoicingType.BASS_BASIC, notePitch);
					referencePitch = notePitch;
				}
			}
		}
	}

	private Note getBassNote(Chord chord) {
		ScaleAnnotation scaleAnnotation = chord.getAnnotation(ScaleAnnotation.class);
		if (scaleAnnotation == null) {
			return null;
		}
		if (scaleAnnotation.getScale(ScaleType.BASS_NOTE).isEmpty()) {
			return null;
		}
		return scaleAnnotation.getScale(ScaleType.BASS_NOTE).get(0);
	}

	private NotePitch getNearestPitch(Note note, NotePitch referencePitch) throws InvalidMidiDataException {
		if (note.getNumber() - referencePitch.getNote().getNumber() > 6) {
			return new NotePitch(note, referencePitch.getOctave() - 1);
		}
		if (note.getNumber() - referencePitch.getNote().getNumber() < -6) {
			return new NotePitch(note, referencePitch.getOctave() + 1);
		}
		return new NotePitch(note, referencePitch.getOctave());
	}

	private NotePitch movePitchInRange(NotePitch pitch, NotePitch lowestPitch, NotePitch highesPitch)
			throws InvalidMidiDataException {

		NotePitch newPitch = pitch;
		while (newPitch.getPitch() < lowestPitch.getPitch()) {
			newPitch = new NotePitch(newPitch.getNote(), newPitch.getOctave() + 1);
		}
		while (newPitch.getPitch() > highesPitch.getPitch()) {
			newPitch = new NotePitch(newPitch.getNote(), newPitch.getOctave() - 1);
		}
		return newPitch;
	}

}
