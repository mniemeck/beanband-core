package org.beanband.model.music;

import org.beanband.model.song.Chord;

/**
 * {@code MusicAnnotation} attachable to {@code Chord} object that captures
 * information about the harmonic structure of a song. Such annotations should
 * annotate the function of specific chords.
 * 
 * @author Michael Niemeck
 */
public class ProgressionAnnotation extends MusicAnnotation<Chord> {

	private boolean changeBefore;
	private boolean changeAfter;

	/**
	 * Checks if the annotated {@code Chord} is different than the {@code Chord}
	 * immediately preceding it.
	 * 
	 * @return {@code true} if the {@code Chord} is different than the one before,
	 *         {@code false} otherwise. Also returns {@code false} for the first
	 *         {@code Chord} of a song.
	 */
	public boolean isChangeBefore() {
		return changeBefore;
	}

	/**
	 * Marks the annotated {@code Chord} as different than the {@code Chord}
	 * immediately preceding it.
	 * 
	 * @param changeBefore {@code true} if the {@code Chord} is different than the
	 *                     one before, {@code false} otherwise.
	 */
	public void setChangeBefore(boolean changeBefore) {
		this.changeBefore = changeBefore;
	}

	/**
	 * Checks if the annotated {@code Chord} is different than the {@code Chord}
	 * immediately following it.
	 * 
	 * @return {@code true} if the {@code Chord} is different than the next one,
	 *         {@code false} otherwise. Also returns {@code false} for the last
	 *         {@code Chord} of a song.
	 */
	public boolean isChangeAfter() {
		return changeAfter;
	}

	/**
	 * Marks the annotated {@code Chord} as different than the {@code Chord}
	 * immediately following it.
	 * 
	 * @param changeAfter {@code true} if the {@code Chord} is different than the
	 *                    next one, {@code false} otherwise.
	 */
	public void setChangeAfter(boolean changeAfter) {
		this.changeAfter = changeAfter;
	}

	@Override
	public String toString() {
		return super.toString() + (changeBefore ? "changeBefore " : "") + (changeAfter ? "changeAfter " : "");
	}
}
