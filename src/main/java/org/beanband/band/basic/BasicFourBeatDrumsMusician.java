package org.beanband.band.basic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import javax.sound.midi.InvalidMidiDataException;

import org.beanband.band.PercussionMusician;
import org.beanband.model.midi.MidiElement;
import org.beanband.model.midi.MidiPercussionElement;
import org.beanband.model.midi.PercussionKey;
import org.beanband.model.music.FormAnnotation;
import org.beanband.model.song.Bar;

/**
 * The drummer of our showcase band. It plays a simple straight four-beat groove
 * with a slight variation based on the number of changes per bar. Also has a
 * special groove for N.C. bars. In addition, as a {@code LeadMusician}, it can
 * render the classic two-bar count-in.
 * 
 * @author Michael Niemeck
 *
 */
public class BasicFourBeatDrumsMusician extends PercussionMusician {
	private static final double COUNT_IN_BEATS = 8;

	@Override
	protected void createElements(Bar bar) throws InvalidMidiDataException {
		FormAnnotation annotation = bar.getAnnotation(FormAnnotation.class);
		if ((annotation != null) && annotation.isNoChord()) {
			addElement(new MidiPercussionElement(PercussionKey.ACOUSTIC_BASS_DRUM, 0.0, 120, 127));
			addElement(new MidiPercussionElement(PercussionKey.ACOUSTIC_SNARE, 0.625, 120, 127));
			addElement(new MidiPercussionElement(PercussionKey.ACOUSTIC_SNARE, 0.75, 120, 127));
		} else {
			switch (bar.getChords().size()) {
			case 1:
				addKickStrongHalfBar(0.0);
				addSnareWeakHalfBar(0.0);
				addHiHatWeakHalfBar(0.0);
	
				addKickWeakHalfBar(0.5);
				addSnareWeakHalfBar(0.5);
				addHiHatStrongHalfBar(0.5);
				break;
			case 2:
				addKickStrongHalfBar(0.0);
				addSnareWeakHalfBar(0.0);
				addHiHatStrongHalfBar(0.0);
	
				addKickStrongHalfBar(0.5);
				addSnareStrongHalfBar(0.5);
				addHiHatStrongHalfBar(0.5);
				break;
			}
		}
		if ((annotation != null) && annotation.isLastBar()) {
			addElement(new MidiPercussionElement(PercussionKey.CRASH_CYMBAL_1, 1.0, 120, 127));
		}
	}

	private void addKickStrongHalfBar(double start) throws InvalidMidiDataException {
		addElement(new MidiPercussionElement(PercussionKey.ACOUSTIC_BASS_DRUM, start, 120, 127));
		addElement(new MidiPercussionElement(PercussionKey.ACOUSTIC_BASS_DRUM, start + 0.375, 100, 127));
	}

	private void addKickWeakHalfBar(double start) throws InvalidMidiDataException {
		addElement(new MidiPercussionElement(PercussionKey.ACOUSTIC_BASS_DRUM, start, 110, 127));
	}

	private void addSnareWeakHalfBar(double start) throws InvalidMidiDataException {
		addElement(new MidiPercussionElement(PercussionKey.ACOUSTIC_SNARE, start + 0.25, 110, 127));
	}

	private void addSnareStrongHalfBar(double start) throws InvalidMidiDataException {
		addElement(new MidiPercussionElement(PercussionKey.ACOUSTIC_SNARE, start + 0.25, 110, 127));
		addElement(new MidiPercussionElement(PercussionKey.ACOUSTIC_SNARE, start + 0.375, 90, 127));
	}

	private void addHiHatWeakHalfBar(double start) throws InvalidMidiDataException {
		addElement(new MidiPercussionElement(PercussionKey.CLOSED_HI_HAT, start + 0.0, 100, 127));
		addElement(new MidiPercussionElement(PercussionKey.CLOSED_HI_HAT, start + 0.125, 90, 127));
		addElement(new MidiPercussionElement(PercussionKey.CLOSED_HI_HAT, start + 0.25, 92, 127));
		addElement(new MidiPercussionElement(PercussionKey.CLOSED_HI_HAT, start + 0.375, 91, 127));
	}

	private void addHiHatStrongHalfBar(double start) throws InvalidMidiDataException {
		addElement(new MidiPercussionElement(PercussionKey.CLOSED_HI_HAT, start + 0.0, 110, 127));
		addElement(new MidiPercussionElement(PercussionKey.CLOSED_HI_HAT, start + 0.125, 91, 127));
		addElement(new MidiPercussionElement(PercussionKey.CLOSED_HI_HAT, start + 0.25, 90, 127));
		addElement(new MidiPercussionElement(PercussionKey.OPEN_HI_HAT, start + 0.375, 92, 127));
	}

	@Override
	public Collection<MidiElement> getCountIn() throws InvalidMidiDataException {
		Collection<MidiElement> elements = new ArrayList<>();
		elements.add(new MidiPercussionElement(PercussionKey.SIDE_STICK, 0.0, 127, 127));
		elements.add(new MidiPercussionElement(PercussionKey.SIDE_STICK, 0.25, 127, 127));
		elements.add(new MidiPercussionElement(PercussionKey.SIDE_STICK, 0.5, 127, 127));
		elements.add(new MidiPercussionElement(PercussionKey.SIDE_STICK, 0.625, 127, 127));
		elements.add(new MidiPercussionElement(PercussionKey.SIDE_STICK, 0.75, 127, 127));
		elements.add(new MidiPercussionElement(PercussionKey.SIDE_STICK, 0.875, 127, 127));
		return Collections.unmodifiableCollection(elements);
	}

	@Override
	public double getCountInBeats() {
		return COUNT_IN_BEATS;
	}

}
