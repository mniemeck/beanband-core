/**
 * The <em>Bandleader</em> orchestrates the transformation from Song Model to
 * Sequence. It loads and calls all Arrangers to produce the Music Model. Then
 * it loads all Bands and calls all of their Musicians. Finally it collects the
 * resulting MIDI Model and passes it to the Engineer for final assembly of the
 * Sequence.
 * 
 * @author Michael Niemeck
 */
package org.beanband.bandleader;