package org.beanband.band;

import java.util.Collection;

import javax.sound.midi.InvalidMidiDataException;

import org.beanband.model.midi.MidiElement;

public interface LeadMusician {

	Collection<MidiElement> getCountIn() throws InvalidMidiDataException;
	
	double getCountInBeats();


}
