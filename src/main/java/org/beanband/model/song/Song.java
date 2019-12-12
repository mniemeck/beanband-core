package org.beanband.model.song;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Top-level class of the Song Model. A {@code Song} represents the
 * user-provided input from which a playback in the form of a {@code Sequence}
 * will be generated.
 * 
 * @author Michael Niemeck
 */
public class Song {

	private final List<SongElement> elements = new ArrayList<>();
	private final List<Bar> bars = new ArrayList<>();

	/**
	 * Creates a new {@code Bar} object and adds it to this {@code Song}. Bars and
	 * other song elements retain the order in which they were added. There are no
	 * methods to (re-)move them after they have been added.
	 * 
	 * @return The newly created {@code Bar}.
	 */
	public Bar addBar() {
		Bar bar = new Bar();

		elements.add(bar);
		bars.add(bar);

		return bar;
	}

	/**
	 * Creates a new {@code StyleChange} object and adds it to this {@code Song}.
	 * Style changes and other song elements retain the order in which they were
	 * added. There are no methods to (re-)move them after they have been added.
	 * 
	 * @param style The style that should be changed into. See
	 *              {@code StyleChange.StyleChange} for details.
	 * @param tempo The tempo that should be changed into. See
	 *              {@code StyleChange.StyleChange} for details.
	 * @return The newly created {@code Bar}.
	 * @see StyleChange#StyleChange(String, int)
	 */
	public StyleChange addStyleChange(String style, int tempo) {
		StyleChange styleChange = new StyleChange(style, tempo);

		elements.add(styleChange);

		return styleChange;
	}

	/**
	 * Returns all {@code SongElement} objects in the order in which they were added
	 * to this {@code Song}.
	 * 
	 * @return An unmodifiable {@code List} of all {@code SongElement} objects in
	 *         the correct order.
	 */
	public List<SongElement> getElements() {
		return Collections.unmodifiableList(elements);
	}

	/**
	 * Returns only the {@code Bar} objects in the order in which they were added to
	 * this {@code Song}.
	 * 
	 * @return An unmodifiable {@code List} of all {@code Bar} objects in the
	 *         correct order.
	 */
	public List<Bar> getBars() {
		return Collections.unmodifiableList(bars);
	}

	/**
	 * Returns a textual representation of this {@code Song} including all nested
	 * song elements.
	 * 
	 * @return A multi-line Unicode {@code String} representing this {@code Song}.
	 * @see SongElement#toString()
	 */
	@Override
	public String toString() {
		String string = "Song (" + bars.size() + " bars, " + elements.size() + " elements total)";
		for (SongElement element : elements) {
			string += "\n  " + element.toString().replaceAll("(\\v+)(\\h+)", "$1$2$2");
		}
		return string;
	}
}
