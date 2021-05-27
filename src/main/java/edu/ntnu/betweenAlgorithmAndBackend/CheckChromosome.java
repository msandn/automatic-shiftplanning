package edu.ntnu.betweenAlgorithmAndBackend;

import edu.ntnu.bsc020backend.models.Solution;
import edu.ntnu.bsc020backend.models.SolutionMessage;
import edu.ntnu.sga.Chromosome;

import java.util.ArrayList;
import java.util.List;

public class CheckChromosome {
    public Solution checkChromosome(Chromosome chromosome, List<Restrictions> restrictions){


        SolutionMessage solutionMessage = new SolutionMessage("Check chromosome");

        for (Restrictions restriction: restrictions) {
            solutionMessage.addMessage(restriction.evaluate(chromosome).toString());
        }


        int[] genes = chromosome.getGenes();
        List<Integer> solution = new ArrayList<>();
        for (int i = 0; i < genes.length; i++) {
            solution.add(genes[i]);
        }

        return new Solution(solution, solutionMessage);
    }
}
