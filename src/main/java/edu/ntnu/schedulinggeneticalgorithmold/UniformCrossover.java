package edu.ntnu.schedulinggeneticalgorithmold;
import java.util.SplittableRandom;

//Problemes with reference
//Deep copy requires a lot of processing power and isn't always required.
//Eventually what should be deep copied and wat can be shallow
public class UniformCrossover {
    static int[][] cross(int[] parent1, int[] parent2) {
        //Initiate Children
        int[] child = new int[parent1.length];
        SplittableRandom ran = new SplittableRandom();
        for(int i = 0; i<child.length; i++){
            if(ran.nextDouble()>0.5){
                child[i] = parent1[i];
            } else {
                child[i] = parent2[i];
            }
        }

        return new int[][]{child};
    }
}
