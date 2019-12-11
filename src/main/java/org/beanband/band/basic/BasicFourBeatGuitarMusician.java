package org.beanband.band.basic;

import java.util.Collections;
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

public class BasicFourBeatGuitarMusician extends Musician {
	@Override
	public InstrumentPatch getInstrumentPatch() {
		return InstrumentPatch.ACOUSTIC_GUITAR_STEEL;
	}

	@Override
	protected void createElements(Bar bar) throws InvalidMidiDataException {
		if (!bar.getChords().isEmpty()) {
			List<NotePitch> voicing = extractGuitarVoicing(bar.getChords().get(0));
			if (bar.getChords().size() == 1) {
				for (NotePitch notePitch : voicing) {
					addElement(new MidiNoteElement(notePitch, 0.0, 0.375, 80, 127));
					addElement(new MidiNoteElement(notePitch, 0.5, 0.375, 65, 127));
				}
			} else {
				List<NotePitch> voicing2 = extractGuitarVoicing(bar.getChords().get(1));
				for (NotePitch notePitch : voicing) {
					addElement(new MidiNoteElement(notePitch, 0.0, 0.1875, 80, 127));
					addElement(new MidiNoteElement(notePitch, 0.25, 0.1875, 50, 127));
				}
				for (NotePitch notePitch : voicing2) {
					addElement(new MidiNoteElement(notePitch, 0.5, 0.1875, 80, 127));
					addElement(new MidiNoteElement(notePitch, 0.75, 0.1875, 50, 127));
				}
			}
		}
	}

	private List<NotePitch> extractGuitarVoicing(Chord chord) {
		VoicingAnnotation voicingAnnotation = chord.getAnnotation(VoicingAnnotation.class);
		if (voicingAnnotation == null) {
			return Collections.emptyList();
		}
		return voicingAnnotation.getVoicing(Type.GUITAR_BASIC);
	}

}
