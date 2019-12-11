package org.beanband.model.midi;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * A {@code MidiTrack} is a collection of {@code MidiElement} objects that shall
 * be rendered onto a single track in the final {@code Sequence}.
 * 
 * @author Michael Niemeck
 *
 */
public class MidiTrack {

	private final List<MidiElement> elements = new ArrayList<>();

	private final InstrumentPatch instrumentPatch;

	/**
	 * Constructs a new {@code MidiTrack} object.
	 * 
	 * @param instrumentPatch The {@code InstrumentPatch} that will be set for this
	 *                        {@code MidiTrack}.
	 */
	public MidiTrack(InstrumentPatch instrumentPatch) {
		this.instrumentPatch = instrumentPatch;
	}

	/**
	 * Adds a new {@code MidiElement} to this {@code MidiTrack}.
	 * 
	 * @param element The {@code MidiElement} to add.
	 */
	public void addElement(MidiElement element) {
		elements.add(element);
	}

	/**
	 * Adds multiple {@code MidiElement} objects to this {@code MidiTrack}.
	 * 
	 * @param elements A {@code Collection} of {@code MidiElements} objects. The
	 *                 elements are added in the order as they are returned by the
	 *                 {@code Collection}.
	 */
	public void addElements(Collection<MidiElement> elements) {
		this.elements.addAll(elements);
	}

	/**
	 * Returns the {@code MidiElement} objects that have been added to this
	 * {@code MidiTrack}.
	 * 
	 * @return An unmodifiable {@code List} of {@code MidiElement} objects.
	 */
	public List<MidiElement> getElements() {
		return Collections.unmodifiableList(elements);
	}

	/**
	 * Returns the {@code InstrumentPatch} that has been set for {@code MidiTrack}.
	 * @return The {@code InstrumentPatch} of this track.
	 */
	public InstrumentPatch getInstrumentPatch() {
		return instrumentPatch;
	}
}
