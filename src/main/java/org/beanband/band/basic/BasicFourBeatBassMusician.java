package org.beanband.band.basic;

import java.util.List;

import javax.sound.midi.InvalidMidiDataException;

import org.beanband.band.Musician;
import org.beanband.model.midi.InstrumentPatch;
import org.beanband.model.midi.MidiNoteElement;
import org.beanband.model.midi.NotePitch;
import org.beanband.model.music.VoicingAnnotation;
import org.beanband.model.music.VoicingAnnotation.Type;
import org.beanband.model.song.Bar;
import org.beanband.model.song.Chord;

public class BasicFourBeatBassMusician extends Musician {

	@Override
	public InstrumentPatch getInstrumentPatch() {
		return InstrumentPatch.ACOUSTIC_BASS;
	}

	@Override
	protected void createElements(Bar bar) throws InvalidMidiDataException {
		if (!bar.getChords().isEmpty()) {
			NotePitch bassNote = extractBassNote(bar.getChords().get(0));
			if (bassNote != null) {
				addElement(new MidiNoteElement(bassNote, 0.0, 0.28125, 120, 127));
				addElement(new MidiNoteElement(bassNote, 0.375, 0.09375, 100, 127));
			}
			if (bar.getChords().size() > 1) {
				NotePitch bassNote2 = extractBassNote(bar.getChords().get(1));
				if (bassNote2 != null) {
					addElement(new MidiNoteElement(bassNote2, 0.5, 0.28125, 120, 127));
					addElement(new MidiNoteElement(bassNote2, 0.875, 0.09375, 100, 127));
				}
			} else {
				if (bassNote != null) {
					addElement(new MidiNoteElement(bassNote, 0.5, 0.375, 120, 127));
				}
			}
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
