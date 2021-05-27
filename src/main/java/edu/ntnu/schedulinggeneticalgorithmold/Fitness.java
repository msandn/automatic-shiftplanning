package edu.ntnu.schedulinggeneticalgorithmold;

import edu.ntnu.bsc020backend.models.Shift;
import edu.ntnu.bsc020backend.models.ShiftSchedule;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class Fitness {
    int[][] overlappingChromosones;
    int[][][] maxWorkPerDay;
    int[][][] maxWorkPerWeek;

    Fitness(ShiftSchedule shiftSchedule){
        init(shiftSchedule);
    }

    public int evaluate(int[] chromosone) {
        return evaluateOverlapp(chromosone)*1000 + evaluateWorkPerDay(chromosone) + evaluateWorkPerWeek(chromosone);
    }

    public void init(ShiftSchedule shiftSchedule){
        initWorkPerDay(shiftSchedule);
        initOverlapp(shiftSchedule);
        initWorkPerWeek(shiftSchedule);
    }

    public int evaluateWorkPerWeek(int[] chromosone){
        int evaluate = 0;
        for(int i = 0; i<chromosone.length; i++){
            for(int j = 0; j<chromosone.length; j++){
                if(chromosone[i] == chromosone[j]){
                    evaluate += maxWorkPerWeek[i][j][0];
                }
            }
        }
        return evaluate;
    }

    public void initWorkPerWeek(ShiftSchedule shiftSchedule){
        List<Shift> shifts = shiftSchedule.getShifts();
        boolean[][] bitMap = new boolean[shifts.size()][shifts.size()];
        maxWorkPerWeek = new int[shifts.size()][shifts.size()][1];

        int count = 0;

        for(int i = 0; i<shifts.size(); i++){
            for(int j = 0; j<shifts.size(); j++){
                maxWorkPerWeek[i][j][0]=minWithinWeek(shifts.get(i).getStartTime(), shifts.get(j).getStartTime(), shifts.get(j).getDurationMin());
            }
        }
    }

    public int minWithinWeek(LocalDateTime start1, LocalDateTime start2, int duration2){
        long min = ChronoUnit.MINUTES.between(start1, start2);
        if (min > 10080 || min<0){
            return 0;
        }
        if(duration2+min>10080){
            return 10080-(int)min;
        }
        return duration2;
    }

    public int evaluateWorkPerDay(int[] chromosone){
        if(chromosone == null){
            return 0;
        }
        int evaluate = 0;
        for(int i = 0; i<chromosone.length; i++){
            for(int j = 0; j<chromosone.length; j++){
                if(chromosone[i] == chromosone[j]){
                    evaluate += maxWorkPerDay[i][j][0];
                }
            }
        }
        return evaluate;
    }

    //Makes a int map over shifts that overlapp within 24 hours and amount of overlapp withing 24 hours
    public void initWorkPerDay(ShiftSchedule shiftSchedule){
        List<Shift> shifts = shiftSchedule.getShifts();
        boolean[][] bitMap = new boolean[shifts.size()][shifts.size()];
        maxWorkPerDay = new int[shifts.size()][shifts.size()][1];

        int count = 0;

        for(int i = 0; i<shifts.size(); i++){
            for(int j = 0; j<shifts.size(); j++){
                maxWorkPerDay[i][j][0]=minWithinDay(shifts.get(i).getStartTime(), shifts.get(j).getStartTime(), shifts.get(j).getDurationMin());
            }
        }
    }


    //Returns amount of minutes that the second shift impedes on the first shift.
    public int minWithinDay(LocalDateTime start1, LocalDateTime start2, int duration2){
        long min = ChronoUnit.MINUTES.between(start1, start2);
        //If the second shift doesn't impede on the first shifts grace period of 24 hours the method returns 0
        //Or if the second shift starts before the first sift, it's the second shifts problem
        if (min > 1440 || min<0){
            return 0;
        }
        //We only count the minutes withing the 24 hours, everything that excedes gets discarded.
        if(duration2+min>1440){
            return 1440-(int)min;
        }
        //If all of the duration is withing the 24 hours return the whole duration
        return duration2;
    }

    public int evaluateOverlapp(int[] chromosone){
        int value = 0;
        for(int i = 0; i<overlappingChromosones.length; i++){
            for(int j = 0; j<overlappingChromosones[i].length; j++){
                if(chromosone[i] == overlappingChromosones[i][j]){
                    value++;
                }
            }
        }
        return value;
    }

    public void initOverlapp(ShiftSchedule shiftSchedule) {
        List<Shift> shifts = shiftSchedule.getShifts();
        boolean[][] bitMap = new boolean[shifts.size()][shifts.size()];
        overlappingChromosones = new int[shifts.size()][];

        int count = 0;

        for(int i = 0; i<shifts.size(); i++){
            for(int j = 0; j<shifts.size(); j++){
                if(isOverlapping(shifts.get(i).getStartTime(), shifts.get(j).getStartTime(), shifts.get(i).getDurationMin(), shifts.get(j).getDurationMin())){
                    bitMap[i][j] = true;
                    count++;
                }
            }
            overlappingChromosones[i] = new int[count];
            count = 0;
        }

        count = 0;
        for(int i = 0; i<bitMap.length; i++){
            for(int j = 0; j<bitMap[i].length; j++){
                if(bitMap[i][j]){
                    overlappingChromosones[i][count] = j;
                    count++;
                }
            }
            count = 0;
        }
    }

    public boolean isOverlapping(LocalDateTime start1, LocalDateTime start2, int duration1, int duration2){
        LocalDateTime end1 = start1.plusMinutes(duration1);
        LocalDateTime end2 = start2.plusMinutes(duration2);
        return start1.isBefore(end2) && start2.isBefore(end1);
    }
}
