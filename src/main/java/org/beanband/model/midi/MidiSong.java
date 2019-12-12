package org.beanband.model.midi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Top-level class of the MIDI Model. A {@code MidiSong} represents the playback
 * that has been generated from a single {@code Song} object. It is a collection
 * of {@code MidiBar} objects.
 * 
 * @author Michael Niemeck
 * 
 */
public class MidiSong {

	private final List<MidiBar> bars = new ArrayList<>();

	/**
	 * Creates a new {@code MidiBar} and attaches it to the list of bars of this
	 * {@code MidiSong}.
	 * 
	 * @param beats          The number of beats that the new bar should have.
	 * @param beatsPerMinute The tempo of this bar in <em>beats per minute</em>.
	 * @param label          The label of this bar. Labels are added as lyric events
	 *                       to the final {@code Sequence} and typicall contain the
	 *                       chord symbols of the changes in this bar.
	 * @return The newly created {@code Bar}.
	 */
	public MidiBar addBar(double beats, int beatsPerMinute, String label) {

		MidiBar newBar = new MidiBar(beats, beatsPerMinute, label);

		bars.add(newBar);

		return newBar;
	}

	/**
	 * Returns all {@code MidiBar} elements of this {@code MidiSong} in the order in
	 * which they were added.
	 *
	 * @return An unmodifiable {@code List} of all {@code MidiBar} elements.
	 */
	public List<MidiBar> getBars() {
		return Collections.unmodifiableList(bars);
	}

}
