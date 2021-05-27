package edu.ntnu.schedulinggeneticalgorithmold;

import edu.ntnu.bsc020backend.models.ShiftSchedule;

import java.util.Arrays;
import java.util.SplittableRandom;

/*
public class GeneticAlgorithm {
    final double mutationProbability;
    final int population;
    final int mutationRate;
    final int generations;
    int chromosoneSize;

    public GeneticAlgorithm(double mutationProbability, int mutationRate, int population, int generations) {
        this.mutationProbability = mutationProbability;
        this.population = population;
        this.mutationRate = mutationRate;
        this.generations = generations;
    }

    public GeneticAlgorithm() {
        // Between 0 - 1
        this.mutationProbability = 0.1;
        this.population = 100;
        this.mutationRate = 100;
        this.generations = 100;
    }

    public int[] init(ShiftSchedule shiftSchedule) {
        Fitness fitness = new Fitness(shiftSchedule);

        //Initate variables
        int[] fit = new int[population];
        int[][] schedules = new int[population][];
        int[][] newSchedules;
        int[][] domain = shiftSchedule.toDomain();


        System.out.println("Innitial Population");
        //Initiate random population and printing it
        for (int i = 0; i < population; i++) {
            schedules[i] = shiftSchedule.toRandomChromosome();
            //System.out.println(Arrays.toString(schedules[i]));
        }

        chromosoneSize = schedules[0].length;

        for (int i = 0; i < generations; i++) {
            System.out.println("GENERATION: " + i);
            for (int j = 0; j < schedules.length; j++) {
                fit[j] = fitness.evaluate(schedules[j]);
            }

            double sum = 0;
            for (int d : fit) sum += d;

            int min = Integer.MAX_VALUE;
            for(int d: fit){
                if(d<min) min=d;
            }
            System.out.println("Average Fitness: " + sum/fit.length);
            System.out.println("Best Fitness: " + min);

            //Initiate new population
            newSchedules = new int[population][];

            //Variables used in the crossbreeding
            int[] parent1;
            int[] parent2;

            //Crossbreed a new population of the same size based on the old one
            for(int j = 0; j<population;) {
                //Select the parents
                int w1 = FitnessProportionateSelection.Select(schedules, fit, 10);
                int w2 = FitnessProportionateSelection.Select(schedules, fit, 10);
                parent1 = schedules[w1];
                parent2 = schedules[w2];


                //Crosses the parents and returns the children as a array
                int[][] shifts = UniformCrossover.cross(parent1, parent2);

                //Mutates the children if they pass a random check
                SplittableRandom rand = new SplittableRandom();
                for (int[] shift : shifts) {
                    if (rand.nextDouble() < mutationProbability) {
                        Mutation.mutate(shift, domain, mutationRate);
                    }
                }

                //Adds the children to the population making sure it doesn't surpass the population limit
                for(int k = 0; j<population && k<shifts.length; j++, k++){
                    //System.out.println("Child nr " + j + ":" + Arrays.toString(shifts[k]));
                    newSchedules[j] = shifts[k];
                }
            }
            schedules = newSchedules;
        }

        //Innitiate variables to find the best schedule
        int bestSchedule = 0;
        int bestFitness = Integer.MAX_VALUE;
        int temp = Integer.MAX_VALUE;

        //Find the one with the highest fitness
        for (int j = 0; j < schedules.length; j++) {
            temp = fitness.evaluate(schedules[j]);
            if (temp < bestFitness) {
                bestFitness = temp;
                bestSchedule = j;
            }
        }

        //return the one with the highest fitness
        System.out.println("Best fitness was: " + temp);
        System.out.println("BEST SINGEL INDIVIDUALLLL: " + Arrays.toString(schedules[bestSchedule]));
        return schedules[bestSchedule];
    }
}
*/