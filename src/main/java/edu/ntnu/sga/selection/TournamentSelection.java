package edu.ntnu.sga.selection;

import edu.ntnu.sga.Chromosome;
import edu.ntnu.sga.Population;
import edu.ntnu.sga.Random;

//Selects a couple random individuals then returns the best
public class TournamentSelection implements Selector {
    int nrInTournament = 10;

    public TournamentSelection(int nrInTournament){
        this.nrInTournament = nrInTournament;
    }

    @Override
    public Chromosome select(Population population) {
        Chromosome[] arr = new Chromosome[nrInTournament];

        for (int i = 0; i < nrInTournament; i++) {
            arr[i] = population.get(Random.nextInt(population.length()));
        }
        //Finds the best chromosome
        Chromosome best = arr[0];
        for (int i = 0; i < nrInTournament; i++) {
            if(best.getFitness() < arr[i].getFitness()){
                best = arr[i];
            }
        }
        return best;
    }
}
