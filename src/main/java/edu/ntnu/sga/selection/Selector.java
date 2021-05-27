package edu.ntnu.sga.selection;

import edu.ntnu.sga.Chromosome;
import edu.ntnu.sga.Population;

public interface Selector {
    public Chromosome select(Population population);

    public default Chromosome[] selectMultiple(Population population, int nrToSelect){
        Chromosome[] chromosomes = new Chromosome[nrToSelect];
        for (int i = 0; i < nrToSelect; i++) {
            chromosomes[i] = select(population);
        }
        return chromosomes;
    }
}
