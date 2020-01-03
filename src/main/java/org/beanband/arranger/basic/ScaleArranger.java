package org.beanband.arranger.basic;

import javax.sound.midi.InvalidMidiDataException;

import org.beanband.arranger.Arranger;
import org.beanband.model.music.ScaleAnnotation;
import org.beanband.model.music.ScaleAnnotation.ScaleType;
import org.beanband.model.song.Bar;
import org.beanband.model.song.Chord;
import org.beanband.model.song.Fifth;
import org.beanband.model.song.Note;
import org.beanband.model.song.Song;

/**
 * Calculates Scale Notes and other functions based on a given {@code Chord}.
 * 
 * @author Michael Niemeck
 * @see ScaleAnnotation
 */
public class ScaleArranger extends Arranger {

	@Override
	public void annotate(Song song) throws InvalidMidiDataException {
		for (Bar bar : song.getBars()) {
			for (Chord chord : bar.getChords()) {
				annotateBassNote(chord);
				annotateChordNotes(chord);
			}
		}

		// TODO Enhance to calculate SCALE_NOTES. Construct based on chord functions.
		// Default tones based on 3 and 5, altered by chord functions.

		// TODO Enhance to include various function (e.g. lead) notes based on context,
		// maybe based on ProgressionAnnotation.
	}

	private void annotateBassNote(Chord chord) {
		Note bassNote = (chord.getBass() != null ? chord.getBass() : chord.getRoot());
		if (bassNote != null) {
			chord.getOrCreateAnnotation(ScaleAnnotation.class).addNote(ScaleType.BASS_NOTE, bassNote);
		}
	}

	private void annotateChordNotes(Chord chord) {
		Note rootNote = chord.getRoot();
		if (rootNote == null) {
			return;
		}
		ScaleAnnotation scaleAnnotation = chord.getOrCreateAnnotation(ScaleAnnotation.class);
		if (chord.getThird() != null) {
			scaleAnnotation.addNote(ScaleType.CHORD_NOTES, getNote(rootNote, chord.getThird().getNumber()));
		}
		if ((chord.getFifth() != null) && (chord.getFifth() != Fifth.PERFECT)) {
			scaleAnnotation.addNote(ScaleType.CHORD_NOTES, getNote(rootNote, chord.getFifth().getNumber()));
		}
		if (chord.getSeventh() != null) {
			scaleAnnotation.addNote(ScaleType.CHORD_NOTES, getNote(rootNote, chord.getSeventh().getNumber()));
		}
		if (chord.getNinth() != null) {
			scaleAnnotation.addNote(ScaleType.CHORD_NOTES, getNote(rootNote, chord.getNinth().getNumber()));
		}
		if (chord.getEleventh() != null) {
			scaleAnnotation.addNote(ScaleType.CHORD_NOTES, getNote(rootNote, chord.getEleventh().getNumber()));
		}
		if (chord.getThirteenth() != null) {
			scaleAnnotation.addNote(ScaleType.CHORD_NOTES, getNote(rootNote, chord.getThirteenth().getNumber()));
		}
		if ((chord.getFifth() != null) && (chord.getFifth() == Fifth.PERFECT)) {
			scaleAnnotation.addNote(ScaleType.CHORD_NOTES, getNote(rootNote, chord.getFifth().getNumber()));
		}
		scaleAnnotation.addNote(ScaleType.CHORD_NOTES, rootNote);
	}

	private Note getNote(Note rootNote, int intervalOffset) {
		Note[] standardNotesSharp = { Note.C, Note.Cs, Note.D, Note.Ds, Note.E, Note.F, Note.Fs, Note.G, Note.Gs,
				Note.A, Note.As, Note.B };
		Note[] standardNotesFlat = { Note.C, Note.Db, Note.D, Note.Eb, Note.E, Note.F, Note.Gb, Note.G, Note.Ab, Note.A,
				Note.Bb, Note.B };
		int pitch = (rootNote.getNumber() + intervalOffset) % 12;
		switch (rootNote) {
		case F:
		case Bb:
		case Eb:
		case Ab:
		case Db:
		case Gb:
		case Cb:
		case Fb:
			return standardNotesFlat[pitch];
		default:
			return standardNotesSharp[pitch];
		}
	}

	@Override
	protected int getPriority() {
		return Integer.MAX_VALUE - 20;
	}
}
