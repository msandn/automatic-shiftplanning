package edu.ntnu.sga.crossover;

import edu.ntnu.sga.Chromosome;

import java.util.Arrays;
import java.util.List;

public interface Crossover {
    Chromosome[] crossover(Chromosome parent1, Chromosome parent2);
}
