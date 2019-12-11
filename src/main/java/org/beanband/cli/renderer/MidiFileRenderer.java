package org.beanband.cli.renderer;

import java.io.File;
import java.io.IOException;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;

/**
 * This class performs rendering of a {@code Sequence} into a standard MIDI
 * file.
 * 
 * @author Michael Niemeck
 * @see Sequence
 */
public class MidiFileRenderer {

	/**
	 * Renders the MIDI {@code Sequence} into the specified {@code File}.
	 * 
	 * @param sequence The MIDI {@code Sequence} to render.
	 * @param file     The {@code File} in which to write the {@code Sequence}. Must
	 *                 be existing and writable.
	 * @throws IOException When there are problems opening or writing the
	 *                     {@code File}.
	 */
	public void render(Sequence sequence, File file) throws IOException {
		MidiSystem.write(sequence, 1, file);
	}
}
