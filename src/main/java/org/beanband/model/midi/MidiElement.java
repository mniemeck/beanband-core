package org.beanband.model.midi;

import java.util.Collection;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;


public abstract class MidiElement {

	public abstract Collection<MidiEvent> getMidiEvents(int channel, long ticksPerBar) throws InvalidMidiDataException;
	
	protected long calculateTicks(double value, long ticksPerBar) {
		return Math.round(value * ticksPerBar);
	}
	
	protected long calculateTicks(double value, long offset, long ticksPerBar) {
		return offset + Math.max(calculateTicks(value, ticksPerBar), 1);
	}
}
