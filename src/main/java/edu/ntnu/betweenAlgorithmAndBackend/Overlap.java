package edu.ntnu.betweenAlgorithmAndBackend;

import edu.ntnu.bsc020backend.models.Shift;
import edu.ntnu.bsc020backend.models.ShiftSchedule;
import edu.ntnu.bsc020backend.models.SolutionMessage;
import edu.ntnu.sga.Chromosome;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * An instance of this class takes a shift schedule and maps all overlapping shifts.
 */
public class Overlap implements Restrictions{
    private int maxScore = 0;
    private int maxOverlap = 0;
    private boolean[][] bitmap;
    private int[][] map;

    /**
     * Creates a new overlap object
     * @param maxScore The value of an avoided possible overlapp
     */
    public Overlap(int maxScore){
        this.maxScore = maxScore;
    }

    /**
     * Takes a shift schedule and makes a map over all overlapping shifts
     * @param shiftSchedule A shift schedule to map
     */
    @Override
    public void initialize(ShiftSchedule shiftSchedule) {
        List<Shift> shifts = shiftSchedule.getShifts();
        bitmap = new boolean[shifts.size()][];
        for (int i = 0; i < bitmap.length; i++) {
            bitmap[i] = new boolean[i];
        }

        //Making a boolean bitmap of the shifts
        for (int i = 0; i < bitmap.length; i++) {
            for (int j = 0; j < bitmap[i].length; j++) {
                bitmap[i][j] = isOverlapping(shifts.get(i), shifts.get(j));
            }
        }

        //Counting the boolean bitmap and making a new int map
        map = new int[bitmap.length][];
        int temp = 0;
        for (int i = 0; i < bitmap.length; i++) {
            for (int j = 0; j < bitmap[i].length; j++) {
                if(bitmap[i][j]){
                    temp++;
                }
            }
            map[i] = new int[temp];
            temp = 0;
        }

        //Filling the new map
        //And checking max overlapp
        temp = 0;
        for (int i = 0; i < bitmap.length; i++) {
            for (int j = 0; j < bitmap[i].length; j++) {
                if(bitmap[i][j]){
                    map[i][temp] = j;
                    temp++;
                    maxOverlap++;
                }
            }
            temp = 0;
        }
        System.out.println("Max Overlap: " + maxOverlap);
    }

    /**
     * Takes a chromosome and checks if it breaks any overlapping restrictions.
     * In the case it does this method creates a message inn the form of a SolutionException object
     * that will inform the client of overlapping shifts in the proposed chromosome
     * @param chromosome The chromosome to evaluate
     * @return A solution exception, or no solution exception
     */
    @Override
    public SolutionMessage evaluate(Chromosome chromosome) {
        SolutionMessage solutionMessage = new SolutionMessage("Overlaping shifts: ");

        List<String> strings = new ArrayList<>();

        int[] genes = chromosome.getGenes();
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (genes[i]==genes[j]){
                    strings.add("Employee nr " + genes[i] + " works both shift nr " + i + " and " + j);
                }
            }
        }

        if (strings.size() == 0){
            solutionMessage.addMessage("There are no overlapping shift given to the same employee");
            return solutionMessage;
        }

        solutionMessage.addMessage("There are " + strings.size() + " cases of overlapping shifts given to employees");

        if (strings.size()>10){
            return solutionMessage;
        }

        for (String str: strings) {
            solutionMessage.addMessage(str);
        }

        return solutionMessage;
    }

    /**
     * Counts the number of overlaps per shift
     * @return An int[] array with the amount of overlapping shifts per shift
     */
    public int[] countOverlaps(){
        int[] arr = new int[map.length];
        for (int i = 0; i < map.length; i++) {
            arr[i] = map[i].length;
        }
        return arr;
    }

    /**
     * Takes a chromosome and checks it with the bitmap and returns a score based on its performance
     * @param chromosome The chromosome to evaluate
     * @return A score
     */
    @Override
    public int calcFitness(Chromosome chromosome) {
        int[] genes = chromosome.getGenes();
        int overlaps = 0;

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (genes[i]==genes[j]){
                    overlaps++;
                }
            }
        }

        return (int) (maxScore * (1.0 - (double) overlaps / (double) maxOverlap));
    }

    /**
     * Checks 2 shifts for any overlap during their running time.
     * @param shift1 The first shift
     * @param shift2 The second shift
     * @return True if they overlap, false if they don't or in the case they are the same shift.
     */
    private boolean isOverlapping(Shift shift1, Shift shift2) {
        if (shift1 == shift2) return false;

        LocalDateTime start1 = shift1.getStartTime();
        LocalDateTime start2 = shift2.getStartTime();

        return start1.isBefore(start2.plusMinutes(shift2.getDurationMin()))
                && start2.isBefore(start1.plusMinutes(shift1.getDurationMin()));
    }

    /**
     * Converts the bitmap to a string
     * @return The bitmap as a string
     */
    public String bitMapToString(){
        if (bitmap == null){
            return "";
        }
        StringBuilder str = new StringBuilder("Bitmap: {");
        for (int i = 0; i < bitmap.length; i++) {
            str.append("\n Shift ").append(i + 1).append(": [");
            if (bitmap[i].length > 0) {
                if (bitmap[i][0]) {
                    str.append("t");
                } else {
                    str.append("f");
                }
            }
            for (int j = 1; j < bitmap[i].length; j++) {
                if (bitmap[i][j]) {
                    str.append(", t");
                } else {
                    str.append(", f");
                }
            }
            str.append("]");
        }
        return str.append("}").toString();
    }

    /**
     * Converts the map to a string
     * @return The map as a string
     */
    public String mapToString(){
        if (map == null){
            return "";
        }
        StringBuilder str = new StringBuilder("Map: {");
        for (int i = 0; i < map.length; i++) {
            str.append("\n Shift ").append(i + 1).append(": [");
            if (map[i].length>0){
                str.append(map[i][0]);
                for (int j = 1; j < map[i].length; j++) {
                    str.append(", ").append(map[i][j]);
                }
            }
            str.append("]");
        }
        return str.append("}").toString();
    }

    @Override
    public String toString() {
        return "Overlap";
    }
}

