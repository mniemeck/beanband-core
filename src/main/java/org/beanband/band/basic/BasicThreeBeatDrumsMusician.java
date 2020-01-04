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
public class BasicThreeBeatDrumsMusician extends PercussionMusician {
	private static final double COUNT_IN_BEATS = 6;

	private final RandomizedMusicianService service = new RandomizedMusicianService();

	@Override
	protected void createElements(Bar bar) throws InvalidMidiDataException {
		if (bar.getAnnotationDefault(FormAnnotation.class).isNoChord()) {
			addElement(service.createRandomizedElement(PercussionKey.ACOUSTIC_BASS_DRUM, 0.0, 120));
		} else {
			switch (bar.getChords().size()) {
			case 1:
				addKickStrongBar(0.0);
				addSnareStrongBar(0.0);
				addRideStrongBar(0.0);
				break;
			case 3:
				addKickWeakBar(0.0);
				addSnareWeakBar(0.0);
				addRideWeakBar(0.0);
				break;
			}
		}
		if (bar.getAnnotationDefault(FormAnnotation.class).isLastBar()) {
			addElement(service.createRandomizedElement(PercussionKey.CRASH_CYMBAL_1, 1.0, 120));
		}
	}

	private void addKickStrongBar(double start) throws InvalidMidiDataException {
		addElement(service.createRandomizedElement(PercussionKey.ACOUSTIC_BASS_DRUM, start, 100));
	}

	private void addKickWeakBar(double start) throws InvalidMidiDataException {
		addElement(service.createRandomizedElement(PercussionKey.ACOUSTIC_BASS_DRUM, start, 80));
		addElement(service.createRandomizedElement(PercussionKey.ACOUSTIC_BASS_DRUM, start + 1.0 / 3.0, 80));
		addElement(service.createRandomizedElement(PercussionKey.ACOUSTIC_BASS_DRUM, start + 2.0 / 3.0, 80));
	}

	private void addSnareStrongBar(double start) throws InvalidMidiDataException {
		addElement(service.createRandomizedElement(PercussionKey.ACOUSTIC_SNARE, start + 1.0 / 3.0, 90));
		addElement(service.createRandomizedElement(PercussionKey.ACOUSTIC_SNARE, start + 2.0 / 3.0, 70));
	}

	private void addSnareWeakBar(double start) throws InvalidMidiDataException {
		addElement(service.createRandomizedElement(PercussionKey.ACOUSTIC_SNARE, start + 1.0 / 6.0, 65));
		addElement(service.createRandomizedElement(PercussionKey.ACOUSTIC_SNARE, start + 3.0 / 6.0, 65));
		addElement(service.createRandomizedElement(PercussionKey.ACOUSTIC_SNARE, start + 5.0 / 6.0, 65));
	}

	private void addRideStrongBar(double start) throws InvalidMidiDataException {
		addElement(service.createRandomizedElement(PercussionKey.RIDE_CYMBAL_1, start, 80));
		addElement(service.createRandomizedElement(PercussionKey.RIDE_CYMBAL_1, start + 1.0 / 3.0, 65));
		addElement(service.createRandomizedElement(PercussionKey.RIDE_CYMBAL_1, start + 2.0 / 3.0, 65));
	}

	private void addRideWeakBar(double start) throws InvalidMidiDataException {
		addElement(service.createRandomizedElement(PercussionKey.RIDE_CYMBAL_1, start, 70));
		addElement(service.createRandomizedElement(PercussionKey.RIDE_BELL, start + 1.0 / 3.0, 65));
		addElement(service.createRandomizedElement(PercussionKey.RIDE_BELL, start + 2.0 / 3.0, 75));
	}

	@Override
	public Collection<MidiElement> getCountIn() throws InvalidMidiDataException {
		Collection<MidiElement> elements = new ArrayList<>();
		elements.add(new MidiPercussionElement(PercussionKey.SIDE_STICK, 0.0, 127, 127));
		elements.add(new MidiPercussionElement(PercussionKey.SIDE_STICK, 1.0 / 6.0, 100, 127));
		elements.add(new MidiPercussionElement(PercussionKey.SIDE_STICK, 2.0 / 6.0, 100, 127));
		elements.add(new MidiPercussionElement(PercussionKey.SIDE_STICK, 3.0 / 6.0, 127, 127));
		elements.add(new MidiPercussionElement(PercussionKey.SIDE_STICK, 4.0 / 6.0, 100, 127));
		elements.add(new MidiPercussionElement(PercussionKey.SIDE_STICK, 5.0 / 6.0, 100, 127));
		return Collections.unmodifiableCollection(elements);
	}

	@Override
	public double getCountInBeats() {
		return COUNT_IN_BEATS;
	}

}
