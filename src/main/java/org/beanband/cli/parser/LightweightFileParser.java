package org.beanband.cli.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Consumer;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.beanband.model.song.Bar;
import org.beanband.model.song.Chord;
import org.beanband.model.song.Eleventh;
import org.beanband.model.song.Fifth;
import org.beanband.model.song.Ninth;
import org.beanband.model.song.Note;
import org.beanband.model.song.Seventh;
import org.beanband.model.song.Song;
import org.beanband.model.song.SongElement;
import org.beanband.model.song.Third;
import org.beanband.model.song.Thirteenth;

/**
 * This class performs parsing of lightweight text files containing song
 * information, more specifically chord symbols enriched with some style
 * information - so-called <em>leadsheets</em>. Such a leadsheet file might
 * look something like this
 * 
 * <pre>
 * [BasicFourBeat:80] // Everything following "//" is treated as a comment
 * // Empty lines produce a bar with no chord
 * D.min
 * D.min./C
 * Bb.maj7 Ab.maj7
 * G.7.sus4 C.7.sus4
 * </pre>
 * 
 * It contains one bar per line, and can contain multiple chords per bar. Before
 * every bar it is possible to switch style (which will select a different Band)
 * and provide tempo information. Note that a lot of information that you would
 * expect can <em>not</em> be provided, since it will be determined by the band:
 * <dl>
 * <dt>Meter information and absoulte tempo
 * <dd>Each Band plays in a specific style and with that also in a specific
 * meter. This meter also determines how the tempo information is interpreted.
 * <dt>Volume information
 * <dd>At least for now, each Band handles it's volume autonomously.
 * <dt>Timing information on chord changes
 * <dd>Each Band can only handle chord changes at specific points in time. In
 * addition, each Band defines how many chord changes it can handle within one
 * single bar. Excess chords will be ignored.
 * </dl>
 * The end result of parsing is the so-called <em>Song Model</em>.
 * 
 * @author Michael Niemeck
 * @see Song
 */
public class LightweightFileParser {

	private final static String commentSeparatorPattern = "\\s*//\\s*";
	private final static Pattern stylePattern = Pattern.compile("\\[(\\w*):(\\d*)\\]");

	private final Map<Pattern, Consumer<Chord>> componentPatternMap;

	/**
	 * Constructs a new {@code LightweightFileParser} object.
	 */
	public LightweightFileParser() {
		this.componentPatternMap = createComponentPatternMap();
	}

