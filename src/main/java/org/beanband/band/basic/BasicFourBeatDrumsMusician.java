package org.beanband.band.basic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;

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

	private static final double START_DEVIATION = 0.003;
	private static final double ON_VELOCITY_DEVIATION = 2.0;
	private static final int OFF_VELOCITY = 127;

	private final Random random = new Random();

	@Override
	protected void createElements(Bar bar) throws InvalidMidiDataException {
		if (bar.getAnnotationDefault(FormAnnotation.class).isNoChord()) {
			addRandomizedElement(PercussionKey.ACOUSTIC_BASS_DRUM, 0.0, 120);
			addRandomizedElement(PercussionKey.ACOUSTIC_SNARE, 0.625, 120);
			addRandomizedElement(PercussionKey.ACOUSTIC_SNARE, 0.75, 120);
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
			case 4:
				addKickStrongHalfBar(0.0);
				addSnareStrongHalfBar(0.0);
				addHiHatStrongHalfBar(0.0);

				addKickStrongHalfBar(0.5);
				addSnareStrongHalfBar(0.5);
				addHiHatStrongHalfBar(0.5);
				break;
			}
		}
		if (bar.getAnnotationDefault(FormAnnotation.class).isLastBar()) {
			addRandomizedElement(PercussionKey.CRASH_CYMBAL_1, 1.0, 120);
		}
	}

	private void addKickStrongHalfBar(double start) throws InvalidMidiDataException {
		addRandomizedElement(PercussionKey.ACOUSTIC_BASS_DRUM, start, 120);
		addRandomizedElement(PercussionKey.ACOUSTIC_BASS_DRUM, start + 0.375, 100);
	}

	private void addKickWeakHalfBar(double start) throws InvalidMidiDataException {
		addRandomizedElement(PercussionKey.ACOUSTIC_BASS_DRUM, start, 110);
	}

	private void addSnareWeakHalfBar(double start) throws InvalidMidiDataException {
		addRandomizedElement(PercussionKey.ACOUSTIC_SNARE, start + 0.25, 110);
	}

	private void addSnareStrongHalfBar(double start) throws InvalidMidiDataException {
		addRandomizedElement(PercussionKey.ACOUSTIC_SNARE, start + 0.25, 110);
		addRandomizedElement(PercussionKey.ACOUSTIC_SNARE, start + 0.375, 90);
	}

	private void addHiHatWeakHalfBar(double start) throws InvalidMidiDataException {
		addRandomizedElement(PercussionKey.CLOSED_HI_HAT, start + 0.0, 100);
		addRandomizedElement(PercussionKey.CLOSED_HI_HAT, start + 0.125, 90);
		addRandomizedElement(PercussionKey.CLOSED_HI_HAT, start + 0.25, 90);
		addRandomizedElement(PercussionKey.CLOSED_HI_HAT, start + 0.375, 90);
	}

	private void addHiHatStrongHalfBar(double start) throws InvalidMidiDataException {
		addRandomizedElement(PercussionKey.CLOSED_HI_HAT, start + 0.0, 110);
		addRandomizedElement(PercussionKey.CLOSED_HI_HAT, start + 0.125, 90);
		addRandomizedElement(PercussionKey.CLOSED_HI_HAT, start + 0.25, 90);
		addRandomizedElement(PercussionKey.OPEN_HI_HAT, start + 0.375, 90);
	}

	@Override
	public Collection<MidiElement> getCountIn() throws InvalidMidiDataException {
		Collection<MidiElement> elements = new ArrayList<>();
		elements.add(new MidiPercussionElement(PercussionKey.SIDE_STICK, 0.0, 127, OFF_VELOCITY));
		elements.add(new MidiPercussionElement(PercussionKey.SIDE_STICK, 0.25, 127, OFF_VELOCITY));
		elements.add(new MidiPercussionElement(PercussionKey.SIDE_STICK, 0.5, 127, OFF_VELOCITY));
		elements.add(new MidiPercussionElement(PercussionKey.SIDE_STICK, 0.625, 127, OFF_VELOCITY));
		elements.add(new MidiPercussionElement(PercussionKey.SIDE_STICK, 0.75, 127, OFF_VELOCITY));
		elements.add(new MidiPercussionElement(PercussionKey.SIDE_STICK, 0.875, 127, OFF_VELOCITY));
		return Collections.unmodifiableCollection(elements);
	}

	private void addRandomizedElement(PercussionKey key, double start, int onVelocity)
			throws InvalidMidiDataException {
		double actualStart = Math.max(0.00025, random.nextGaussian() * START_DEVIATION + start);
		int actualOnVelocity = (int) Math.round(random.nextGaussian() * ON_VELOCITY_DEVIATION + onVelocity);
		actualOnVelocity = Math.max(0, actualOnVelocity);
		actualOnVelocity = Math.min(127, actualOnVelocity);
		int actualOffVelocity = OFF_VELOCITY;

		addElement(new MidiPercussionElement(key, actualStart, actualOnVelocity, actualOffVelocity));
	}

	@Override
	public double getCountInBeats() {
		return COUNT_IN_BEATS;
	}

}
