package edu.ntnu.schedulinggeneticalgorithmold;

import java.util.SplittableRandom;

//Use t.unimi.dsi.util.XoRoShiRo128PlusRandom for random in the future

//Class that mutates a Shift Schedule
//mutation Rate is the amount of iterations, every iteration is one random change
//Might change to percetage based
//Mutation should be deep copied
public class Mutation {
    //Function for mutation
    static public void mutate(int[] shiftSchedule, int[][] domain, int mutationRate) {
        SplittableRandom rand = new SplittableRandom();

        //Replace one shift's employee with another one
        for (int i = 0; i < mutationRate; i++) {
            int j = rand.nextInt(shiftSchedule.length);
            shiftSchedule[j] = domain[j][rand.nextInt(domain[j].length)];
        }
    }
}
