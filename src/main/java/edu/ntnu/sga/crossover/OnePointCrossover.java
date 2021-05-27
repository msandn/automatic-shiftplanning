package edu.ntnu.sga.crossover;

import edu.ntnu.sga.Chromosome;
import edu.ntnu.sga.SGAFactory;

public class OnePointCrossover implements Crossover{
    KPointCrossover multiplePointCrossover;

    public OnePointCrossover(SGAFactory sgaFactory){
        multiplePointCrossover = new KPointCrossover(sgaFactory, 1);
    }

    @Override
    public Chromosome[] crossover(Chromosome parent1, Chromosome parent2) {
        return multiplePointCrossover.crossover(parent1, parent2);
    }
}
