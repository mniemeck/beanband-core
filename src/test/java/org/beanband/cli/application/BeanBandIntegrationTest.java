package org.beanband.cli.application;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.Sequence;

import org.beanband.bandleader.Bandleader;
import org.beanband.cli.parser.LightweightFileParser;
import org.beanband.cli.renderer.MidiFileRenderer;
import org.beanband.model.song.Song;

/**
 * Integration test class to generate all test songs in the Portfolio. It reads
 * all filenames from the test directory, then processes the song file through
 * the following chain
 * <ul>
 * <li>{@link LightweightFileParser}
 * <li>{@link Bandleader}
 * <li>{@link MidiFileRenderer}
 * </ul>
 * 
 * @author Michael Niemeck
 */
public class BeanBandIntegrationTest {

	private static final String RESSOURCE_DIRECTORY = "target/test-classes/songs";

	private final Logger logger;

	/**
	 * Constructs a new {@code BeanBandIntegrationTest} and initializes the
	 * properties of the global {@code Logger}.
	 */
	public BeanBandIntegrationTest() {
		logger = Logger.getLogger(Bandleader.LOGGER_NAME);
		logger.setLevel(Level.ALL);
		logger.setUseParentHandlers(false);
		Handler handler = new ConsoleHandler();
		handler.setLevel(Level.ALL);
		handler.setFormatter(new SimpleFormatter());
		logger.addHandler(handler);
	}

	/**
	 * Runs the main integration test by rendering all song files stored in the
	 * {@code songs} folder.
	 * 
	 * @throws IOException              When there are problems, opening, reading or
	 *                                  writing the input or output {@code File}.
	 * @throws InvalidMidiDataException When the generation of MIDI events runs into
	 *                                  an illegal state.
	 */
	public void testSongPortfolio() throws IOException, InvalidMidiDataException {
		File directory = new File(RESSOURCE_DIRECTORY);
		for (String filename : directory.list((dir, name) -> name.endsWith(".bb"))) {
			String fileroot = directory.getAbsolutePath() + File.separator + filename.split("\\.", 0)[0];
			createPlayback(new File(fileroot + ".bb"), new File(fileroot + ".midi"), new File(fileroot + ".log"));
		}

	}

	private void createPlayback(File infile, File outfile, File logfile) throws IOException, InvalidMidiDataException {
		logger.fine("Parsing file " + infile.getAbsolutePath());
		LightweightFileParser parser = new LightweightFileParser();
		Song song = parser.parse(infile);
		Bandleader bandleader = new Bandleader();
		Sequence sequence = bandleader.perform(song);
		MidiFileRenderer renderer = new MidiFileRenderer();
		renderer.render(sequence, outfile);
		BufferedWriter writer = new BufferedWriter(new FileWriter(logfile, false));
		writer.write(song.toString());
		writer.close();
	}

}
