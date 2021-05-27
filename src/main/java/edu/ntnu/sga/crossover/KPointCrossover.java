package edu.ntnu.sga.crossover;

import edu.ntnu.sga.Chromosome;
import edu.ntnu.sga.SGAFactory;

public class KPointCrossover implements Crossover{
    SGAFactory sgaFactory;
    int k;

    public KPointCrossover(SGAFactory sgaFactory, int k){
        this.sgaFactory = sgaFactory;
        this.k = k;
    }

    public Chromosome[] crossover(Chromosome parent1, Chromosome parent2) {
        int[] p1 = parent1.getGenes();
        int[] p2 = parent2.getGenes();

        //Initiate Children
        int[] child1 = new int[p1.length];
        int[] child2 = new int[p1.length];

        int step = p1.length/(k +1);
        if(step==0){
            step = 1;
        }
        int i = 0;
        boolean oddNumber = true;
        while (i<p1.length/step){
            if(oddNumber){
                oddNumber = false;
                System.arraycopy(p1, i*step, child1, i*step, step);
                System.arraycopy(p2, i*step, child2, i*step, step);
            } else {
                oddNumber = true;
                System.arraycopy(p1, i*step, child2, i*step, step);
                System.arraycopy(p2, i*step, child1, i*step, step);
            }
            i++;
        }
        int remainingAmount = p1.length%step;
        if(!oddNumber){
            System.arraycopy(p1, i*step, child1, i*step, remainingAmount);
            System.arraycopy(p2, i*step, child2, i*step, remainingAmount);
        } else {
            System.arraycopy(p1, i*step, child2, i*step, remainingAmount);
            System.arraycopy(p2, i*step, child1, i*step, remainingAmount);
        }

        return new Chromosome[]{sgaFactory.newChromosome(child1), sgaFactory.newChromosome(child2)};
    }
}
