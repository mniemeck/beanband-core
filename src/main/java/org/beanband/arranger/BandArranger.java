package org.beanband.arranger;

import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

import javax.sound.midi.InvalidMidiDataException;

import org.beanband.band.Band;
import org.beanband.model.music.BandAnnotation;
import org.beanband.model.music.WarningAnnotation;
import org.beanband.model.song.Bar;
import org.beanband.model.song.Song;
import org.beanband.model.song.SongElement;
import org.beanband.model.song.StyleChange;

/**
 * The {@code BandArranger} scans the Song Model for {@code StyleChange}
 * elements and attaches corresponding {@code BandAnnotation} objects to the
 * bars. The correct band is identified by comparing
 * {@code StyleChange.getStyle()} against {@code Band.getStyleName()}. If no
 * matching band can be found, the style is not changed. Note that the
 * {@code BandArranger} attaches a {@code BandAnnotation} containing the current
 * band and tempo to every single {@code Bar} element, even if no change has
 * occurred.
 * 
 * @author Michael Niemeck
 * @see BandAnnotation
 */
public class BandArranger extends Arranger {

	@Override
	public void annotate(Song song) throws InvalidMidiDataException {
		Map<String, Band> bandMap = createBandMap();

		String currentStyle = "DEFAULT";
		Integer currentTempo = 60;
		Band currentBand = bandMap.values().iterator().next();

		if (!song.getElements().isEmpty()) {
			SongElement songElement = song.getElements().get(0);
			if (!(songElement instanceof StyleChange)) {
				songElement.getOrCreateAnnotation(WarningAnnotation.class)
						.addMessage("Song does not start with a style specification. Defaults assumed.");
			}
		}
		for (SongElement songElement : song.getElements()) {
			if (songElement instanceof StyleChange) {
				StyleChange styleChange = (StyleChange) songElement;
				currentStyle = styleChange.getStyle();
				if (styleChange.getTempo() > 0) {
					currentTempo = styleChange.getTempo();
				} else {
					songElement.getOrCreateAnnotation(WarningAnnotation.class)
							.addMessage("Tempo must be greater than zero. Tempo not changed.");
				}
				if (bandMap.containsKey(currentStyle)) {
					currentBand = bandMap.get(currentStyle);
				} else {
					songElement.getOrCreateAnnotation(WarningAnnotation.class)
							.addMessage("No matching band for style " + currentStyle + " found. Band not changed.");
				}
			} else if (songElement instanceof Bar) {
				BandAnnotation bandAnnotation = songElement.getOrCreateAnnotation(BandAnnotation.class);
				bandAnnotation.setStyle(currentStyle);
				bandAnnotation.setTempo(currentTempo);
				bandAnnotation.setBand(currentBand);
				validateChordChanges((Bar) songElement, currentBand);
			}
		}
	}

	private void validateChordChanges(Bar bar, Band band) {
		for (int chordChanges : band.getAllowedChanges()) {
			if (bar.getChords().size() == chordChanges) {
				return;
			}
		}
		bar.getOrCreateAnnotation(WarningAnnotation.class).addMessage(
				"Unsupported number of changes for Band " + band.getStyleName() + ". Excess chords ignored.");
	}

	private Map<String, Band> createBandMap() {
		Map<String, Band> bandMap = new HashMap<>();
		ServiceLoader<Band> serviceLoader = ServiceLoader.load(Band.class);
		serviceLoader.forEach(b -> bandMap.put(b.getStyleName(), b));
		if (bandMap.isEmpty()) {
			throw new UnsupportedOperationException("No Bands found. Unable to render playbacks.");
		}
		return bandMap;
	}

	@Override
	protected int getPriority() {
		return Integer.MAX_VALUE;
	}

}
