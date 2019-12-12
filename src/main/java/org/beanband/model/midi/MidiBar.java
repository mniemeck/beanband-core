package org.beanband.model.midi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The {@code MidiBar} represents one bar of music in the playback. It collects
 * MIDI events in a list of {@code MidiTrack} objects.
 * 
 * @author Michael Niemeck
 *
 */
public class MidiBar {

	private final List<MidiTrack> tracks = new ArrayList<>();
	private final List<MidiTrack> percussionTracks = new ArrayList<>();

	private final double beats;
	private final int beatsPerMinute;
	private final String label;

	/**
	 * Constructs a new {@code MidiBar} object.
	 * 
	 * @param beats          The number of beats that the new bar should have.
	 * @param beatsPerMinute The tempo of this bar in <em>beats per minute</em>.
	 * @param label          The label of this bar. Labels are added as lyric events
	 *                       to the final {@code Sequence} and typicall contain the
	 *                       chord symbols of the changes in this bar.
	 */
	public MidiBar(double beats, int beatsPerMinute, String label) {
		this.beats = beats;
		this.beatsPerMinute = beatsPerMinute;
		this.label = label;
	}

	/**
	 * Creates a new {@code MidiTrack} and attaches it to the list of tracks of this
	 * {@code MidiBar}.
	 * 
	 * @param instrumentPatch The {@code InstrumentPatch} that should be associated
	 *                        with the new {@code MidiTrack}.
	 * @return The newly created {@code MidiTrack}.
	 */
	public MidiTrack addTrack(InstrumentPatch instrumentPatch) {

		MidiTrack newTrack = new MidiTrack(instrumentPatch);

		tracks.add(newTrack);

		return newTrack;
	}

	/**
	 * Gets the {@code MidiTrack} objects created for this {@code MidiBar}.
	 * 
	 * @return An unmodifiable {@code List} of {@code MidiTrack} objects.
	 */
	public List<MidiTrack> getTracks() {
		return Collections.unmodifiableList(tracks);
	}

	/**
	 * Creates a new {@code MidiTrack} and attaches it to the list of percussion
	 * tracks of this {@code MidiBar}. Note that in contrast to normal
	 * {@code MidiTrack} objects, no {@code InstrumentPatch} can be associated with
	 * a percussion track.
	 * 
	 * @return The newly created {@code MidiTrack}.
	 */
	public MidiTrack addPercussionTrack() {
		MidiTrack newTrack = new MidiTrack(null);

		percussionTracks.add(newTrack);

		return newTrack;
	}

	/**
	 * Gets the {@code MidiTrack} objects created an percussion tracks for this
	 * {@code MidiBar}.
	 * 
	 * @return An unmodifiable {@code List} of {@code MidiTrack} objects assigned as
	 *         percussion tracks to this {@code MidiBar}.
	 */
	public List<MidiTrack> getPercussionTracks() {
		return Collections.unmodifiableList(percussionTracks);
	}

	/**
	 * Calculates the ticks per bar, based on the number of beats specified for this
	 * {@code MidiBar} and the specified resolution.
	 * 
	 * @param resolution The resolution in <em>ticks per beat</em>.
	 * @return The length of this bar in MIDI ticks.
	 */
	public long getTicksPerBar(int resolution) {
		return Math.round(beats * resolution);
	}

	/**
	 * Calculates the length of one beat in milliseconds based on the beats per
	 * minute specified for this {@code MidiBar}.
	 * 
	 * @return The length of one beat in milliseconds.
	 */
	public long getMsPerBeat() {
		return Math.round(60000000 / beatsPerMinute);
	}

	/**
	 * Returns the label that has been set for this {@code MidiBar}.
	 * 
	 * @return The label of this bar.
	 */
	public String getLabel() {
		return label;
	}

}
