package org.beanband.model.midi;

/**
 * Abstract class defining absolute MIDI pitches. Valid MIDI pitches range from 0
 * (C0) to 127 (G12).
 * 
 * @author Michael Niemeck
 */
public abstract class Pitch {

	/**
	 * Gets the pitch value represented by this {@code Pitch}.
	 * 
	 * @return The pitch value of this {@code Pitch}.
	 */
	abstract int getPitch();
}
