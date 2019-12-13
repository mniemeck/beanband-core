/**
 * The <em>Song Model</em> is the internal representation of the user-provided
 * content. This is the song for which a playback should be generated. The
 * limits of the Song Model determine the musical information <b>BeanBand</b> is
 * able to understand. Although enhancements are possible at a later point in
 * time, we will try to start out with a reasonably comprehensive model that
 * will last for some time. Note however that we want to keep the Song Model as
 * simple as possible at all costs. Rather that having the user enter more
 * performance-related information rather implement {@code Arranger} and
 * {@code Band} implementations to deliver a better performance automatically.
 * 
 * @author Michael Niemeck
 */
package org.beanband.model.song;