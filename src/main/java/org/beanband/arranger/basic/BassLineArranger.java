package org.beanband.arranger.basic;

import javax.sound.midi.InvalidMidiDataException;

import org.beanband.arranger.Arranger;
import org.beanband.model.midi.NotePitch;
import org.beanband.model.music.VoicingAnnotation;
import org.beanband.model.music.VoicingAnnotation.Type;
import org.beanband.model.song.Bar;
import org.beanband.model.song.Chord;
import org.beanband.model.song.Note;
import org.beanband.model.song.Song;

public class BassLineArranger extends Arranger {

	@Override
	public void annotate(Song song) throws InvalidMidiDataException {
		NotePitch lowestPitch = new NotePitch(Note.E, 2);
		NotePitch highestPitch = new NotePitch(Note.B, 3);
		NotePitch referencePitch = new NotePitch(Note.B, 2);

		for (Bar bar : song.getBars()) {
			for (Chord chord : bar.getChords()) {
				Note note = (chord.getBass() != null ? chord.getBass() : chord.getRoot());
				if (note != null) {
					NotePitch notePitch = movePitchInRange(getNearestPitch(note, referencePitch), lowestPitch, highestPitch);
					chord.getOrCreateAnnotation(VoicingAnnotation.class).addNotePitch(Type.BASS_BASIC, notePitch);
					referencePitch = notePitch;
				}
			}
		}
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
