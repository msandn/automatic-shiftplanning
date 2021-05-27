package edu.ntnu.sga;

import edu.ntnu.sga.crossover.Crossover;

import java.util.Arrays;

/**
 * Class that is responsible for the creation of Chromosomes.
 * Also can create random population
 */
public class SGAFactory {
    int[][] domain;

    /**
     * Normal constructor
     * @param domain Takes a domain, what value a gene can be assigned to
     */
    public SGAFactory(int[][] domain){
        this.domain = domain;
    }

    /**
     * Creates a random chromosome
     * @return A new random chromosome
     */
    public Chromosome newRandomChromosome() {
        int[] gene = new int[domain.length];
        int temp;
        for (int i = 0; i < gene.length; i++) {
            if (domain[i].length<=0){
                gene[i] = -1;
            } else {
                temp = Random.nextInt(domain[i].length);
                gene[i] = domain[i][temp];
            }
        }
        return new Chromosome(gene);
    }

    /**
     * Creates a new random population
     * @param size Size of the population, number of individuals
     * @return A new random population
     */
    public Population newRandomPopulation(int size) {
        Population population = new Population(size);
        Chromosome temp;
        for (int i = 0; i < size; i++) {
            temp = newRandomChromosome();
            population.addIndividuals(temp);
        }
        return population;
    }

    /**
     * Abstraction for the creation of a gene
     * @param gene A gene
     * @return A new chromosome
     */
    public Chromosome newChromosome(int[] gene){
        return new Chromosome(gene);
    }

    /**
     * Copies a chromosome
     * @param chromosome The chromosome to copy
     * @return New chromosome with identical gene
     */
    public Chromosome copyChromosome(Chromosome chromosome){
        return new Chromosome(Arrays.copyOf(chromosome.getGenes(), chromosome.getGenes().length));
    }
}
