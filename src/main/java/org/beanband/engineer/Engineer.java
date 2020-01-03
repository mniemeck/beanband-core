package org.beanband.engineer;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.Sequence;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

import org.beanband.model.midi.MidiBar;
import org.beanband.model.midi.MidiElement;
import org.beanband.model.midi.MidiSong;
import org.beanband.model.midi.MidiTrack;

/**
 * This class is responsible to translate a <em>MIDI Model</em> into an actual,
 * standard-conforming MIDI {@code Sequence}.
 * 
 * @author Michael Niemeck
 *
 */
public class Engineer {

	private static final int PPQ_RESOLUTION = 960;
	private static final int[] INSTRUMENT_CHANNEL = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 10, 11, 12, 13, 14, 15 };
	private static final int PERCUSSION_CHANNEL = 9;

	/**
	 * Reads the information from the MIDI Model represented by a {@code MidiSong}
	 * and transforms it into a single {@code Sequence}. All MIDI events are scaled
	 * into a unified timeline and supporting events (program changes, tempo changes
	 * etc.) are added.
	 * 
	 * @param midiSong The {@code MidiSong} that should be rendered.
	 * @return A single {@code Sequence} containing all events from the MididModel.
	 * @throws InvalidMidiDataException When the generation of MIDI events runs into
	 *                                  an illegal state.
	 */
	public Sequence assemble(MidiSong midiSong) throws InvalidMidiDataException {
		Sequence sequence = new Sequence(Sequence.PPQ, PPQ_RESOLUTION);
		Track tempoTrack = sequence.createTrack();
		Track percussionTrack = sequence.createTrack();
		Track lyricsTrack = sequence.createTrack();

		long tickOffset = 0;
		long currentMsPerBeat = 0;
		Integer[] patchList = new Integer[15];
		Track[] trackList = new Track[15];

		for (MidiBar midiBar : midiSong.getBars()) {
			long ticksPerBar = midiBar.getTicksPerBar(PPQ_RESOLUTION);
			long msPerBeat = midiBar.getMsPerBeat();
			if (msPerBeat != currentMsPerBeat) {
				tempoTrack.add(createTempoChange(msPerBeat, tickOffset));
				currentMsPerBeat = msPerBeat;
			}
			String label = midiBar.getLabel();
			if (label != null) {
				lyricsTrack.add(createLyricEvent(label, tickOffset));
			}
			int currentTrack = 0;
			for (MidiTrack midiTrack : midiBar.getTracks()) {
				int currentChannel = INSTRUMENT_CHANNEL[currentTrack];
				if (trackList[currentChannel] == null) {
					trackList[currentChannel] = sequence.createTrack();
				}
				for (MidiEvent midiEvent : alignMidiEvents(midiTrack.getElements(), currentChannel, ticksPerBar,
						tickOffset)) {
					boolean isEventToAdd = false;
					if (!(midiEvent.getMessage() instanceof ShortMessage)) {
						isEventToAdd = true;
					} else if (((ShortMessage) midiEvent.getMessage()).getCommand() != ShortMessage.PROGRAM_CHANGE) {
						isEventToAdd = true;
					} else {
						int instrumentPatch = ((ShortMessage) midiEvent.getMessage()).getData1();
						if ((patchList[currentChannel] == null) || (patchList[currentChannel] != instrumentPatch)) {
							patchList[currentChannel] = instrumentPatch;
							isEventToAdd = true;
						}
					}

					if (isEventToAdd) {
						trackList[currentChannel].add(midiEvent);
					}
				}
				currentTrack++;
			}
			for (MidiTrack midiTrack : midiBar.getPercussionTracks()) {
				for (MidiEvent midiEvent : alignMidiEvents(midiTrack.getElements(), PERCUSSION_CHANNEL, ticksPerBar,
						tickOffset)) {
					percussionTrack.add(midiEvent);
				}
			}
			tickOffset += ticksPerBar;
		}
		return sequence;
	}

	private MidiEvent createTempoChange(long msPerBeat, long tick) throws InvalidMidiDataException {
		byte tempoChangeType = 0x51;
		byte[] tempoChange = new byte[3];
		tempoChange[0] = (byte) ((msPerBeat & 0xff0000) >> 16);
		tempoChange[1] = (byte) ((msPerBeat & 0x00ff00) >> 8);
		tempoChange[2] = (byte) (msPerBeat & 0x0000ff);
		MidiMessage midiMessage = new MetaMessage(tempoChangeType, tempoChange, tempoChange.length);
		return new MidiEvent(midiMessage, tick);
	}

	private MidiEvent createLyricEvent(String text, long tick) throws InvalidMidiDataException {
		byte lyricType = 0x05;
		byte[] lyrics = text.getBytes(StandardCharsets.UTF_8);
		MidiMessage midiMessage = new MetaMessage(lyricType, lyrics, lyrics.length);
		return new MidiEvent(midiMessage, tick);
	}

	private Collection<MidiEvent> alignMidiEvents(Collection<MidiElement> midiElements, int currentChannel,
			long ticksPerBar, long tickOffset) throws InvalidMidiDataException {
		Collection<MidiEvent> midiEvents = new ArrayList<>();
		for (MidiElement midiElement : midiElements) {
			for (MidiEvent midiEvent : midiElement.getMidiEvents(currentChannel, ticksPerBar)) {
				midiEvent.setTick(Math.max(0, midiEvent.getTick() + tickOffset));
				midiEvents.add(midiEvent);
			}
		}
		return midiEvents;
	}

}