	private static Map<Pattern, Consumer<Chord>> createComponentPatternMap() {
		Map<String, Note> notes = new HashMap<>();
		notes.put("Cb", Note.Cb);
		notes.put("C", Note.C);
		notes.put("C#", Note.Cs);
		notes.put("Db", Note.Db);
		notes.put("D", Note.D);
		notes.put("D#", Note.Ds);
		notes.put("Eb", Note.Eb);
		notes.put("E", Note.E);
		notes.put("E#", Note.Es);
		notes.put("Fb", Note.Fb);
		notes.put("F", Note.F);
		notes.put("F#", Note.Fs);
		notes.put("Gb", Note.Gb);
		notes.put("G", Note.G);
		notes.put("G#", Note.Gs);
		notes.put("Ab", Note.Ab);
		notes.put("A", Note.A);
		notes.put("A#", Note.As);
		notes.put("Bb", Note.Bb);
		notes.put("B", Note.B);
		notes.put("B#", Note.Bs);

		Map<Pattern, Consumer<Chord>> map = new HashMap<>();

		map.put(Pattern.compile("^$"), c -> {
			Logger.getGlobal().fine("Blank line encountered. Empty chord will be added.");
		});

		notes.forEach((s, n) -> {
			map.put(Pattern.compile("^" + s + "$"), c -> {
				setDefaultOptions(c, n);
			});
		});
		map.put(Pattern.compile("^min$"), c -> {
			c.setThird(Third.MIN);
		});
		map.put(Pattern.compile("^sus2$"), c -> {
			c.setThird(Third.SUS2);
		});
		map.put(Pattern.compile("^sus4$"), c -> {
			c.setThird(Third.SUS4);
		});
		map.put(Pattern.compile("^b5$"), c -> {
			c.setFifth(Fifth.DIM);
		});
		map.put(Pattern.compile("^aug"), c -> {
			c.setThird(Third.MAJ);
			c.setFifth(Fifth.AUG);
		});
		map.put(Pattern.compile("^6$"), c -> {
			c.setSeventh(Seventh.SIXTH);
		});
		map.put(Pattern.compile("^7$"), c -> {
			c.setSeventh(Seventh.MIN);
		});
		map.put(Pattern.compile("^maj7$"), c -> {
			c.setSeventh(Seventh.MAJ);
		});
		map.put(Pattern.compile("^dim$"), c -> {
			c.setThird(Third.MIN);
			c.setFifth(Fifth.DIM);
		});
		map.put(Pattern.compile("^dim7$"), c -> {
			c.setThird(Third.MIN);
			c.setFifth(Fifth.DIM);
			c.setSeventh(Seventh.SIXTH);
		});
		map.put(Pattern.compile("^9$"), c -> {
			c.setNinth(Ninth.MAJ);
		});
		map.put(Pattern.compile("^b9$"), c -> {
			c.setNinth(Ninth.MIN);
		});
		map.put(Pattern.compile("^#9$"), c -> {
			c.setNinth(Ninth.AUG);
		});
		map.put(Pattern.compile("^11$"), c -> {
			c.setEleventh(Eleventh.PERFECT);
		});
		map.put(Pattern.compile("^b11$"), c -> {
			c.setEleventh(Eleventh.DIM);
		});
		map.put(Pattern.compile("^#11$"), c -> {
			c.setEleventh(Eleventh.AUG);
		});
		map.put(Pattern.compile("^13$"), c -> {
			c.setThirteenth(Thirteenth.MAJ);
		});
		map.put(Pattern.compile("^b13$"), c -> {
			c.setThirteenth(Thirteenth.MIN);
		});
		map.put(Pattern.compile("^#13$"), c -> {
			c.setThirteenth(Thirteenth.AUG);
			;
		});
		notes.forEach((s, n) -> {
			map.put(Pattern.compile("^/" + s + "$"), c -> {
				c.setBass(n);
			});
		});
		return map;
	}

	private static void setDefaultOptions(Chord chord, Note root) {
		chord.setRoot(root);
		chord.setThird(Third.MAJ);
		chord.setFifth(Fifth.PERFECT);
	}

	/**
	 * Parses the specified {@code File} and creates the corresponding Song Model.
	 * 
	 * @param file The {@code File} that should be parsed. Must be readable.
	 * @return The {@code Song} representing the Song Model of the leadsheet from
	 *         the input file.
	 * @throws IOException When there are problems opening or reading the
	 *                     {@code File}.
	 */
	public Song parse(File file) throws IOException {
		Song song = new Song();

		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			reader.lines().forEach(line -> processLine(line, song));
		}

		return song;
	}

	private void processLine(String line, Song song) {
		String[] components = line.split(commentSeparatorPattern, 2);
		Matcher styleMatcher = stylePattern.matcher(components[0]);
		SongElement songElement = null;
		if (styleMatcher.matches()) {
			String style = styleMatcher.group(1);
			Integer tempo = Integer.valueOf(styleMatcher.group(2));
			songElement = song.addStyleChange(style, tempo);
		} else {
			songElement = song.addBar();
			for (String chord : components[0].split("\\s")) {
				processChord(chord, (Bar) songElement);
			}
		}
		if ((components.length > 1) && (songElement != null)) {
			songElement.getOrCreateAnnotation(ParserCommentAnnotation.class).setComment(components[1]);
		}
	}

	private void processChord(String chordString, Bar bar) {
		Chord chord = bar.addChord();
		for (String chordComponent : chordString.split("\\.")) {
			processChordComponent(chordComponent, chord);
		}
	}

	private void processChordComponent(String chordComponent, Chord chord) {
		for (Entry<Pattern, Consumer<Chord>> entry : componentPatternMap.entrySet()) {
			if (entry.getKey().matcher(chordComponent).matches()) {
				entry.getValue().accept(chord);
				return;
			}
		}
		Logger.getGlobal().warning("Pattern " + chordComponent + " could not be interpreted");
	}
}
