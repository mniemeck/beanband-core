package org.beanband.arranger.basic;

import java.util.List;

import javax.sound.midi.InvalidMidiDataException;

import org.beanband.arranger.Arranger;
import org.beanband.model.midi.NotePitch;
import org.beanband.model.music.ScaleAnnotation;
import org.beanband.model.music.VoicingAnnotation;
import org.beanband.model.music.ScaleAnnotation.ScaleType;
import org.beanband.model.music.VoicingAnnotation.VoicingType;
import org.beanband.model.song.Bar;
import org.beanband.model.song.Chord;
import org.beanband.model.song.Note;
import org.beanband.model.song.Song;

/**
 * Calculates pitches to be played on a piano for a given chord. The algorithm
 * is still rather simplistic, by taking the four most important notes of any chord,
 * and moving them into a low right-hand range.
 * 
 * @author Michael Niemeck
 * @see VoicingAnnotation
 * @see VoicingAnnotation.VoicingType#PIANO_BASIC
 */
public class PianoVoicingArranger extends Arranger {

	@Override
	public void annotate(Song song) throws InvalidMidiDataException {
		NotePitch lowestPitch = new NotePitch(Note.Bb, 4);
		NotePitch highestPitch = new NotePitch(Note.A, 5);
		
		for (Bar bar : song.getBars()) {
			for (Chord chord : bar.getChords()) {
				List<Note> scale = chord.getAnnotationDefault(ScaleAnnotation.class).getScale(ScaleType.CHORD_NOTES);
				for (Note note : scale.subList(0, (scale.size() > 4 ? 4 : scale.size()))) {
					NotePitch notePitch = movePitchInRange(new NotePitch(note, 0), lowestPitch,
							highestPitch);
					chord.getOrCreateAnnotation(VoicingAnnotation.class).addNotePitch(VoicingType.PIANO_BASIC, notePitch);
				}
			}
		}
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
