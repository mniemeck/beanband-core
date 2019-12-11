package org.beanband.model.midi;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class MidiTrack {

	private final List<MidiElement> elements = new ArrayList<>();

	private final InstrumentPatch instrumentPatch;
	
	public MidiTrack(InstrumentPatch instrumentPatch) {
		this.instrumentPatch = instrumentPatch;
	}
	
	public void addElement(MidiElement element) {
		elements.add(element);
	}
	
	public void addElements(Collection<MidiElement> elements) {
		this.elements.addAll(elements);
	}
	
	public List<MidiElement> getElements() {
		return Collections.unmodifiableList(elements);
	}
	
	public InstrumentPatch getInstrumentPatch() {
		return instrumentPatch;
	}
}
