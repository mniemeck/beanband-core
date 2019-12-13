package org.beanband.arranger;

import java.util.ServiceLoader;

import javax.sound.midi.InvalidMidiDataException;

import org.beanband.model.music.MusicAnnotation;
import org.beanband.model.song.Song;

/**
 * {@code Arranger} implementations analyze and annotate the Song Model to
 * create the Music Model. {@code Arranger} objects are instantiated and called
 * by the {@code Bandleader}. They create the Music Model by creating
 * {@code MusicAnnotation} objects on the {@code Annotable} parts of the Song
 * model. Note that new instances are created using the {@code ServiceLoader}
 * class, so implementations must stick to the restrictions imposed by the
 * {@code ServiceLoader} and must be registered in the in the
 * {@code META-INF/services/org.beanband.arranger.Arranger} file. You should
 * <dl>
 * <dt>create a new {@code Arranger}</dt>
 * <dd>when when you have a new type of musicological information to calculate
 * (you might need an extension of the Music Model as well).</dd>
 * <dt>enhance an existing Arranger</dt>
 * <dd>if you have a more clever way of calculating existing musicological
 * information.</dd>
 * <dt>create a new Arranger with a lower priority</dt>
 * <dd>to overwrite existing annotations. That might make sense if you want to
 * build on the results from multiple higher-priority Arrangers.</dd>
 * </dl>
 * As a general rule-of-thumb, put calculations of information that is relevant
 * to all Bands and is independent of a specific style into Arrangers.
 * 
 * @author Michael Niemeck.
 * @see ServiceLoader
 * @see MusicAnnotation
 */
public abstract class Arranger implements Comparable<Arranger> {

	private static final int DEFAULT_PRIORITY = 0;

	/**
	 * Annotates the specified song by creating {@code MusicAnnotation} objects.
	 * This is the main method of the {@code Arranger}. There are no limitations on
	 * what type of {@code MusicAnnotation} each {@code Arranger} creates or
	 * modifies. Note however, that, in order for them to have any effect, they
	 * should be processed by {@code Band} implementations.
	 * <p>
	 * In contrast to the {@code Band}, each {@code Arranger} has a holistic view on
	 * the entire {@code Song}. It can access any part of the Song Model in
	 * arbitrary order as well as any {@code MusicAnnotation} objects already
	 * created by earlier {@code Arranger} objects. The order in which the
	 * {@code Arranger} objects are called is determined by their
	 * <strong>priority</strong> as returned by {@code getPriority()}.
	 * 
	 * @param song The {@code Song} holding the Song Model to be analyzed.
	 * @throws InvalidMidiDataException When the creation of Midi Model snippets
	 *                                  (like for example {@code Pitch} subclasses)
	 *                                  throws an Exception.
	 */
	public abstract void annotate(Song song) throws InvalidMidiDataException;

	/**
	 * Compares this {@code Arranger} with the specified {@code Arranger} by
	 * comparing their priorities as specified by {@code getPriority}. It is
	 * <em>consistent with equals</em> by defaulting to comparing the hash codes as
	 * returned by {@code hashCode()} when the priorities are equal.
	 */
	@Override
	public final int compareTo(Arranger o) {
		if (o.getPriority() != getPriority()) {
			return Integer.compare(o.getPriority(), getPriority());
		}
		return Integer.compare(o.hashCode(), hashCode());
	}

	/**
	 * Determines the order in which this {@code Arranger} object is called in
	 * relation to other {@code Arranger} objects. {@code Arranger} objects with
	 * higher priority are called first.
	 * 
	 * @return The priority of this {@code Arranger} ranging from
	 *         {@code Integer.MIN_VALUE} to {@code Integer.MAX_VALUE}.
	 */
	protected int getPriority() {
		return DEFAULT_PRIORITY;
	}

}
