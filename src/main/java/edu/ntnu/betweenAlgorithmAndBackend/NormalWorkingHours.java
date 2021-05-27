package edu.ntnu.betweenAlgorithmAndBackend;

import edu.ntnu.bsc020backend.models.Shift;
import edu.ntnu.bsc020backend.models.ShiftSchedule;
import edu.ntnu.bsc020backend.models.SolutionMessage;
import edu.ntnu.sga.Chromosome;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * This class makes and checks restrictions given.
 * Given 2 shifts: if the second shift starts after the first shift + the given interval, it gains a bonus score.
 * If the second shift starts withing the first shift + interval, the score is given based on how many min overlaps, more score for less overlapping.
 *
 */
public class NormalWorkingHours implements Restrictions{
    int interval = 1440; // 1 day
    int minutes = 540; //9 hours
    int worstCase;

    int[] shiftDurations;

    int maxScore = 8;

    int[][] overlappingWork;

    int numberOfEmployees;

    /**
     * Normal constructor
     * @param intervalInDays The interval given in days
     * @param maxMin Max working minutes per given interval
     */
    public NormalWorkingHours(int intervalInDays, int maxMin, int maxScore){
        this.interval = intervalInDays*1440;
        this.minutes = maxMin;
        this.maxScore = maxScore;
    }

    @Override
    public void initialize(ShiftSchedule shiftSchedule) {
        List<Shift> shifts = shiftSchedule.getShifts();

        numberOfEmployees = shiftSchedule.getEmployees().size();

        this.shiftDurations = new int[shifts.size()];
        for (int i = 0; i < shiftDurations.length; i++) {
            shiftDurations[i] = shifts.get(i).getDurationMin();
        }

        overlappingWork = new int[shifts.size()][shifts.size()];
        worstCase = 0;

        for(int i = 0; i<shifts.size(); i++){
            for(int j = 0; j<shifts.size(); j++){
                overlappingWork[i][j]=minWithinX(shifts.get(i).getStartTime(), shifts.get(j).getStartTime(), shifts.get(j).getDurationMin());
            }
        }

        //Worst case if one guy has all the shifts
        int temp = 0;
        for (int[] ints : overlappingWork) {
            for (int j = 0; j < overlappingWork.length; j++) {
                temp += ints[j];
            }
            worstCase += (temp - minutes) ^ 2;
            temp = 0;
        }
    }

    @Override
    public SolutionMessage evaluate(Chromosome chromosome) {
        SolutionMessage solutionMessage = new SolutionMessage("Normal Working Hours, max " + minutes/60 + " hours per " + interval/1440 + " days");

        int[] gene = chromosome.getGenes();

        int temp = 0;
        for (int i = 0; i < overlappingWork.length; i++) {
            for (int j = 0; j < overlappingWork.length; j++) {
                if (gene[i]==gene[j]) temp += overlappingWork[i][j];
            }
            if(temp>minutes){
                solutionMessage.addMessage("Employee: " + gene[i] + " worked " + ((temp-minutes)/60.0) + " hours more than the allowed: " + minutes/60 + " after shift nr " + i);
            }
            temp = 0;
        }

        return solutionMessage;
    }

    @Override
    public int calcFitness(Chromosome chromosome) {
        int[] gene = chromosome.getGenes();

        int evaluate = 0;
        int temp = 0;
        for (int i = 0; i < overlappingWork.length; i++) {
            for (int j = 0; j < overlappingWork.length; j++) {
                if (overlappingWork[i][j]==0) continue;
                if (gene[i]==gene[j]) temp += overlappingWork[i][j];
            }
            if(temp>minutes){
                evaluate+=(temp-minutes)^2;
            }
            temp = 0;
        }
        return (int) (maxScore*(1.0-((double)evaluate/(double) worstCase)));
    }

    //Returns amount of minutes that the second shift impedes on the first shift.
    public int minWithinX(LocalDateTime start1, LocalDateTime start2, int duration2){
        long min = ChronoUnit.MINUTES.between(start1, start2);
        //If the second shift doesn't impede on the first shifts grace period of x hours the method returns 0
        //Or if the second shift starts before the first sift, it's the second shifts problem
        if (min > interval || min<0){
            return 0;
        }
        //We only count the minutes withing the x hours, everything that exceeds gets discarded.
        if(duration2 + min > interval){
            return interval -(int)min;
        }
        //If all of the duration is withing the x hours return the whole duration
        return duration2;
    }
}
