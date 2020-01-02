/**
 * <em>Arrangers</em> perform various analyses of the Song Model and put the
 * results into the Music Model. That means they annotate the Song Model with
 * musicological information useful for the Musicians to create the playback. In
 * contrast to the Bands, each Arranger has a holistic view on the Song Model,
 * having not only access to the entire song, but also to the results of all
 * previously executed Arrangers. Arrangers also validate the Song Model and put
 * any warnings into special {@link org.beanband.model.music.WarningAnnotation}
 * annotations. A special Arranger, the
 * {@link org.beanband.arranger.BandArranger}, will also load, select,
 * instantiate and link the correct Band for every Bar. Arrangers are loaded and
 * called by the {@link org.beanband.bandleader.Bandleader}. {@code Arranger}
 * implementations are completely independent of {@code Band} implementations,
 * they make no assumptions on, or suggestions for, the actual performance of
 * the music.
 * 
 * @author Michael Niemeck
 */
package org.beanband.arranger;

// TODO Clean up and continuously improve existing Arrangers
