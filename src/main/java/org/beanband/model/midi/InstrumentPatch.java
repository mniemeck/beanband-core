package org.beanband.model.midi;

/**
 * Enumeration of the instrument patch numbers defined by the General MIDI (GM)
 * standard.
 * 
 * @author Michael Niemeck
 * @see <a href="http://www.midi.org/techspecs/gm1sound.php#instrument">GM1
 *      Instrument Patch Map</a>
 */
public enum InstrumentPatch {
	/**
	 * Acoustic Grand Piano (PC# 1)
	 */
	ACOUSTIC_GRAND_PIANO(0),
	/**
	 * Bright Acoustic Piano (PC# 2)
	 */
	BRIGHT_ACOUSTIC_PIANO(1),
	/**
	 * Electric Grand Piano (PC# 3)
	 */
	ELECTRIC_GRAND_PIANO(2),
	/**
	 * Honky-tonk Piano (PC# 4)
	 */
	HONKY_TONK_PIANO(3),
	/**
	 * Electric Piano 1 (PC# 5)
	 */
	ELECTRIC_PIANO_1(4),
	/**
	 * Electric Piano 2 (PC# 6)
	 */
	ELECTRIC_PIANO_2(5),
	/**
	 * Harpsichord (PC# 7)
	 */
	HARPSICHORD(6),
	/**
	 * Clavi (PC# 8)
	 */
	CLAVI(7),
	/**
	 * Celesta (PC# 9)
	 */
	CELESTA(8),
	/**
	 * Glockenspiel (PC# 10)
	 */
	GLOCKENSPIEL(9),
	/**
	 * Music Box (PC# 11)
	 */
	MUSIC_BOX(10),
	/**
	 * Vibraphone (PC# 12)
	 */
	VIBRAPHONE(11),
	/**
	 * Marimba (PC# 13)
	 */
	MARIMBA(12),
	/**
	 * Xylophone (PC# 14)
	 */
	XYLOPHONE(13),
	/**
	 * Tubular Bells (PC# 15)
	 */
	TUBULAR_BELLS(14),
	/**
	 * Dulcimer (PC# 16)
	 */
	DULCIMER(15),
	/**
	 * Drawbar Organ (PC# 17)
	 */
	DRAWBAR_ORGAN(16),
	/**
	 * Percussive Organ (PC# 18)
	 */
	PERCUSSIVE_ORGAN(17),
	/**
	 * Rock Organ (PC# 19)
	 */
	ROCK_ORGAN(18),
	/**
	 * Church Organ (PC# 20)
	 */
	CHURCH_ORGAN(19),
	/**
	 * Reed Organ (PC# 21)
	 */
	REED_ORGAN(20),
	/**
	 * Accordion (PC# 22)
	 */
	ACCORDION(21),
	/**
	 * Harmonica (PC# 23)
	 */
	HARMONICA(22),
	/**
	 * Tango Accordion (PC# 24)
	 */
	TANGO_ACCORDION(23),
	/**
	 * Acoustic Guitar (nylon) (PC# 25)
	 */
	ACOUSTIC_GUITAR_NYLON(24),
	/**
	 * Acoustic Guitar (steel) (PC# 26)
	 */
	ACOUSTIC_GUITAR_STEEL(25),
	/**
	 * Electric Guitar (jazz) (PC# 27)
	 */
	ELECTRIC_GUITAR_JAZZ(26),
	/**
	 * Electric Guitar (clean) (PC# 28)
	 */
	ELECTRIC_GUITAR_CLEAN(27),
	/**
	 * Electric Guitar (muted) (PC# 29)
	 */
	ELECTRIC_GUITAR_MUTED(28),
	/**
	 * Overdriven Guitar (PC# 30)
	 */
	OVERDRIVEN_GUITAR(29),
	/**
	 * Distortion Guitar (PC# 31)
	 */
	DISTORTION_GUITAR(30),
	/**
	 * Guitar harmonics (PC# 32)
	 */
	GUITAR_HARMONICS(31),
	/**
	 * Acoustic Bass (PC# 33)
	 */
	ACOUSTIC_BASS(32),
	/**
	 * Electric Bass (finger) (PC# 34)
	 */
	ELECTRIC_BASS_FINGER(33),
	/**
	 * Electric Bass (pick) (PC# 35)
	 */
	ELECTRIC_BASS_PICK(34),
	/**
	 * Fretless Bass (PC# 36)
	 */
	FRETLESS_BASS(35),
	/**
	 * Slap Bass 1 (PC# 37)
	 */
	SLAP_BASS_1(36),
	/**
	 * Slap Bass 2 (PC# 38)
	 */
	SLAP_BASS_2(37),
	/**
	 * Synth Bass 1 (PC# 39)
	 */
	SYNTH_BASS_1(38),
	/**
	 * Synth Bass 2 (PC# 40)
	 */
	SYNTH_BASS_2(39),
	/**
	 * Violin (PC# 41)
	 */
	VIOLIN(40),
	/**
	 * Viola (PC# 42)
	 */
	VIOLA(41),
	/**
	 * Cello (PC# 43)
	 */
	CELLO(42),
	/**
	 * Contrabass (PC# 44)
	 */
	CONTRABASS(43),
	/**
	 * Tremolo Strings (PC# 45)
	 */
	TREMOLO_STRINGS(44),
	/**
	 * Pizzicato Strings (PC# 46)
	 */
	PIZZICATO_STRINGS(45),
	/**
	 * Orchestral Harp (PC# 47)
	 */
	ORCHESTRAL_HARP(46),
	/**
	 * Timpani (PC# 48)
	 */
	TIMPANI(47),
	/**
	 * String Ensemble 1 (PC# 49)
	 */
	STRING_ENSEMBLE_1(48),
	/**
	 * String Ensemble 2 (PC# 50)
	 */
	STRING_ENSEMBLE_2(49),
	/**
	 * SynthStrings 1 (PC# 51)
	 */
	SYNTHSTRINGS_1(50),
	/**
	 * SynthStrings 2 (PC# 52)
	 */
	SYNTHSTRINGS_2(51),
	/**
	 * Choir Aahs (PC# 53)
	 */
	CHOIR_AAHS(52),
	/**
	 * Voice Oohs (PC# 54)
	 */
	VOICE_OOHS(53),
	/**
	 * Synth Voice (PC# 55)
	 */
	SYNTH_VOICE(54),
	/**
	 * Orchestra Hit (PC# 56)
	 */
	ORCHESTRA_HIT(55),
	/**
	 * Trumpet (PC# 57)
	 */
	TRUMPET(56),
	/**
	 * Trombone (PC# 58)
	 */
	TROMBONE(57),
	/**
	 * Tuba (PC# 59)
	 */
	TUBA(58),
	/**
	 * Muted Trumpet (PC# 60)
	 */
	MUTED_TRUMPET(59),
	/**
	 * French Horn (PC# 61)
	 */
	FRENCH_HORN(60),
	/**
	 * Brass Section (PC# 62)
	 */
	BRASS_SECTION(61),
	/**
	 * SynthBrass 1 (PC# 63)
	 */
	SYNTHBRASS_1(62),
	/**
	 * SynthBrass 2 (PC# 64)
	 */
	SYNTHBRASS_2(63),
	/**
	 * Soprano Sax (PC# 65)
	 */
	SOPRANO_SAX(64),
	/**
	 * Alto Sax (PC# 66)
	 */
	ALTO_SAX(65),
	/**
	 * Tenor Sax (PC# 67)
	 */
	TENOR_SAX(66),
	/**
	 * Baritone Sax (PC# 68)
	 */
	BARITONE_SAX(67),
	/**
	 * Oboe (PC# 69)
	 */
	OBOE(68),
	/**
	 * English Horn (PC# 70)
	 */
	ENGLISH_HORN(69),
	/**
	 * Bassoon (PC# 71)
	 */
	BASSOON(70),
	/**
	 * Clarinet (PC# 72)
	 */
	CLARINET(71),
	/**
	 * Piccolo (PC# 73)
	 */
	PICCOLO(72),
	/**
	 * Flute (PC# 74)
	 */
	FLUTE(73),
	/**
	 * Recorder (PC# 75)
	 */
	RECORDER(74),
	/**
	 * Pan Flute (PC# 76)
	 */
	PAN_FLUTE(75),
	/**
	 * Blown Bottle (PC# 77)
	 */
	BLOWN_BOTTLE(76),
	/**
	 * Shakuhachi (PC# 78)
	 */
	SHAKUHACHI(77),
	/**
	 * Whistle (PC# 79)
	 */
	WHISTLE(78),
	/**
	 * Ocarina (PC# 80)
	 */
	OCARINA(79),
	/**
	 * Lead 1 (square) (PC# 81)
	 */
	LEAD_1_SQUARE(80),
	/**
	 * Lead 2 (sawtooth) (PC# 82)
	 */
	LEAD_2_SAWTOOTH(81),
	/**
	 * Lead 3 (calliope) (PC# 83)
	 */
	LEAD_3_CALLIOPE(82),
	/**
	 * Lead 4 (chiff) (PC# 84)
	 */
	LEAD_4_CHIFF(83),
	/**
	 * Lead 5 (charang) (PC# 85)
	 */
	LEAD_5_CHARANG(84),
	/**
	 * Lead 6 (voice) (PC# 86)
	 */
	LEAD_6_VOICE(85),
	/**
	 * Lead 7 (fifths) (PC# 87)
	 */
	LEAD_7_FIFTHS(86),
	/**
	 * Lead 8 (bass + lead) (PC# 88)
	 */
	LEAD_8_BASS_LEAD(87),
	/**
	 * Pad 1 (new age) (PC# 89)
	 */
	PAD_1_NEW_AGE(88),
	/**
	 * Pad 2 (warm) (PC# 90)
	 */
	PAD_2_WARM(89),
	/**
	 * Pad 3 (polysynth) (PC# 91)
	 */
	PAD_3_POLYSYNTH(90),
	/**
	 * Pad 4 (choir) (PC# 92)
	 */
	PAD_4_CHOIR(91),
	/**
	 * Pad 5 (bowed) (PC# 93)
	 */
	PAD_5_BOWED(92),
	/**
	 * Pad 6 (metallic) (PC# 94)
	 */
	PAD_6_METALLIC(93),
	/**
	 * Pad 7 (halo) (PC# 95)
	 */
	PAD_7_HALO(94),
	/**
	 * Pad 8 (sweep) (PC# 96)
	 */
	PAD_8_SWEEP(95),
	/**
	 * FX 1 (rain) (PC# 97)
	 */
	FX_1_RAIN(96),
	/**
	 * FX 2 (soundtrack) (PC# 98)
	 */
	FX_2_SOUNDTRACK(97),
	/**
	 * FX 3 (crystal) (PC# 99)
	 */
	FX_3_CRYSTAL(98),
	/**
	 * FX 4 (atmosphere) (PC# 100)
	 */
	FX_4_ATMOSPHERE(99),
	/**
	 * FX 5 (brightness) (PC# 101)
	 */
	FX_5_BRIGHTNESS(100),
	/**
	 * FX 6 (goblins) (PC# 102)
	 */
	FX_6_GOBLINS(101),
	/**
	 * FX 7 (echoes) (PC# 103)
	 */
	FX_7_ECHOES(102),
	/**
	 * FX 8 (sci-fi) (PC# 104)
	 */
	FX_8_SCI_FI(103),
	/**
	 * Sitar (PC# 105)
	 */
	SITAR(104),
	/**
	 * Banjo (PC# 106)
	 */
	BANJO(105),
	/**
	 * Shamisen (PC# 107)
	 */
	SHAMISEN(106),
	/**
	 * Koto (PC# 108)
	 */
	KOTO(107),
	/**
	 * Kalimba (PC# 109)
	 */
	KALIMBA(108),
	/**
	 * Bag pipe (PC# 110)
	 */
	BAG_PIPE(109),
	/**
	 * Fiddle (PC# 111)
	 */
	FIDDLE(110),
	/**
	 * Shanai (PC# 112)
	 */
	SHANAI(111),
	/**
	 * Tinkle Bell (PC# 113)
	 */
	TINKLE_BELL(112),
	/**
	 * Agogo (PC# 114)
	 */
	AGOGO(113),
	/**
	 * Steel Drums (PC# 115)
	 */
	STEEL_DRUMS(114),
	/**
	 * Woodblock (PC# 116)
	 */
	WOODBLOCK(115),
	/**
	 * Taiko Drum (PC# 117)
	 */
	TAIKO_DRUM(116),
	/**
	 * Melodic Tom (PC# 118)
	 */
	MELODIC_TOM(117),
	/**
	 * Synth Drum (PC# 119)
	 */
	SYNTH_DRUM(118),
	/**
	 * Reverse Cymbal (PC# 120)
	 */
	REVERSE_CYMBAL(119),
	/**
	 * Guitar Fret Noise (PC# 121)
	 */
	GUITAR_FRET_NOISE(120),
	/**
	 * Breath Noise (PC# 122)
	 */
	BREATH_NOISE(121),
	/**
	 * Seashore (PC# 123)
	 */
	SEASHORE(122),
	/**
	 * Bird Tweet (PC# 124)
	 */
	BIRD_TWEET(123),
	/**
	 * Telephone Ring (PC# 125)
	 */
	TELEPHONE_RING(124),
	/**
	 * Helicopter (PC# 126)
	 */
	HELICOPTER(125),
	/**
	 * Applause (PC# 127)
	 */
	APPLAUSE(126),
	/**
	 * Gunshot (PC# 128)
	 */
	GUNSHOT(127);

	private final int number;

	/**
	 * Constructs a new value and associates it with the specified patch number.
	 * 
	 * @param number The number of the patch.
	 */
	private InstrumentPatch(int number) {
		this.number = number;
	}

	/**
	 * Gets the patch number of the patch.
	 * 
	 * @return The number of the patch.
	 */
	public int getNumber() {
		return this.number;
	}
}
