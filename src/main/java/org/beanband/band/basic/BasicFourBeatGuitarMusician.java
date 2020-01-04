package org.beanband.band.basic;

import java.util.List;

import javax.sound.midi.InvalidMidiDataException;

import org.beanband.band.Musician;
import org.beanband.model.midi.InstrumentPatch;
import org.beanband.model.midi.MidiProgramChangeElement;
import org.beanband.model.midi.NotePitch;
import org.beanband.model.music.BandAnnotation;
import org.beanband.model.music.ProgressionAnnotation;
import org.beanband.model.music.VoicingAnnotation;
import org.beanband.model.music.VoicingAnnotation.VoicingType;
import org.beanband.model.song.Bar;
import org.beanband.model.song.Chord;

/**
 * The guitar player of our showcase band. It will play just the basic voicing
 * in a slightly varying rhythm based on the number of changes per bar, and add
 * some randomized fret noises.
 * 
 * @author Michael Niemeck
 *
 */
public class BasicFourBeatGuitarMusician extends Musician {

	private final RandomizedMusicianService service = new RandomizedMusicianService();

	@Override
	protected void createElements(Bar bar) throws InvalidMidiDataException {
		long msPerBar = getMsPerBar(bar);

		addElement(new MidiProgramChangeElement(InstrumentPatch.ACOUSTIC_GUITAR_STEEL, 0.0));
		service.setFretOriginalPatch(InstrumentPatch.ACOUSTIC_GUITAR_STEEL);
		switch (bar.getChords().size()) {
		case 1:
			addFullBar(bar.getChords().get(0), 0.0, msPerBar);
			break;
		case 2:
			addHalfBar(bar.getChords().get(0), 0.0, msPerBar);
			addHalfBar(bar.getChords().get(1), 0.5, msPerBar);
			break;
		case 4:
			addQuarterBar(bar.getChords().get(0), 0.0, msPerBar);
			addQuarterBar(bar.getChords().get(1), 0.25, msPerBar);
			addQuarterBar(bar.getChords().get(2), 0.5, msPerBar);
			addQuarterBar(bar.getChords().get(3), 0.75, msPerBar);
			break;
		}
	}

	private void addFullBar(Chord chord, double start, long msPerBar) throws InvalidMidiDataException {
		List<NotePitch> voicing = chord.getAnnotationDefault(VoicingAnnotation.class)
				.getVoicing(VoicingType.GUITAR_BASIC);
		addElement(service.createRandomizedElement(voicing, start, 0.5 * 0.75, 80));
		addElement(service.createRandomizedElement(voicing, start + 0.5, 0.5 * 0.75, 65));
		if (chord.getAnnotationDefault(ProgressionAnnotation.class).isChangeAfter()) {
			addElement(service.createFretNoise(start + 0.5 + 0.5 * 0.75, 0.5 * 0.25, msPerBar));
		}
	}

	private void addHalfBar(Chord chord, double start, long msPerBar) throws InvalidMidiDataException {
		List<NotePitch> voicing = chord.getAnnotationDefault(VoicingAnnotation.class)
				.getVoicing(VoicingType.GUITAR_BASIC);
		addElement(service.createRandomizedElement(voicing, start, 0.25 * 0.75, 85));
		addElement(service.createRandomizedElement(voicing, start + 0.25, 0.25 * 0.75, 50));
		if (chord.getAnnotationDefault(ProgressionAnnotation.class).isChangeAfter()) {
			addElement(service.createFretNoise(start + 0.25 + 0.25 * 0.75, 0.25 * 0.25, msPerBar));
		}
	}

	private void addQuarterBar(Chord chord, double start, long msPerBar) throws InvalidMidiDataException {
		List<NotePitch> voicing = chord.getAnnotationDefault(VoicingAnnotation.class)
				.getVoicing(VoicingType.GUITAR_BASIC);
		addElement(service.createRandomizedElement(voicing, start, 0.25 * 0.75, 85));
		if (chord.getAnnotationDefault(ProgressionAnnotation.class).isChangeAfter()) {
			addElement(service.createFretNoise(start + 0.25 * 0.75, 0.25 * 0.25, msPerBar));
		}
	}

	private long getMsPerBar(Bar bar) {
		BandAnnotation bandAnnotation = bar.getAnnotationDefault(BandAnnotation.class);
		if ((bandAnnotation.getBand() == null) || (bandAnnotation.getBand().getBeatsPerBar() == 0)
				|| (bandAnnotation.getTempo() == 0)) {
			return Long.MAX_VALUE;
		}
		return Math.round(60000 / bandAnnotation.getTempo() * bandAnnotation.getBand().getBeatsPerBar());
	}

}
