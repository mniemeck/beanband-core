package org.beanband.model.music;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * Abstract superclass of all objects from the Song Model to which
 * {@code MusicAnnotation} objects can be attached by the various
 * {@code Arranger} implementation.
 * 
 * @author Michael Niemeck
 *
 */
public abstract class Annotatable {

	private final Map<Class<?>, MusicAnnotation<?>> annotationMap = new HashMap<>();

	/**
	 * Returns the {@code MusicAnnotation} of the specified type, if it has already
	 * been attached to this {@code Annotatable}.
	 * 
	 * @param <T>        The {@code Annotatable} type parameter of the
	 *                   {@code MusicAnnotation} type.
	 * @param annotation The type of {@code MusicAnnotation} to retrieve.
	 * @return The instance of the specified type, if it has already been attached
	 *         to this {@code Annotatable}, and {@code null} otherwise.
	 */
	public final <T extends MusicAnnotation<?>> T getAnnotation(Class<T> annotation) {
		return annotation.cast(annotationMap.get(annotation));
	}

	/**
	 * Returns the {@code MusicAnnotation} of the specified type. If it is not
	 * already attached to this {@code Annotatable}, a new instance is created,
	 * attached and returned. <strong>Only one instance of any given type can be
	 * attached to each {@code Annotatable}.</strong>
	 * 
	 * @param <T>        The {@code Annotatable} type parameter of the
	 *                   {@code MusicAnnotation} type.
	 * @param annotation The type of {@code MusicAnnotation} to retrieve.
	 * @return The instance of the specified type, if it has already been attached
	 *         to this {@code Annotatable}, or a new one otherwise.
	 */
	public final <T extends MusicAnnotation<?>> T getOrCreateAnnotation(Class<T> annotation) {
		T musicAnnotation = getAnnotation(annotation);
		if (musicAnnotation == null) {
			try {
				musicAnnotation = annotation.getDeclaredConstructor().newInstance();
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException | SecurityException e) {
				throw new UnsupportedOperationException(e);
			}
			annotationMap.put(annotation, musicAnnotation);
		}
		return musicAnnotation;
	}

	/**
	 * Returns a textual representation of the {@code MusicAnnotation} objects
	 * attached to this {@code Annotatable}. Subclasses should override
	 * {@code toString} to return any information specific to their implementation.
	 * <strong>It is however highly recommended to include the return value of
	 * {@code super.toString()} in all subclass implementations. Otherwise the
	 * information of the attached {@code MusicAnnotation} objects is
	 * discarded.</strong> Note also that the annotations are included as
	 * individual, indented lines. So any nested structures of {@code Annotatable}
	 * might have to tidy up the indentation.
	 * 
	 * @return A multi-line Unicode {@code String} representing all
	 *         {@code MusicAnnotation} objects attached to this {@code Annotatable}.
	 * @see MusicAnnotation#toString()
	 */
	@Override
	public String toString() {
		String string = "";
		for (MusicAnnotation<?> musicAnnotation : annotationMap.values()) {
			string += "\n  " + musicAnnotation.toString();
		}
		return string;
	}

}
