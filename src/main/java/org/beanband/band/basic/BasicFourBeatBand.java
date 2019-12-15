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
	// TODO Create BasicFourBeatPianoMusician

	// TODO Clean up and continuously improve existing Arrangers and Musicians

	private static final String STYLE_NAME = "BasicFourBeat";
	private static final double BEATS_PER_BAR = 4;
	private static final int[] ALLOWED_CHANGES = { 1, 2, 4 };

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
