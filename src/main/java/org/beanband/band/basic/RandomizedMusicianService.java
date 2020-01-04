package org.beanband.band.basic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.sound.midi.InvalidMidiDataException;

import org.beanband.model.midi.InstrumentPatch;
import org.beanband.model.midi.MidiElement;
import org.beanband.model.midi.MidiNoteElement;
import org.beanband.model.midi.MidiPercussionElement;
import org.beanband.model.midi.MidiProgramChangeElement;
import org.beanband.model.midi.NotePitch;
import org.beanband.model.midi.PercussionKey;

/**
 * This class provides basic services usable my {@code Musician} implementations
 * to create {@code MidiElements} with some randomness. Most parameters driving
 * the random number generation are tunable using the provided setters.
 * 
 * @author Michael Niemeck
 */
public class RandomizedMusicianService {
	private static final double DEFAULT_START_DEVIATION = 0.003;
	private static final double DEFAULT_DURATION_DEVIATION = 0.003;
	private static final double DEFAULT_ON_VELOCITY_DEVIATION = 2.0;

	private static final double DEFAULT_FRET_PROBABILITY_MEAN = 125.0;
	private static final double DEFAULT_FRET_PROBABILIY_DEVIATION = 175.0;
	private static final int DEFAULT_FRET_PITCH_MIN = 20;
	private static final int DEFAULT_FRET_PITCH_MAX = 100;
	private static final InstrumentPatch DEFAULT_FRET_ORIGINAL_PATCH = InstrumentPatch.ACOUSTIC_GUITAR_NYLON;

	private static final int DEFAULT_OFF_VELOCITY = 127;

	private final Random random = new Random();

	private double startDeviation;
	private double durationDeviation;
	private double onVelocityDeviation;
	private double fretProbabilityMean;
	private double fretProbabilityDeviation;
	private int fretPitchMin;
	private int fretPitchMax;
	private InstrumentPatch fretOriginalPatch;

	/**
	 * Create a new {@code RandomizedMusicianService} instance. All random
	 * parameters are set to default values. Refer to the individual setters for
	 * each default value.
	 */
	public RandomizedMusicianService() {
		startDeviation = DEFAULT_START_DEVIATION;
		durationDeviation = DEFAULT_DURATION_DEVIATION;
		onVelocityDeviation = DEFAULT_ON_VELOCITY_DEVIATION;
		fretProbabilityMean = DEFAULT_FRET_PROBABILITY_MEAN;
		fretProbabilityDeviation = DEFAULT_FRET_PROBABILIY_DEVIATION;
		fretPitchMin = DEFAULT_FRET_PITCH_MIN;
		fretPitchMax = DEFAULT_FRET_PITCH_MAX;
		fretOriginalPatch = DEFAULT_FRET_ORIGINAL_PATCH;
	}

	/**
	 * Sets the standard deviation when generating randomized start values. The mean
	 * is always the specified start value. As always in the Music Model, the unit
	 * is fractions of one whole bar. The default value is
	 * {@value #DEFAULT_START_DEVIATION}.
	 * 
	 * @param startDeviation The standard deviation when generating randomized start
	 *                       values.
	 * @see Random#nextGaussian()
	 */
	public void setStartDeviation(double startDeviation) {
		this.startDeviation = startDeviation;
	}

	/**
	 * Sets the standard deviation when generating randomized duration values. The
	 * mean is always the specified duration value. As always in the Music Model,
	 * the unit is fractions of one whole bar. The default value is
	 * {@value #DEFAULT_DURATION_DEVIATION}.
	 * 
	 * @param durationDeviation The standard deviation when generating randomized
	 *                          duration values.
	 * @see Random#nextGaussian()
	 */
	public void setDurationDeviation(double durationDeviation) {
		this.durationDeviation = durationDeviation;
	}

	/**
	 * Sets the standard deviation when generating randomized on velocity values.
	 * The mean is always the specified on velocity value. The unit is the standard
	 * MIDI velocity, ranging from 0 to 127. The default value is
	 * {@value #DEFAULT_ON_VELOCITY_DEVIATION}.
	 * 
	 * @param onVelocityDeviation The standard deviation when generating randomized
	 *                            on velocity values.
	 * @see Random#nextGaussian()
	 */
	public void setOnVelocityDeviation(double onVelocityDeviation) {
		this.onVelocityDeviation = onVelocityDeviation;
	}

	/**
	 * Sets the mean value when generating fret noise for guitar instruments. The
	 * unit is milliseconds, and the randomization is based on the time the guitar
	 * musician has to change chords. Shorter change times will have a higher
	 * probability of creating a fret noise. Change times matching the value
	 * specified by {@code setFretProbabilityMean} will have a fifty percent chance
	 * of generating a fret noise. The default value is
	 * {@value #DEFAULT_FRET_PROBABILITY_MEAN}.
	 * 
	 * @param fretProbabilityMean The standard deviation when generating randomized
	 *                            fret noise events.
	 * @see Random#nextGaussian()
	 * @see #createFretNoise(double, double, long)
	 */
	public void setFretProbabilityMean(double fretProbabilityMean) {
		this.fretProbabilityMean = fretProbabilityMean;
	}

