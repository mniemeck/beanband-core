package org.beanband.cli.application;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.Sequence;

import org.beanband.bandleader.Bandleader;
import org.beanband.cli.parser.LightweightFileParser;
import org.beanband.cli.renderer.MidiFileRenderer;
import org.beanband.model.song.Song;

/**
 * Main class of the sample command-line application. It reads the filenames
 * from the command line and then processes the song file through the following
 * chain
 * <ul>
 * <li>{@link LightweightFileParser}
 * <li>{@link Bandleader}
 * <li>{@link MidiFileRenderer}
 * </ul>
 * {@code BeanBand} takes two commandline arguments, one source file containing
 * the song definition, and one target file, where the MIDI sequence will be
 * written two. The resulting MIDI file can be directly rendered with any
 * suitable MIDI player software.
 * 
 * @author Michael Niemeck
 *
 */
public class BeanBand {
	private final Logger logger = Logger.getLogger(Bandleader.LOGGER_NAME);

	private BeanBand() {
		// Class should not be instatiated from outside.
	}

	/**
	 * Main method of the command line application.
	 * 
	 * @param args Command-line arguments: {@code <infile> <outfile>}
	 */
	public static void main(String[] args) {
		BeanBand beanBand = new BeanBand();
		File[] files = beanBand.processCommandline(args);
		if (files == null) {
			return;
		}
		beanBand.createPlayback(files[0], files[1]);
	}

	private File[] processCommandline(String[] args) {
		File files[] = new File[2];

		if (args.length < 2) {
			logger.severe("Usage: java -jar BeanBand.jar <infile> <outfile>");
			return null;
		}
		files[0] = new File(args[0]);
		if (!isReadable(files[0])) {
			return null;
		}
		files[1] = new File(args[1]);
		if (!isWritable(files[1])) {
			return null;
		}
		logger.config("Commandline processed: Infile=" + files[0].getPath() + " Outfile=" + files[1].getPath());

		return files;
	}

	private boolean isReadable(File file) {
		if (!file.canRead()) {
			logger.severe("The file " + file.getPath() + " is not readable.");
			return false;
		}
		return true;
	}

	private boolean isWritable(File file) {
		if (!file.canWrite()) {
			if (file.exists()) {
				logger.severe("The file " + file.getPath() + " is not writable.");
				return false;
			} else {
				try {
					file.createNewFile();
				} catch (IOException e) {
					logger.severe("The file " + file.getPath() + " could not be created: " + e.getMessage());
					return false;
				}
			}
		}
		return true;
	}

	private void createPlayback(File infile, File outfile) {
		try {
			logger.fine("Parsing file " + infile.getAbsolutePath());
			LightweightFileParser parser = new LightweightFileParser();
			Song song = parser.parse(infile);
			Bandleader bandleader = new Bandleader();
			Sequence sequence = bandleader.perform(song);
			MidiFileRenderer renderer = new MidiFileRenderer();
			renderer.render(sequence, outfile);
		} catch (IOException | InvalidMidiDataException e) {
			logger.severe("Error during playback creation: " + e.getMessage());
		}
	}
}
