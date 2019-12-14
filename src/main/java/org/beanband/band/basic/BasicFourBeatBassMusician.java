package org.beanband.band.basic;

import java.util.List;

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
		}
	}

	private void addStrongHalfBar(Chord chord, double start) throws InvalidMidiDataException {
		NotePitch bassNote = extractBassNote(chord);
		if (bassNote != null) {
			addElement(new MidiNoteElement(bassNote, start + 0.0, 0.375 * 0.75, 120, 127));
			addElement(new MidiNoteElement(bassNote, start + 0.375, 0.125 * 0.75, 100, 127));
		}
	}

	private void addWeakHalfBar(Chord chord, double start) throws InvalidMidiDataException {
		NotePitch bassNote = extractBassNote(chord);
		if (bassNote != null) {
			addElement(new MidiNoteElement(bassNote, start + 0.0, 0.375 * 0.75, 122, 127));
		}
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
