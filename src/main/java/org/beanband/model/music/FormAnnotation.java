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

	private boolean noChord;
	private boolean lastBar;
	private double changeRatio;

	/**
	 * Checks if the annotated {@code Bar} has no chord attached to it. Such a bar
	 * would be marked with <em>N.C.</em> in a leadsheet.
	 * 
	 * @return {@code true} if the bar is a no-chord-bar, {@code false} otherwise.
	 */
	public boolean isNoChord() {
		return noChord;
	}

	/**
	 * Marks the annotated {@code Bar} as <em>N.C.</em>
	 * 
	 * @param noChord {@code true} if the bar is a no-chord-bar, {@code false}
	 *                otherwise.
	 */
	public void setNoChord(boolean noChord) {
		this.noChord = noChord;
	}

	/**
	 * Checks if the annotated {@code Bar} is the last bar of the song.
	 * 
	 * @return {@code true} if the bar is the last bar of the song.
	 */
	public boolean isLastBar() {
		return lastBar;
	}

	/**
	 * Marks the annotated {@code Bar} as the last bar of the song.
	 * 
	 * @param lastBar {@code true} if the bar is the last bar of the song.
	 */
	public void setLastBar(boolean lastBar) {
		this.lastBar = lastBar;
	}

	/**
	 * Returns the change ratio of the annotated {@code Bar}. The ratio is based on
	 * the average number of chord changes per {@code Bar} throughout the
	 * {@code Song}. Values greater than 1 mean more changes than average, values
	 * less than 1 mean less changes. Consecutive bars containing the same chord are
	 * taken into account, so for example at an average change rate of 1 chord per
	 * bar, two consecutive bars containing each the same single chord will each
	 * have a change ratio of {@code 0.5}. Note that the average number of changes
	 * is rounded to an integer value after calculation, whereas the change ratio is
	 * not rounded anymore. So in a typical four beat song, the change ratios will
	 * be multiples and fractions of 2, i.e. {@code 4, 2, 1, 0.5 etc.}.
	 * 
	 * @return The change ratio of the annotated {@code Bar}.
	 */
	public double getChangeRatio() {
		return changeRatio;
	}

	/**
	 * Sets the chnge ratio of the annotated {@code Bar}.
	 * 
	 * @param changeRatio The change ratio of the current {@code Bar}.
	 * @see #getChangeRatio()
	 */
	public void setChangeRatio(double changeRatio) {
		this.changeRatio = changeRatio;
	}

	@Override
	public String toString() {
		return super.toString() + (noChord ? "noChord " : "") + (lastBar ? "lastBar " : "") + "changeRatio@"
				+ changeRatio;
	}
}
