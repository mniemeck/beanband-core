package org.beanband.cli.renderer;

import java.io.File;
import java.io.IOException;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.Soundbank;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.Transmitter;

/**
 * Experimental renderer that outputs a {@code Sequence} directly through the
 * sound system. At the moment it is <strong>not recommended</strong> to use
 * this class for serious testing! We are using the <em>Gervill</em> synthesizer
 * that is shipped with Java. This means we have no external dependencies and no
 * licensing issues, but the synthesizer has some severe limitations:
 * <ul>
 * <li>The internal API is not accessible, which severly limits the
 * possibilities of configuration. For one thing, we are restricted to use the
 * system's default audio device.
 * <li>The Gervill synthesizer seems to have some performance issues on a
 * non-optimized system, resulting for example in missed notes.
 * <li>The default soundbank is of an extremely low quality. In order to have at
 * least some reasonable output, you would need to load a third-party soundbank,
 * which again might lead to re-distribution/licensing issues.
 * </ul>
 * To summarize, don't use this class yet (it is just here to showcase the
 * possibilities and limits of the standard JAVA API), but rather use some
 * third-party tool to listen to the MIDI files.
 * <p>
 * <strong>FluidSynth</strong> (http://www.fluidsynth.org) in combination with
 * the <em>Fluid (R3) General MIDI SoundFont</em> is highly recommended.
 * 
 * @author Michael Niemeck
 *
 */
public class SoftSynthesizerRenderer {

	private static final String SOUNDBANK_FILE = "/usr/share/sounds/sf2/FluidR3_GM.sf2";

	/**
	 * Renders the MIDI {@code Sequence} into the specified {@code File}.
	 * 
	 * @param sequence The MIDI {@code Sequence} to render.
	 * @param file     The {@code File} in which to write the {@code Sequence}. Must
	 *                 be existing and writable.
	 * @throws MidiUnavailableException When there are problems obtaining the
	 *                                  correct MIDI devices.
	 * @throws InvalidMidiDataException When there are problems in the
	 *                                  {@code Sequencer} when rendering the
	 *                                  {@code Sequence}.
	 * @throws IOException              When there are problems opening or writing
	 *                                  the {@code File}.
	 */
	public void render(Sequence sequence) throws MidiUnavailableException, InvalidMidiDataException, IOException {

		Synthesizer synthesizer = findSynthesizer();
		Sequencer sequencer = findSequencer();

		synthesizer.open();
		sequencer.open();

		Soundbank soundbank = findSoundbank(synthesizer);
		if (soundbank != null) {
			synthesizer.loadAllInstruments(soundbank);
		}

		Receiver receiver = synthesizer.getReceiver();
		Transmitter transmitter = sequencer.getTransmitter();
		transmitter.setReceiver(receiver);

		sequencer.setSequence(sequence);
		sequencer.start();
	}

	private Sequencer findSequencer() throws MidiUnavailableException {
		MidiDevice.Info[] midiDeviceInfos = MidiSystem.getMidiDeviceInfo();
		for (MidiDevice.Info info : midiDeviceInfos) {
			MidiDevice midiDevice = MidiSystem.getMidiDevice(info);
			if (midiDevice instanceof Sequencer) {
				return (Sequencer) midiDevice;
			}
		}
		return null;
	}

	private Synthesizer findSynthesizer() throws MidiUnavailableException {
		MidiDevice.Info[] midiDeviceInfos = MidiSystem.getMidiDeviceInfo();
		for (MidiDevice.Info info : midiDeviceInfos) {
			MidiDevice midiDevice = MidiSystem.getMidiDevice(info);
			if (midiDevice instanceof Synthesizer) {
				return (Synthesizer) midiDevice;
			}
		}
		return null;
	}

	private Soundbank findSoundbank(Synthesizer synthesizer) throws InvalidMidiDataException, IOException {
		Soundbank soundbank;

		soundbank = MidiSystem.getSoundbank(new File(SOUNDBANK_FILE));

		if ((soundbank != null) && synthesizer.isSoundbankSupported(soundbank)) {
			return soundbank;
		}
		return null;
	}

}
