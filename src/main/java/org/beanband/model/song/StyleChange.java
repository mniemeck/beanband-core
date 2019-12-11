package org.beanband.model.song;

import org.beanband.arranger.BandArranger;
import org.beanband.band.Band;
import org.beanband.model.music.Annotatable;

/**
 * {@code SongElement} representing a style of chenge within a {@code Song}. The
 * change becomes effective with the following {@code Bar} element. If this
 * {@code StyleChange} is followed immediately by another {@code StyleChange} it
 * has no effect.
 * 
 * @author Michael Niemeck
 */
public class StyleChange extends SongElement {
	private final String style;
	private final int tempo;

	/**
	 * Constructs a new {@code StyleChange} object with the specified style and
	 * tempo information. The information stored in this {@code StyleChange} object
	 * will be interpreted by the {@code BandArranger}.
	 * 
	 * @param style The style that should be changed into. The specified
	 *              {@code String} is matched against the values returned by
	 *              {@code Band.getStyleName()}. If no matching {@code Band} is
	 *              found, the style change is ignored by the {@code BandArranger}.
	 * @param tempo The tempo that should be changed into. The {@code BandArranger}
	 *              will ignore any non-positive {@code Integer} as tempo. Note that
	 *              the tempo is specified as <em>beats per minute</em>, and is put
	 *              into the resulting {@code Sequence} as such, but it is
	 *              ultimately up to the specific {@code Band} implementation, on
	 *              how <em>one beat</em>, and consequently the perceived tempo, is
	 *              defined.
	 * @see BandArranger
	 * @see Band#getStyleName()
	 * @see Band#getBeatsPerBar()
	 */
	public StyleChange(String style, int tempo) {
		this.style = style;
		this.tempo = tempo;
	}

	/**
	 * Returns the style that should be changed into. The style is matched against
	 * the values returned by {@code Band.getStyleName()}.
	 * 
	 * @return The {@code String} representing the new style.
	 * @see Band#getStyleName()
	 */
	public String getStyle() {
		return style;
	}

	/**
	 * Returns the tempo that should be changed into.
	 * 
	 * @return The tempo in <em>beats per minute</em>
	 * @see Band#getBeatsPerBar()
	 */
	public int getTempo() {
		return tempo;
	}

	/**
	 * Returns a textual representation of this {@code StyleChange} including any
	 * attached {@code MusicAnnotation} objects.
	 * 
	 * @return A multi-line Unicode {@code String} representing this
	 *         {@code StyleChange}.
	 * @see Annotatable#toString()
	 */
	@Override
	public String toString() {
		return "StyleChange [" + style + ":" + tempo + "]" + super.toString() + "]";
	}
}
