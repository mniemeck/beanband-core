package org.beanband.model.midi;

import javax.sound.midi.InvalidMidiDataException;

/**
 * Extension of the {@code Pitch} class, representing an absolute MIDI pitch by
 * specifying a {@code PercussionKey}.
 * 
 * @author Michael Niemeck
 */
public class PercussionPitch extends Pitch {

	private final PercussionKey key;

	/**
	 * Constructs an new {@code NotePitch} object.
	 * 
	 * @param key The {@code PercussionKey} defining this {@code Pitch}. Specifying
	 *            {@code null} will result in an
	 * @throws InvalidMidiDataException When no valid {@code PercussionKey} has been
	 *                                  specified.
	 */
	public PercussionPitch(PercussionKey key) throws InvalidMidiDataException {
		this.key = key;
	}

	@Override
	public int getPitch() {
		return key.getNumber();
	}

	/**
	 * Returns the {@code PercussionKey} defining this {@code Pitch}.
	 * @return The {@code PercussionKey} defining this {@code Pitch}.
	 */
	public PercussionKey getKey() {
		return key;
	}

}
