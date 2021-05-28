package edu.ntnu.sga;

import edu.ntnu.sga.crossover.Crossover;

import java.util.Arrays;
import java.util.List;

public class Chromosome implements Comparable<Chromosome>{
    Score<SGA> score;
    int fitness = 0;
    boolean calFitness = true;
    final int[] genes;

    public static Crossover crossover;

    Chromosome(int[] genes){
        this.genes = genes;

    }

    //Uniform Mutation
    public int mutate(double mutationChance, int[][] domain) {
        int ret = 0;
        for (int i = 0; i < genes.length; i++) {
            if(mutationChance >= Random.nextDouble(1)) {
                if(domain[i].length == 0){
                    //genes[i] = -1;
                }else{
                    genes[i] = domain[i][Random.nextInt(domain[i].length)];
                    ret++;
                }
            }
        }
        return ret;
    }

    public int getFitness() {
        return fitness;
    }

    public int[] getGenes() {
        return genes;
    }

    public Score<SGA> getScore() {
        return score;
    }

    @Override
    public int compareTo(Chromosome o) {
        return this.score.compareTo(o.getScore());
    }

    @Override
    public String toString() {
        return "\nChromosome{" +
                "fitness=" + fitness +
                ", genes=" + Arrays.toString(genes) +
                '}';
    }
}
