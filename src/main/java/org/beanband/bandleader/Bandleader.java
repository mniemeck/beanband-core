package org.beanband.bandleader;

import java.util.ServiceLoader;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.logging.Logger;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.Sequence;

import org.beanband.arranger.Arranger;
import org.beanband.band.Band;
import org.beanband.band.Musician;
import org.beanband.band.PercussionMusician;
import org.beanband.engineer.Engineer;
import org.beanband.model.midi.MidiBar;
import org.beanband.model.midi.MidiSong;
import org.beanband.model.midi.MidiTrack;
import org.beanband.model.music.BandAnnotation;
import org.beanband.model.music.MusicAnnotation;
import org.beanband.model.music.WarningAnnotation;
import org.beanband.model.song.Bar;
import org.beanband.model.song.Chord;
import org.beanband.model.song.Song;
import org.beanband.model.song.SongElement;
import org.beanband.model.song.StyleChange;

/**
 * This is the central class orchestrating the various model layers and
 * processing stages of <em>BeanBand</em>. The process is as follows.
 * <ol>
 * <li>The input is a <em>Song Model</em> as represented by the {@code Song}
 * class.
 * <li>The {@code Bandleader} calls all registered {@code Arranger}
 * implementations to annotate the Song Model.
 * <li>The annotated Song Model is called the <em>Music Model</em>, represented
 * by {@code MusicAnnotation} objects attached to the Song Model.
 * <li>The {@code Bandleader} iterates through all {@code Band} and
 * {@code Musician} objects and calls them to play the song.
 * <li>The result is the <em>MIDI Model</em>, represented by a {@code MidiSong}
 * object.
 * <li>Finally, the {@code Engineer} is called to transform the MIDI Model into
 * a {@code Sequence}.
 * <li>The output is a {@code Sequence} ready for rendering.
 * <li>In addition, the {@code Bandleader} writes all {@code WarningAnnotation}
 * objects from the Music Model to the global {@code Logger}.
 * </ol>
 * 
 * @author Michael Niemeck
 * @see Song
 * @see Arranger
 * @see MusicAnnotation
 * @see Band
 * @see MidiSong
 * @see Engineer
 * @see Sequence
 */
public class Bandleader {

	/**
	 * The name of the {@code Logger} to use throughout the library. Use
	 * {@code Logger.getLogger(Bandleader.LOGGER_NAME)} to acccess is.
	 */
	public static final String LOGGER_NAME = "org.beanband";

	/**
	 * Renders the specified {@code Song} into a {@code Sequence} by calling the
	 * various stages of the <em>BeanBand</em>.
	 * 
	 * @param song The {@code Song} to perform.
	 * @return The {@code Sequence} rendered by the Band.
	 * @throws InvalidMidiDataException When the generation of MIDI events runs into
	 *                                  an illegal state.
	 */
	public Sequence perform(Song song) throws InvalidMidiDataException {
		arrange(song);
		logWarnings(song, Logger.getLogger(LOGGER_NAME));
		MidiSong midiSong = play(song);
		Sequence sequence = assemble(midiSong);
		return sequence;
	}

	private void arrange(Song song) throws InvalidMidiDataException {
		SortedSet<Arranger> arrangers = new TreeSet<>();
		ServiceLoader<Arranger> serviceLoader = ServiceLoader.load(Arranger.class);
		serviceLoader.forEach(a -> arrangers.add(a));
		for (Arranger arranger : arrangers) {
			arranger.annotate(song);
		}
	}

	private void logWarnings(Song song, Logger logger) {
		for (SongElement element : song.getElements()) {
			int lineNumber = song.getElements().indexOf(element) + 1;
			if (element instanceof Bar) {
				Bar bar = (Bar) element;
				if (bar.getAnnotation(WarningAnnotation.class) != null) {
					bar.getAnnotation(WarningAnnotation.class).getMessages()
							.forEach(m -> logger.warning("Bar " + lineNumber + ": " + m));
				}
				bar.getChords().forEach(c -> {
					int chordNumber = bar.getChords().indexOf(c) + 1;
					if (c.getAnnotation(WarningAnnotation.class) != null) {
						c.getAnnotation(WarningAnnotation.class).getMessages()
								.forEach(m -> logger.warning("Bar " + lineNumber + ":Chord " + chordNumber + ": " + m));
					}
				});
			} else if (element instanceof StyleChange) {
				StyleChange styleChange = (StyleChange) element;
				if (styleChange.getAnnotation(WarningAnnotation.class) != null) {
					styleChange.getAnnotation(WarningAnnotation.class).getMessages()
							.forEach(m -> logger.warning("Style Change " + lineNumber + ": " + m));
				}
			}
		}
	}

	private MidiSong play(Song song) throws InvalidMidiDataException {
		MidiSong midiSong = new MidiSong();
		if (!song.getBars().isEmpty()) {
			Bar firstBar = song.getBars().get(0);
			Band band = firstBar.getAnnotation(BandAnnotation.class).getBand();
			PercussionMusician leadMusician = band.getLeadMusician();
			if (leadMusician != null) {
				Integer tempo = firstBar.getAnnotation(BandAnnotation.class).getTempo();
				MidiBar midiBar = midiSong.addBar(leadMusician.getCountInBeats(), tempo, null);
				MidiTrack midiTrack = midiBar.addPercussionTrack();
				midiTrack.addElements(leadMusician.getCountIn());
			}
		}
		for (Bar bar : song.getBars()) {
			Band band = bar.getAnnotation(BandAnnotation.class).getBand();
			Integer tempo = bar.getAnnotation(BandAnnotation.class).getTempo();
			MidiBar midiBar = midiSong.addBar(band.getBeatsPerBar(), tempo, getBarLabel(bar));
			for (Musician musician : band.getMusicians()) {
				MidiTrack midiTrack;
				if (musician instanceof PercussionMusician) {
					midiTrack = midiBar.addPercussionTrack();
				} else {
					midiTrack = midiBar.addTrack();
				}
				midiTrack.addElements(musician.play(bar));
			}
		}
		return midiSong;
	}

	private String getBarLabel(Bar bar) {
		String label = "|";
		for (Chord chord : bar.getChords()) {
			label += " " + chord.getSymbol();
		}
		label += " |";
		return label;
	}

	private Sequence assemble(MidiSong midiSong) throws InvalidMidiDataException {
		Engineer engineer = new Engineer();
		Sequence sequence = engineer.assemble(midiSong);
		return sequence;
	}
}
