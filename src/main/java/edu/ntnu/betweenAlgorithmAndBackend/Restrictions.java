package edu.ntnu.betweenAlgorithmAndBackend;

import edu.ntnu.bsc020backend.models.ShiftSchedule;
import edu.ntnu.bsc020backend.models.SolutionMessage;
import edu.ntnu.sga.Chromosome;
import edu.ntnu.sga.Fitness;

/**
 * Extends Fitness for extra functionality.
 * For example in the case it has to prepare before being run,
 * or when you have a given solution and need to check it
 */
public interface Restrictions extends Fitness {
    /**
     * Runs once before algorithm to extract information from a shift shcedule
     * @param shiftSchedule The shift schedule
     */
    public void initialize(ShiftSchedule shiftSchedule);

    /**
     * Runs once after the algorithm to catch any mistakes
     * @param chromosome The chromosome to evaluate
     * @return The message to convey in the form of a Solution Message
     */
    public SolutionMessage evaluate(Chromosome chromosome);
}
