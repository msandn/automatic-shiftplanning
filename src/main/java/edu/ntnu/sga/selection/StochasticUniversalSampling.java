package edu.ntnu.sga.selection;

import edu.ntnu.sga.Chromosome;
import edu.ntnu.sga.Population;
import edu.ntnu.sga.Random;
import edu.ntnu.sga.selection.Selector;

/*StochasticUniversalSampling takes a population and gives a individual a chance to be picked
equal to their fitness in relation to the fitness of the whole population
Unlike a RouletteWheelSelection it picks the whole population at once*/
public class StochasticUniversalSampling implements Selector {
    @Override
    public Chromosome select(Population population) {
        return null;
    }

    @Override
    public Chromosome[] selectMultiple(Population population, int nrToSelect) {
        int F = population.getTotalFitness();
        int N = nrToSelect;
        int P = F/N;
        int start = Random.nextInt(P);
        int[] pointers = new int[N];
        for (int i = 0; i < N; i++) {
            pointers[i] = start+P*i;
        }
        return RWS(population, pointers, F, N, P);

    }

    private Chromosome[] RWS(Population population, int[] points, int F, int N, int P){
        Chromosome[] keep = new Chromosome[population.length()];
        for (int p = 0; p < points.length; p++) {
            int i = 0;
        }
        return keep;
    }
}
