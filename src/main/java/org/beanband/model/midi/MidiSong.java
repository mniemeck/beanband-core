package org.beanband.model.midi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Top-level class of the MIDI Model. A ...
 * 
 * @author Michael Niemeck
 */
public class MidiSong {

	private final List<MidiBar> bars = new ArrayList<>();

	public MidiBar addBar(double beats, int beatsPerMinute, String label) {

		MidiBar newBar = new MidiBar(beats, beatsPerMinute, label);
		
		bars.add(newBar);

		return newBar;
	}

	public List<MidiBar> getBars() {
		return Collections.unmodifiableList(bars);
	}
	
}