	/**
	 * Sets the standard deviation when generating randomized fret noise events. See
	 * {@code setFretProbabilityMean} for details. The default value is
	 * {@value #DEFAULT_FRET_PROBABILIY_DEVIATION}.
	 * 
	 * @param fretProbabilityDeviation The standard deviation when generating
	 *                                 randomized fret noise events.
	 * @see #setFretProbabilityMean(double)
	 */
	public void setFretProbabilityDeviation(double fretProbabilityDeviation) {
		this.fretProbabilityDeviation = fretProbabilityDeviation;
	}

	/**
	 * Sets the minimum pitch when generating randomized fret noise events. Fret
	 * noises are a standard MIDI patch, so events have all properties of note
	 * events, such as velocity, duration, and, of course, pitch. All parameters are
	 * generated randomly, but not all pitches sound realistic. This method limits
	 * the range from which pitches are chosen. The default value is
	 * {@value #DEFAULT_FRET_PITCH_MIN}.
	 * 
	 * @param fretPitchMin The minimum pitch when generating randomized fret noise
	 *                     events.
	 * @throws InvalidMidiDataException When the pitch is outside the regular MIDI
	 *                                  range (0-127), or when it is higher than the
	 *                                  maximum pitch.
	 * @see #setFretPitchMax(int)
	 */
	public void setFretPitchMin(int fretPitchMin) throws InvalidMidiDataException {
		if ((fretPitchMin < 0) || (fretPitchMin > 127) || (fretPitchMin > fretPitchMax)) {
			throw new InvalidMidiDataException(
					"Pitch must be between 0 and 127, and must not be greater than the maximum pitch.");
		}
		this.fretPitchMin = fretPitchMin;
	}

	/**
	 * Sets the maximum pitch when generating randomized fret noise events. See
	 * {@code setFretPitchMin} for details. The default value is
	 * {@value #DEFAULT_FRET_PITCH_MAX}.
	 * 
	 * @param fretPitchMax The maximum pitch when generating randomized fret noise
	 *                     events.
	 * @throws InvalidMidiDataException When the pitch is outside the regular MIDI
	 *                                  range (0-127), or when it is lower than the
	 *                                  minimum pitch.
	 * @see #setFretPitchMin(int)
	 */
	public void setFretPitchMax(int fretPitchMax) throws InvalidMidiDataException {
		if ((fretPitchMax < 0) || (fretPitchMax > 127) || (fretPitchMax < fretPitchMin)) {
			throw new InvalidMidiDataException(
					"Pitch must be between 0 and 127, and must not be lower than the minimum pitch.");
		}
		this.fretPitchMax = fretPitchMax;
	}

	/**
	 * Fret noise are produced by sending a {@code MidiProgramChangeElement} to
	 * switch to {@code InstrumentPatch.GUITAR_FRET_NOISE}, sending a
	 * {@code MidiNoteElement} to produce the actual fret noise, and then switching
	 * back to the original instrument patch. This method sets the
	 * {@code InstrumentPatch} that should be switched back to.
	 * 
	 * @param fretOriginalPatch The original {@code InstrumentPatch} to switch back
	 *                          to after a fret noise.
	 */
	public void setFretOriginalPatch(InstrumentPatch fretOriginalPatch) {
		this.fretOriginalPatch = fretOriginalPatch;
	}

	/**
	 * Creates a randomized {@code MidiNoteElement} based on the specified values
	 * and the internal randomization parameters.
	 * 
	 * @param pitch      The pitch of the {@code MidiNoteElement}, specified as a
	 *                   {@code NotePitch} element.
	 * @param start      The mean start time, specified in fractions of a whole bar.
	 * @param duration   The mean duration of the note, specified in fractions of a
	 *                   whole bar.
	 * @param onVelocity The mean on velocity, specified in the standard MIDI range
	 *                   (0-127).
	 * @return The generated {@code MidiElement}
	 * @throws InvalidMidiDataException When the creation of the {@code MidiElement}
	 *                                  encounters a problem.
	 */
	public MidiElement createRandomizedElement(NotePitch pitch, double start, double duration, int onVelocity)
			throws InvalidMidiDataException {
		double actualStart = Math.max(0.00025, random.nextGaussian() * startDeviation + start);
		double actualDuration = random.nextGaussian() * durationDeviation + duration;
		int actualOnVelocity = (int) Math.round(random.nextGaussian() * onVelocityDeviation + onVelocity);
		actualOnVelocity = Math.max(0, actualOnVelocity);
		actualOnVelocity = Math.min(127, actualOnVelocity);
		int actualOffVelocity = DEFAULT_OFF_VELOCITY;

		return new MidiNoteElement(pitch, actualStart, actualDuration, actualOnVelocity, actualOffVelocity);
	}

