package edu.ntnu.sga;

import edu.ntnu.sga.selection.Selector;
import io.swagger.models.auth.In;

import java.util.Arrays;
import java.util.List;

//A list off chromosome that represents a generation.
public class Population {
    Chromosome[] population;
    int individuals;

    Population(int maxSize){
       population = new Chromosome[maxSize];
       individuals = 0;
    }

    /**
     * @param chromosome The chromosome to add
     * @return returns true if chromosome was added, returns false if not
     */
   public boolean addIndividuals(Chromosome chromosome){
       if (individuals >= population.length){
            return false;
       } else {
           population[individuals] = chromosome;
           individuals++;
           return true;
       }
   }

    /**
     * @param chromosome The chromosomes to add
     * @return Returns true if all where added, returns false when its filled
     */
   public boolean addIndividuals(Chromosome... chromosome){
       for (Chromosome c: chromosome) {
            if(!this.addIndividuals(c)){
                return false;
            }
       }
       return true;
   }

   public int size(){
        return individuals;
   }

    public int length(){
        return population.length;
    }

   public Chromosome get(int i){
       return population[i];
   }

   public void sort(){
       Arrays.sort(population);
   }

    public Chromosome getBestChromosome(){
       Chromosome currentMax = population[0];
        for (int i = 1; i < population.length; i++) {
            if (currentMax.getFitness() < population[i].getFitness()){
                currentMax = population[i];
            }
        }
        return currentMax;
    }

    public void mutate(int eliteToKeep, double mutationChance, int[][] domain){
        for (int i = eliteToKeep; i < population.length; i++) {
            population[i].mutate(mutationChance, domain);
        }
    }

    public void calcFit(List<Fitness> fitnessList){
        for (Chromosome chromosome: population) {
            chromosome.fitness = 0;
            for (Fitness fitness: fitnessList) {
                chromosome.fitness += fitness.calcFitness(chromosome);
            }
        }
    }


    @Override
    public String toString() {
        return "Population{" +
                " Average Fitness= " + getTotalFitness()/individuals + "\n" +
                " Highest Fitness= " + getBestChromosome().getFitness() + "\n" +
                '}';
    }

    public int getTotalFitness(){
       int sum = 0;
        for (Chromosome c: population) {
            sum+=c.getFitness();
        }
        return sum;
    }

    public int getFitness(int index){
       return population[index].getFitness();
    }

    public int[] getFitness(){
       int[] arr = new int[individuals];

        for (int j = 0; j < individuals; j++) {
            arr[j] = population[j].getFitness();
        }

        return arr;
    }

    public static void main(String[] args) {

    }
}
