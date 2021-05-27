package edu.ntnu.schedulinggeneticalgorithmold;

import java.util.SplittableRandom;

public class FitnessProportionateSelection {
    public static int Select(int[][] schedules, int[] fit, int size) {
        //Initiate tournament variables
        int[] tournamentFitness = new int[size];
        int[] tournamentAttendees = new int[size];

        //Initiate Random method
        SplittableRandom rand = new SplittableRandom();
        int j;

        //Select 5 random tournament attendee
        for (int i = 0; i < tournamentAttendees.length; i++) {
            j = rand.nextInt(fit.length);
            tournamentAttendees[i] = j;
            tournamentFitness[i] = fit[j];
        }

        //Compare the 5 random tournament atandes and return the best
        int bestFitness = Integer.MAX_VALUE;
        int bestAttendee = 0;
        for (int i = 0; i < tournamentAttendees.length; i++) {
            if (bestFitness > tournamentFitness[i]) {
                bestFitness = tournamentFitness[i];
                bestAttendee = i;
            }
        }
        return tournamentAttendees[bestAttendee];
    }
}
