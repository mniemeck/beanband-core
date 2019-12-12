package org.beanband.band;

import org.beanband.model.midi.InstrumentPatch;

/**
 * A {@code PercussionMusician} if a {@code Musician} that will play on the
 * provided drumset, and therefore does not specify an {@code InstrumentPatch}.
 * 
 * @author Michael Niemeck
 *
 */
public abstract class PercussionMusician extends Musician {

	/**
	 * {@inheritDoc} For a {@code PercussionMusician}, this always returns
	 * {@code null}.
	 * 
	 * @return {@code null}
	 */
	@Override
	public final InstrumentPatch getInstrumentPatch() {
		return null;
	}
}
