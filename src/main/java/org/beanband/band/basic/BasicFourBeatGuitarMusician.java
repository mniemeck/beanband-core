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

/**
 * The guitar player of our showcase band. It will play just the basic voicing
 * in a slightly varying rhythm based on the number of changes per bar.
 * 
 * @author Michael Niemeck
 *
 */
public class BasicFourBeatGuitarMusician extends Musician {
	@Override
	public InstrumentPatch getInstrumentPatch() {
		return InstrumentPatch.ACOUSTIC_GUITAR_STEEL;
	}

	@Override
	protected void createElements(Bar bar) throws InvalidMidiDataException {
		switch (bar.getChords().size()) {
		case 1:
			addFullBar(bar.getChords().get(0), 0.0);
			break;
		case 2:
			addHalfBar(bar.getChords().get(0), 0.0);
			addHalfBar(bar.getChords().get(1), 0.5);
			break;
		}
	}
		
	private void addFullBar(Chord chord, double start) throws InvalidMidiDataException {
		List<NotePitch> voicing = extractGuitarVoicing(chord);
		for (NotePitch notePitch : voicing) {
			addElement(new MidiNoteElement(notePitch, start + 0.0, 0.5 * 0.75, 80, 127));
			addElement(new MidiNoteElement(notePitch, start + 0.5, 0.5 * 0.75, 65, 127));
		}
	}

	private void addHalfBar(Chord chord, double start) throws InvalidMidiDataException {
		List<NotePitch> voicing = extractGuitarVoicing(chord);
		for (NotePitch notePitch : voicing) {
			addElement(new MidiNoteElement(notePitch, start + 0.0, 0.25 * 0.75, 82, 127));
			addElement(new MidiNoteElement(notePitch, start + 0.25, 0.25 * 0.75, 50, 127));
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
