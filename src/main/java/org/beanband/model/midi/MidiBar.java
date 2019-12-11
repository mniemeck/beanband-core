package org.beanband.model.midi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class MidiBar {

	private final List<MidiTrack> tracks = new ArrayList<>();
	private final List<MidiTrack> percussionTracks = new ArrayList<>();
	
	private final double beats;
	private final int beatsPerMinute;
	private final String label;
	
	public MidiBar(double beats, int beatsPerMinute, String label) {
		this.beats = beats;
		this.beatsPerMinute = beatsPerMinute;
		this.label = label;
	}

	public MidiTrack addTrack(InstrumentPatch instrumentPatch) {

		MidiTrack newTrack = new MidiTrack(instrumentPatch);
		
		tracks.add(newTrack);

		return newTrack;
	}
	
	public List<MidiTrack> getTracks() {
		return Collections.unmodifiableList(tracks);
	}
	
	public MidiTrack addPercussionTrack() {
		MidiTrack newTrack = new MidiTrack(null);
		
		percussionTracks.add(newTrack);

		return newTrack;
	}
	
	public List<MidiTrack> getPercussionTracks() {
		return Collections.unmodifiableList(percussionTracks);
	}

	public long getTicksPerBar(int resolution) {
		return Math.round(beats * resolution);
	}
	
	public long getMsPerBeat() {
		return Math.round(60000000 / beatsPerMinute);
	}
	
	public String getLabel() {
		return label;
	}
	
}
