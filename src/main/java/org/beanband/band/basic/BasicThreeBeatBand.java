package org.beanband.band.basic;

import org.beanband.band.Band;

/**
 * A very basic combo playing a standard three-beat jazz-waltz type groove.
 * Currently this is just a showcase implementation to spark further
 * development.
 * 
 * @author Michael Niemeck
 *
 */
public class BasicThreeBeatBand extends Band {
	private static final String STYLE_NAME = "BasicThreeBeat";
	private static final double BEATS_PER_BAR = 3;
	private static final int[] ALLOWED_CHANGES = { 1, 3 };

	@Override
	public String getStyleName() {
		return STYLE_NAME;
	}

	@Override
	public double getBeatsPerBar() {
		return BEATS_PER_BAR;
	}

	@Override
	public int[] getAllowedChanges() {
		return ALLOWED_CHANGES;
	}

	@Override
	protected void createMusicians() {
		addMusician(new BasicThreeBeatBassMusician());
		addMusician(new BasicThreeBeatPianoMusician());
		addLeadMusician(new BasicThreeBeatDrumsMusician());
	}

}
