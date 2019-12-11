package org.beanband.band;

import org.beanband.model.midi.InstrumentPatch;

public abstract class PercussionMusician extends Musician {

	@Override
	public final InstrumentPatch getInstrumentPatch() {
		return null;
	}
}
