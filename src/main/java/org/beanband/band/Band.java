package org.beanband.band;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.ServiceLoader;
import java.util.logging.Logger;

import org.beanband.bandleader.Bandleader;
import org.beanband.model.song.StyleChange;

/**
 * {@code Band} implementations are responsible for turning the Song Model and
 * the Music Model into an actual playback. Since the actual rendering is done
 * by {@code Musician} implementations, the {@code Band} is mainly responsible
 * for managing its musicians. Note that new instances are created using the
 * {@code ServiceLoader} class, so implementations must stick to the
 * restrictions imposed by the {@code ServiceLoader} and must be registered in
 * the in the {@code META-INF/services/org.beanband.arranger.Arranger} file.
 * 
 * @author Michael Niemeck
 * @see ServiceLoader
 */
public abstract class Band {

	private List<Musician> musicians;
	private LeadMusician leadMusician;

	/**
	 * Returns the style name that this {@code Band} implements. This style name
	 * will be matched against the style name returned by
	 * {@code StyleChange.getStyle()}, which in turn represents the style chosen by
	 * the user in the song file.
	 * 
	 * @return The style name as a {@code String}.
	 * @see StyleChange#getStyle()
	 */
	public abstract String getStyleName();

	/**
	 * Returns the number of beats per bar that this band plays. Note that it is
	 * therefore necessary to create a new {@code Band} implementation in order to
	 * support a different meter.
	 * 
	 * @return The number of beats per bar. Although this is typically an integer,
	 *         it is also possible to return any {@code double} value.
	 */
	public abstract double getBeatsPerBar();

	/**
	 * Returns the number of different chords the {@code Band} is able to play
	 * within one bar. Changing chords by simply switching notes in mid-performance
	 * simply doesn't sound good, so it was decided not to support an arbitrary
	 * number of chord changes, and neither to let the user choose when exactly a
	 * chord change takes place. Each musician should handle chord changes
	 * gracefully in a musical way, so the chord changes should be explicitly
	 * implemented. Also a bar with less chord changes might be played in a slightly
	 * different style than a bar with a lot of them. All of this reasonings make it
	 * necessary that each {@code Band} only supports a limited, pre_defined number
	 * of chord changes within one bar.
	 * 
	 * @return An array of {@code int} numbers, containing all possible number of
	 *         chord changes supported within one bar.
	 */
	public abstract int[] getAllowedChanges();

	/**
	 * Returns the {@code Musician} objects forming this {@code Band}.
	 * 
	 * @return A {@code Collection} of {@code Musician} objects ready to play!
	 */
	public final Collection<Musician> getMusicians() {
		if (musicians == null) {
			loadMusicians();
		}
		return Collections.unmodifiableList(musicians);
	}

	private void loadMusicians() {
		musicians = new ArrayList<>();
		createMusicians();
		if (musicians.isEmpty()) {
			Logger.getLogger(Bandleader.LOGGER_NAME)
					.warning("Band " + getClass().getName() + " did not register any musicians.");
		}
		int numMusicians = 0;
		for (Musician musician : musicians) {
			if (!(musician instanceof PercussionMusician)) {
				numMusicians++;
			}
		}
		if (numMusicians > 15) {
			throw new UnsupportedOperationException("A band can not register more than 15 non-percussion musicians");
		}
	}

	/**
	 * One {@code Musician} of the {@code Band} can be designated as the
	 * {@code LeadMusician}. This musician has the additional capability of
	 * performing a <em>count-in</em> before the first bar. A typical example is a
	 * drummer counting one bar of quarter beats by banging the drumsticks together.
	 * 
	 * @return The {@code LeadMusician} of the {@code Band}, or {@code null} if the
	 *         {@code Band} doesn't have one.
	 */
	public final LeadMusician getLeadMusician() {
		if (musicians == null) {
			loadMusicians();
		}
		return leadMusician;
	}

	protected abstract void createMusicians();

	protected final void addMusician(Musician musician) {
		musicians.add(musician);
	}

	protected final void setLeadMusician(LeadMusician musician) {
		this.leadMusician = musician;
	}

	protected final void addLeadMusician(Musician musician) {
		addMusician(musician);
		if (musician instanceof LeadMusician) {
			setLeadMusician((LeadMusician) musician);
		} else {
			Logger.getLogger(Bandleader.LOGGER_NAME)
					.warning(musician.getClass().getName() + " is not a LeadMusician. Not set.");
		}
	}
}
