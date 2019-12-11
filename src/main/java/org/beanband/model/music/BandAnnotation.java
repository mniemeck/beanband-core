package org.beanband.model.music;

import org.beanband.arranger.BandArranger;
import org.beanband.band.Band;
import org.beanband.model.song.Bar;
import org.beanband.model.song.StyleChange;

/**
 * {@code MusicAnnotation} that captures the {@code StyleChange} information
 * from the Song Model and attaches it to every single {@code Bar}. Since
 * {@code Band} and {@code Musician} implementations have only access to one
 * single bar of music at a time, the style information must be present on a bar
 * level. {@code BandAnnotation} objects are created by the
 * {@code BandArranger}, which also validates the style and tempo entries.
 * 
 * @author Michael Niemeck.
 * @see BandArranger
 */
public class BandAnnotation extends MusicAnnotation<Bar> {
	private String style;
	private Integer tempo;
	private Band band;

	/**
	 * Returns the style of the annotated {@code Bar} as read from the Song Model.
	 * 
	 * @return The style {@code String} of the annotated {@code Bar}
	 * @see StyleChange#getStyle()
	 */
	public String getStyle() {
		return style;
	}

	/**
	 * Sets the style name of the annotated {@code Bar}.
	 * 
	 * @param style The style {@code String} to set for the annotated {@code Bar}.
	 * @see StyleChange#getStyle()
	 */
	public void setStyle(String style) {
		this.style = style;
	}

	/**
	 * Returns the tempo of the annotated {@code Bar} as read from the Song Model.
	 * 
	 * @return The tempo in <em>beats per minute</em>.
	 * @see StyleChange#getTempo()
	 */
	public Integer getTempo() {
		return tempo;
	}

	/**
	 * Sets the tempo of the annotated {@code Bar}.
	 * 
	 * @param tempo The tempo in <em>beats per minute</em>.
	 */
	public void setTempo(Integer tempo) {
		this.tempo = tempo;
	}

	/**
	 * Returns the {@code Band} that has been selected based on the set style. The
	 * Band is selected by the {@code BandArranger}.
	 * 
	 * @return The {@code Band} responsible for playing the annotated {@code Bar}
	 * @see BandArranger
	 */
	public Band getBand() {
		return band;
	}

	/**
	 * Sets the {@code Band} that should be responsible for playing the annotated
	 * {@code Bar}.
	 * 
	 * @param band The {@code Band} that should play the annotated {@code Bar}.
	 * @see BandArranger
	 */
	public void setBand(Band band) {
		this.band = band;
	}

	@Override
	public String toString() {
		return super.toString() + getStyle() + "@" + getTempo() + " (" + band.getClass().getName() + ")";
	}
}
