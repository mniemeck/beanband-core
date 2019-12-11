package org.beanband.model.music;

import org.beanband.model.song.Bar;

/**
 * {@code MusicAnnotation} attachable to {@code Bar} object that captures
 * information about the overall structure of a song. Such annotations might
 * annotate special bars (for example the first or last bar of a section),
 * identified sections like intros, chorusses or bridges, as well as musical
 * breaks.
 * 
 * @author Michael Niemeck
 */
public class FormAnnotation extends MusicAnnotation<Bar> {

	private boolean isNoChord;
	private boolean isLastBar;

	/**
	 * Checks if the annotated {@code Bar} has no chord attached to it. Such a bar
	 * would be marked with <em>N.C.</em> in a leadsheet.
	 * 
	 * @return {@code true} if the bar is a no-chord-bar, {@code false} otherwise.
	 */
	public boolean isNoChord() {
		return isNoChord;
	}

	/**
	 * Marks the annotated {@code Bar} as <em>N.C.</em>
	 */
	public void setNoChord() {
		this.isNoChord = true;
	}

	/**
	 * Checks if the annotated {@code Bar} is the last bar of the song.
	 * 
	 * @return {@code true} if the bar is the last bar of the song.
	 */
	public boolean isLastBar() {
		return isLastBar;
	}

	/**
	 * Marks the annotated {@code Bar} as the last bar of the song.
	 */
	public void setIsLastBar() {
		this.isLastBar = true;
	}

	@Override
	public String toString() {
		return super.toString() + (isNoChord ? "noChord " : "") + (isLastBar ? "lastBar " : "");
	}
}
