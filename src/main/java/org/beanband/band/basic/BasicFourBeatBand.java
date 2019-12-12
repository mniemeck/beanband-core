package org.beanband.band.basic;

import org.beanband.band.Band;

/**
 * A very basic combo playing a standard four-beat groove. Currently this is
 * just a showcase implementation to spark further development.
 * 
 * @author Michael Niemeck
 *
 */
public class BasicFourBeatBand extends Band {
	private final static String STYLE_NAME = "BasicFourBeat";
	private final static double BEATS_PER_BAR = 4;
	private final static int[] ALLOWED_CHANGES = { 1, 2 };

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
		addMusician(new BasicFourBeatBassMusician());
		addMusician(new BasicFourBeatGuitarMusician());
		addLeadMusician(new BasicFourBeatDrumsMusician());
	}

}
