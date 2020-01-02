/**
 * <em>Bands</em> transform the Music Model into playable music represented by
 * the MIDI Model. Each Band can play in one specific style, and comprises a
 * number of <em>Musicians</em>. The Musicians of a Band process the Music Model
 * one Bar at a time and produce parts of the MIDI Model. Bands are loaded by
 * the {@link org.beanband.arranger.BandArranger} and called by the
 * {@link org.beanband.bandleader.Bandleader}. Note that {@code Band}
 * implementations should be developed completely independent of
 * {@code Arranger} implementations. If your {@code Band} relies on a specific
 * {@code Arranger}, condsider re-designing you approach.
 * 
 * @author Michael Niemeck
 */
package org.beanband.band;

//TODO Clean up and continuously improve existing Musicians