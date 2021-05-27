package edu.ntnu.sga;

import edu.ntnu.bsc020backend.models.ShiftSchedule;
import edu.ntnu.sga.Chromosome;

public interface Fitness {
    public int calcFitness(Chromosome chromosome);
}
