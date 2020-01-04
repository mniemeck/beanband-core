package org.beanband.model.music;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

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

	private static final Map<Class<?>, MusicAnnotation<?>> defaultInstanceMap = new HashMap<>();

	/**
	 * Returns the default instance of the {@code MusicAnnotation} of the specified
	 * type. For every class of {@code MusicAnnotation}, only one singleton instance
	 * is kept. <strong>The instance is created using the default constructor, so
	 * make sure that every subclass implements reasonable default behaviour when
	 * created in this way.</strong>
	 * 
	 * @param <T>        The {@code Annotatable} type parameter of the
	 *                   {@code MusicAnnotation} type.
	 * @param annotation The type of {@code MusicAnnotation} to retrieve.
	 * @return The default instance of the specified type.
	 * @see Annotatable#getAnnotationDefault(Class)
	 */
	public static final <T extends MusicAnnotation<?>> T getDefaultAnnotation(Class<T> annotation) {
		T musicAnnotation = annotation.cast(defaultInstanceMap.get(annotation));
		if (musicAnnotation == null) {
			synchronized (MusicAnnotation.class) {
				musicAnnotation = annotation.cast(defaultInstanceMap.get(annotation));
				if (musicAnnotation == null) {
					try {
						musicAnnotation = annotation.getDeclaredConstructor().newInstance();
					} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
							| InvocationTargetException | NoSuchMethodException | SecurityException e) {
						throw new UnsupportedOperationException(e);
					}
					defaultInstanceMap.put(annotation, musicAnnotation);
				}
			}
		}
		return musicAnnotation;
	}

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
