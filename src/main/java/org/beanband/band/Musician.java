package org.beanband.band;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import javax.sound.midi.InvalidMidiDataException;

import org.beanband.model.midi.InstrumentPatch;
import org.beanband.model.midi.MidiElement;
import org.beanband.model.song.Bar;

public abstract class Musician {

	public final Collection<MidiElement> elements = new ArrayList<>();

	public abstract InstrumentPatch getInstrumentPatch();

	public final Collection<MidiElement> play(Bar bar) throws InvalidMidiDataException {
		elements.clear();
		createElements(bar);
		return Collections.unmodifiableCollection(elements);
	}

	protected abstract void createElements(Bar bar) throws InvalidMidiDataException;

	protected final void addElement(MidiElement element) {
		elements.add(element);
	}
}