	/**
	 * Creates a {@code List} of randomized {@code MidiNoteElement} based on the
	 * specified values and the internal randomization parameters. This method is
	 * designed to randomize chords composed of multiple {@code NotePitch} elements,
	 * so all notes are randomized uniformly, so that the chord, despite variation,
	 * will still sound homogenous.
	 * 
	 * @param voicing    The pitches of the {@code MidiNoteElement} obects,
	 *                   specified as a {@code List} of {@code NotePitch} elements.
	 * @param start      The mean start time, specified in fractions of a whole bar.
	 * @param duration   The mean duration of the note, specified in fractions of a
	 *                   whole bar.
	 * @param onVelocity The mean on velocity, specified in the standard MIDI range
	 *                   (0-127).
	 * @return The {@code List} of generated {@code MidiElement}
	 * @throws InvalidMidiDataException When the creation of the {@code MidiElement}
	 *                                  encounters a problem.
	 */
	public List<MidiElement> createRandomizedElement(List<NotePitch> voicing, double start, double duration,
			int onVelocity) throws InvalidMidiDataException {
		double actualStart = Math.max(0.00025, random.nextGaussian() * startDeviation + start);
		double actualDuration = random.nextGaussian() * durationDeviation + duration;
		int actualOnVelocity = (int) Math.round(random.nextGaussian() * onVelocityDeviation + onVelocity);
		actualOnVelocity = Math.max(0, actualOnVelocity);
		actualOnVelocity = Math.min(127, actualOnVelocity);
		int actualOffVelocity = DEFAULT_OFF_VELOCITY;

		List<MidiElement> elements = new ArrayList<>();
		for (NotePitch notePitch : voicing) {
			elements.add(
					new MidiNoteElement(notePitch, actualStart, actualDuration, actualOnVelocity, actualOffVelocity));
		}
		return elements;
	}

	/**
	 * Creates a randomized {@code MidiPercussionElement} based on the specified
	 * values and the internal randomization parameters.
	 * 
	 * @param key        The key of the {@code MidiPercussionElement}, specified as
	 *                   a {@code PercussionKey} element.
	 * @param start      The mean start time, specified in fractions of a whole bar.
	 * @param onVelocity The mean on velocity, specified in the standard MIDI range
	 *                   (0-127).
	 * @return The generated {@code MidiElement}
	 * @throws InvalidMidiDataException When the creation of the {@code MidiElement}
	 *                                  encounters a problem.
	 */
	public MidiElement createRandomizedElement(PercussionKey key, double start, int onVelocity)
			throws InvalidMidiDataException {
		double actualStart = Math.max(0.00025, random.nextGaussian() * startDeviation + start);
		int actualOnVelocity = (int) Math.round(random.nextGaussian() * onVelocityDeviation + onVelocity);
		actualOnVelocity = Math.max(0, actualOnVelocity);
		actualOnVelocity = Math.min(127, actualOnVelocity);
		int actualOffVelocity = DEFAULT_OFF_VELOCITY;

		return new MidiPercussionElement(key, actualStart, actualOnVelocity, actualOffVelocity);
	}

	/**
	 * Creates a random fret noise. This method will not always return an actual
	 * fret noise. The probability is based on the time the guitar musician has to
	 * change chords. Since a fret noise requires a {@code MidiProgramChangeElement}
	 * before and after the fret noise, the result is actually a series of three
	 * {@code MidiElement} objects.
	 * 
	 * @param start    The start of the time interval in which the fret noise can
	 *                 occur, specified in fractions of a whole bar.
	 * @param duration The duration of the time interval in which the fret noise can
	 *                 occur, specified in fractions of a whole bar.
	 * @param msPerBar The length of one bar, specified in milliseconds.
	 * @return The {@code List} of {@code MidiElement} objects making up the fret
	 *         noise. Returns an empty {@code List}, if no fret noise has been
	 *         produced.
	 * @throws InvalidMidiDataException When the creation of the {@code MidiElement}
	 *                                  encounters a problem.
	 */
	public List<MidiElement> createFretNoise(double start, double duration, long msPerBar)
			throws InvalidMidiDataException {
		if (random.nextGaussian() * fretProbabilityDeviation + fretProbabilityMean <= (duration * msPerBar)) {
			return Collections.emptyList();
		}

		double startMean = start + duration / 2;
		double startDev = duration / 6;

		double actualStart = Math.max(0.00025, random.nextGaussian() * startDev + startMean);

		double durationMean = duration / 2;
		double durationDev = duration / 100;

		double actualDuration = random.nextGaussian() * durationDev + durationMean;

		NotePitch actualPitch = new NotePitch(fretPitchMin + random.nextInt(fretPitchMax - fretPitchMin));

		List<MidiElement> elements = new ArrayList<>();
		elements.add(new MidiProgramChangeElement(InstrumentPatch.GUITAR_FRET_NOISE, actualStart - 0.01));
		elements.add(new MidiNoteElement(actualPitch, actualStart, actualDuration, 64, 0));
		elements.add(new MidiProgramChangeElement(fretOriginalPatch, actualStart + 0.01));
		return elements;
	}

}
