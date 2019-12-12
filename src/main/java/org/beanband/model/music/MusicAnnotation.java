package org.beanband.model.music;

import org.beanband.arranger.Arranger;
import org.beanband.band.Band;

/**
 * Superclass of the Music Model. Objects of {@code MusicAnnotation}
 * implementations are created by {@code Arranger} implementations. They get
 * attached to {@code Annotatable} subclasses, and contain musicological
 * information that the {@code Band} implementations later require to generate
 * the playback.
 * <p>
 * There is no restriction of the contents of a specific {@code MusicAnnotation}
 * implementation. It is up to the {@code Arranger} implementations, what type
 * of annotations they are able to populate, and up to the {@code Band}
 * implementations, what type of annotations they understand and how they will
 * react to them. The specifics should be documented in each respective
 * implementations.
 * 
 * @author Michael Niemeck
 *
 * @param <T> The type of {@code Annotatable} this {@code MusicAnnotation} can
 *            be attached to.
 * @see Arranger
 * @see Band
 */
public abstract class MusicAnnotation<T extends Annotatable> {

	/**
	 * Returns a textual representation of the contents of this
	 * {@code MusicAnnotation}. The base implementation returns just the class name.
	 * It is good practice, although not required, to prefix any subclass
	 * implementation of this method with {@code super.toString()}. <strong>The
	 * output of this mehtod should be a single line, otherwise it will mangle the
	 * output of {@code Annotatable.toString()}.</strong>
	 * 
	 * @return A single-line Unicode {@code String} representing the contents of
	 *         this {@code MusicAnnotation} object.
	 * @see Annotatable#toString()
	 */
	@Override
	public String toString() {
		return "[" + getClass().getSimpleName() + "] ";
	}
}
