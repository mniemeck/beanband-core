package org.beanband.band.basic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import javax.sound.midi.InvalidMidiDataException;

import org.beanband.band.LeadMusician;
import org.beanband.band.PercussionMusician;
import org.beanband.model.midi.MidiElement;
import org.beanband.model.midi.MidiPercussionElement;
import org.beanband.model.midi.PercussionKey;
import org.beanband.model.music.FormAnnotation;
import org.beanband.model.song.Bar;

public class BasicFourBeatDrumsMusician extends PercussionMusician implements LeadMusician {
	private final static double COUNT_IN_BEATS = 8;


	@Override
	protected void createElements(Bar bar) throws InvalidMidiDataException {
		FormAnnotation annotation = bar.getAnnotation(FormAnnotation.class);
		if ((annotation != null) && (annotation.isNoChord())) {
			addElement(new MidiPercussionElement(PercussionKey.ACOUSTIC_BASS_DRUM, 0.0, 120, 127));
			addElement(new MidiPercussionElement(PercussionKey.ACOUSTIC_SNARE, 0.625, 120, 127));
			addElement(new MidiPercussionElement(PercussionKey.ACOUSTIC_SNARE, 0.75, 120, 127));
		} else {
			addElement(new MidiPercussionElement(PercussionKey.CLOSED_HI_HAT, 0.0, 90, 127));
			addElement(new MidiPercussionElement(PercussionKey.CLOSED_HI_HAT, 0.125, 90, 127));
			addElement(new MidiPercussionElement(PercussionKey.CLOSED_HI_HAT, 0.25, 90, 127));
			if (bar.getChords().size() > 1) {
				addElement(new MidiPercussionElement(PercussionKey.OPEN_HI_HAT, 0.375, 90, 127));
			} else {
				addElement(new MidiPercussionElement(PercussionKey.CLOSED_HI_HAT, 0.375, 90, 127));
			}
			addElement(new MidiPercussionElement(PercussionKey.CLOSED_HI_HAT, 0.5, 90, 127));
			addElement(new MidiPercussionElement(PercussionKey.CLOSED_HI_HAT, 0.625, 90, 127));
			addElement(new MidiPercussionElement(PercussionKey.CLOSED_HI_HAT, 0.75, 90, 127));
			addElement(new MidiPercussionElement(PercussionKey.OPEN_HI_HAT, 0.875, 90, 127));
			addElement(new MidiPercussionElement(PercussionKey.ACOUSTIC_BASS_DRUM, 0.0, 120, 127));
			addElement(new MidiPercussionElement(PercussionKey.ACOUSTIC_BASS_DRUM, 0.5, 110, 127));
			addElement(new MidiPercussionElement(PercussionKey.ACOUSTIC_SNARE, 0.25, 110, 127));
			addElement(new MidiPercussionElement(PercussionKey.ACOUSTIC_SNARE, 0.75, 110, 127));
		}
		if ((annotation != null) && (annotation.isLastBar())) {
			addElement(new MidiPercussionElement(PercussionKey.CRASH_CYMBAL_1, 1.0, 120, 127));
		}
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
