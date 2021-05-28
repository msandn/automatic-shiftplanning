package edu.ntnu.sga;

import edu.ntnu.sga.crossover.Crossover;
import edu.ntnu.sga.crossover.KPointCrossover;
import edu.ntnu.sga.selection.Selector;
import edu.ntnu.sga.selection.TournamentSelection;

import java.util.ArrayList;
import java.util.List;

//Scheduling Genetic Algorithm
public class SGA {
    //Classes
    private SGAFactory sgaFactory;
    private Selector selector;
    private Crossover crossover;
    private List<Fitness> fitnessFunctions;

    //Variables
    private int generations;
    private int populationSize;
    private int eliteToKeep;
    private double mutationChance;
    private double crossOverChance;
    NonRepeatRandom nonRepeatRandom;

    public SGA(int[][] domain){
        this.generations = 100;
        this.populationSize = 10000;
        this.eliteToKeep = (int) (populationSize*0.02);
        this.mutationChance = 0.02;
        this.crossOverChance = 0.9;

        this.sgaFactory = new SGAFactory(domain);
        this.nonRepeatRandom = new NonRepeatRandom(populationSize);

        this.fitnessFunctions = new ArrayList<>();
        this.selector = new TournamentSelection((int) (populationSize*0.05));
        this.crossover = new KPointCrossover(this.sgaFactory, 2);
    }

    public void setGenerations(int generations) {
        this.generations = generations;
    }

    public void setPopulationSize(int populationSize) {
        this.populationSize = populationSize;
        this.nonRepeatRandom = new NonRepeatRandom(populationSize);
    }

    public void addFitness(Fitness fitness){
        fitnessFunctions.add(fitness);
    }

    public Chromosome runSGA(){
        Population pop = sgaFactory.newRandomPopulation(populationSize);
        pop.calcFit(fitnessFunctions);

        int temp = 10;
        System.out.println("Generations: " + generations);
        for (int i = 0; i < generations; i++) {
            pop = formNewPopulation(pop);
            if(i%temp==0){
                System.out.println("Generation: " + i);
                System.out.println(pop.toString());
            }
        }
        System.out.println("Final Generation: ");
        System.out.println(pop.toString());
        return pop.getBestChromosome();
    }

    private Population formNewPopulation(Population population){
        Population pop = new Population(population.population.length);

        //Keep the individuals with highest fitness
        population.sort();
        for (int i = 0; i < eliteToKeep; i++) {
            pop.addIndividuals(population.get(i));
        }

        //Select
        Chromosome[] selectedIndividuals = selector.selectMultiple(population, population.length());

        //Crossover
        crossover(pop, selectedIndividuals);

        //Mutate
        pop.mutate(eliteToKeep, mutationChance, sgaFactory.domain);

        //Calculate fitness
        pop.calcFit(fitnessFunctions);

        return pop;
    }

    private void crossover(Population pop, Chromosome[] chromosomes){
        //Select the initial random index
        nonRepeatRandom.reset();
        int index1 = nonRepeatRandom.nextIndex();
        int index2 = nonRepeatRandom.nextIndex();

        Chromosome[] temp;
        while (index1 >= 0 && index2 >= 0){
            if(crossOverChance>Random.nextDouble(1)){
                temp = crossover.crossover(
                        chromosomes[index1],
                        chromosomes[index2]
                );
            }else {
                temp = new Chromosome[]{chromosomes[index1], chromosomes[index2]};
            }
            if(!pop.addIndividuals(temp)){
                return;
            };


            index1 = nonRepeatRandom.nextIndex();
            index2 = nonRepeatRandom.nextIndex();
        }
        //In case there is one left over that wasn't able to breed:
        if (index1 >= 0){
            pop.addIndividuals(chromosomes[index1]);
        }
        if (index2 >= 0){
            pop.addIndividuals(chromosomes[index2]);
        }
    }
}
