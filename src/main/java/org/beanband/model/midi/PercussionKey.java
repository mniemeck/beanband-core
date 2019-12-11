package org.beanband.model.midi;

/**
 * Enumeration of the percussion key numbers defined by the General MIDI (GM)
 * standard.
 * 
 * @author Michael Niemeck
 * @see <a href="http://www.midi.org/techspecs/gm1sound.php#percussion">GM1
 *      Percussion Key Map</a>
 */
public enum PercussionKey {
	/**
	 * Acoustic Bass Drum (Key# 35)
	 */
	ACOUSTIC_BASS_DRUM(35),
	/**
	 * Bass Drum 1 (Key# 36)
	 */
	BASS_DRUM_1(36),
	/**
	 * Side Stick (Key# 37)
	 */
	SIDE_STICK(37),
	/**
	 * Acoustic Snare (Key# 38)
	 */
	ACOUSTIC_SNARE(38),
	/**
	 * Hand Clap (Key# 39)
	 */
	HAND_CLAP(39),
	/**
	 * Electric Snare (Key# 40)
	 */
	ELECTRIC_SNARE(40),
	/**
	 * Low Floor Tom (Key# 41)
	 */
	LOW_FLOOR_TOM(41),
	/**
	 * Closed Hi Hat (Key# 42)
	 */
	CLOSED_HI_HAT(42),
	/**
	 * High Floor Tom (Key# 43)
	 */
	HIGH_FLOOR_TOM(43),
	/**
	 * Pedal Hi-Hat (Key# 44)
	 */
	PEDAL_HI_HAT(44),
	/**
	 * Low Tom (Key# 45)
	 */
	LOW_TOM(45),
	/**
	 * Open Hi-Hat (Key# 46)
	 */
	OPEN_HI_HAT(46),
	/**
	 * Low-Mid Tom (Key# 47)
	 */
	LOW_MID_TOM(47),
	/**
	 * Hi-Mid Tom (Key# 48)
	 */
	HI_MID_TOM(48),
	/**
	 * Crash Cymbal 1 (Key# 49)
	 */
	CRASH_CYMBAL_1(49),
	/**
	 * High Tom (Key# 50)
	 */
	HIGH_TOM(50),
	/**
	 * Ride Cymbal 1 (Key# 51)
	 */
	RIDE_CYMBAL_1(51),
	/**
	 * Chinese Cymbal (Key# 52)
	 */
	CHINESE_CYMBAL(52),
	/**
	 * Ride Bell (Key# 53)
	 */
	RIDE_BELL(53),
	/**
	 * Tambourine (Key# 54)
	 */
	TAMBOURINE(54),
	/**
	 * Splash Cymbal (Key# 55)
	 */
	SPLASH_CYMBAL(55),
	/**
	 * Cowbell (Key# 56)
	 */
	COWBELL(56),
	/**
	 * Crash Cymbal 2 (Key# 57)
	 */
	CRASH_CYMBAL_2(57),
	/**
	 * Vibraslap (Key# 58)
	 */
	VIBRASLAP(58),
	/**
	 * Ride Cymbal 2 (Key# 59)
	 */
	RIDE_CYMBAL_2(59),
	/**
	 * Hi Bongo (Key# 60)
	 */
	HI_BONGO(60),
	/**
	 * Low Bongo (Key# 61)
	 */
	LOW_BONGO(61),
	/**
	 * Mute Hi Conga (Key# 62)
	 */
	MUTE_HI_CONGA(62),
	/**
	 * Open Hi Conga (Key# 63)
	 */
	OPEN_HI_CONGA(63),
	/**
	 * Low Conga (Key# 64)
	 */
	LOW_CONGA(64),
	/**
	 * High Timbale (Key# 65)
	 */
	HIGH_TIMBALE(65),
	/**
	 * Low Timbale (Key# 66)
	 */
	LOW_TIMBALE(66),
	/**
	 * High Agogo (Key# 67)
	 */
	HIGH_AGOGO(67),
	/**
	 * Low Agogo (Key# 68)
	 */
	LOW_AGOGO(68),
	/**
	 * Cabasa (Key# 69)
	 */
	CABASA(69),
	/**
	 * Maracas (Key# 70)
	 */
	MARACAS(70),
	/**
	 * Short Whistle (Key# 71)
	 */
	SHORT_WHISTLE(71),
	/**
	 * Long Whistle (Key# 72)
	 */
	LONG_WHISTLE(72),
	/**
	 * Short Guiro (Key# 73)
	 */
	SHORT_GUIRO(73),
	/**
	 * Long Guiro (Key# 74)
	 */
	LONG_GUIRO(74),
	/**
	 * Claves (Key# 75)
	 */
	CLAVES(75),
	/**
	 * Hi Wood Block (Key# 76)
	 */
	HI_WOOD_BLOCK(76),
	/**
	 * Low Wood Block (Key# 77)
	 */
	LOW_WOOD_BLOCK(77),
	/**
	 * Mute Cuica (Key# 78)
	 */
	MUTE_CUICA(78),
	/**
	 * Open Cuica (Key# 79)
	 */
	OPEN_CUICA(79),
	/**
	 * Mute Triangle (Key# 80)
	 */
	MUTE_TRIANGLE(80),
	/**
	 * Open Triangle (Key# 81)
	 */
	OPEN_TRIANGLE(81);

	private final int number;

	/**
	 * Constructs a new value and associates it with the specified key number.
	 * 
	 * @param number The key number.
	 */
	private PercussionKey(int number) {
		this.number = number;
	}

	/**
	 * Gets the key number of the patch.
	 * 
	 * @return The key number.
	 */
	public int getNumber() {
		return this.number;
	}
}
