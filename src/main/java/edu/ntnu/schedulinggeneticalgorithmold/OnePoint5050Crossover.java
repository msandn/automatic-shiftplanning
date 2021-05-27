package edu.ntnu.schedulinggeneticalgorithmold;

//Problemes with reference
//Deep copy requires a lot of processing power and isn't always required.
//Eventually what should be deep copied and wat can be shallow
public class OnePoint5050Crossover {
    static int[][] cross(int[] parent1, int[] parent2) {
        //Initiate Children
        int[] child1 = new int[parent1.length];
        int[] child2 = new int[parent1.length];
        ;

        //Add left half of parents to each child
        for (int i = 0; i < parent1.length / 2; i++) {
            child1[i] = parent1[i];
            child2[i] = parent2[i];
        }
        //Add right half of parents to each child
        for (int i = parent1.length / 2; i < parent1.length; i++) {
            child1[i] = parent2[i];
            child2[i] = parent1[i];
        }

        return new int[][]{child1, child2};
    }
}
