package edu.ntnu.betweenAlgorithmAndBackend;

import edu.ntnu.bsc020backend.models.Employee;
import edu.ntnu.bsc020backend.models.Shift;
import edu.ntnu.bsc020backend.models.ShiftSchedule;
import edu.ntnu.bsc020backend.models.SolutionMessage;
import edu.ntnu.sga.Chromosome;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class that makes sure a employee works the given amount
 */
public class PercentageOfEmployment implements Restrictions {
    int[] employeeWorkMin;
    int[] hoursPerShift;
    int[] weekIndex;

    boolean overworkAllowed;
    int maxScore;
    int worstScore = 0;

    /**
     * Normal Constructor
     * @param maxScore The score when all restrictions are filled
     * @param overworkAllowed If overwork is allowed one will not be punished for working more than the time given
     */
    public PercentageOfEmployment(boolean overworkAllowed, int maxScore){
        this.overworkAllowed = overworkAllowed;
        this.maxScore = maxScore;
    }

    @Override
    public void initialize(ShiftSchedule shiftSchedule) {
        //List of working min per week
        List<Employee> employees = shiftSchedule.getEmployees();
        employeeWorkMin = new int[employees.size()];
        for (int i = 0; i < employees.size(); i++) {
            employeeWorkMin[i] = employees.get(i).getWorkingHoursInWeek();
        }

        //List of min per shift
        List<Shift> shifts = shiftSchedule.getShifts();
        hoursPerShift = new int[shifts.size()];
        for (int i = 0; i < shifts.size(); i++) {
            hoursPerShift[i] = shifts.get(i).getDurationMin();
        }

        //Dividing into weeks with a array of what shift the week changes, given that the shifts are set up chronologically
        //The logic: If its monday and its not the same day, new week
        List<Integer> dividedIntoWeeks = new ArrayList<>();
        dividedIntoWeeks.add(0);

        LocalDate lastDay = shifts.get(0).getStartTime().toLocalDate();
        LocalDate currentDay;

        for (int i = 1; i < shifts.size(); i++) {
            currentDay = shifts.get(i).getStartTime().toLocalDate();

            if (currentDay.getDayOfWeek() == DayOfWeek.MONDAY && !currentDay.isEqual(lastDay)){
                lastDay = currentDay;
                dividedIntoWeeks.add(i);
            }
        }

        dividedIntoWeeks.add(shifts.size()-1);

        //Fill the int array
        this.weekIndex = new int[dividedIntoWeeks.size()];
        for (int i = 0; i < dividedIntoWeeks.size(); i++) {
            this.weekIndex[i] = dividedIntoWeeks.get(i);
        }

        //Worst score happens if nobody has work, or everyone is overworked.
        for (int i: employeeWorkMin) {
            worstScore+=i;
        }
        worstScore = worstScore*(weekIndex.length-1);
    }

    @Override
    public SolutionMessage evaluate(Chromosome chromosome) {
        SolutionMessage solutionMessage = new SolutionMessage("Percentage of employment");

        int[] genes = chromosome.getGenes();
        int[] workedHours;

        int temp;

        //Each week by itself
        for (int i = 0; i < weekIndex.length-1; i++) {
            //Sum of the hours worked per employee per week
            workedHours = SumWorkedHours(genes, weekIndex[i], weekIndex[i+1]);
            //For each employee
            for (int j = 0; j < workedHours.length; j++) {
                temp = workedVsEmployment(workedHours[j], employeeWorkMin[j], true);
                temp = temp/60;
                if (temp==0) continue;
                if (temp>0 && !overworkAllowed){
                    solutionMessage.addMessage("Employee " + j + " worked " + Math.abs(temp) + " hours too much at week nr " + (i+1));
                } else {
                    solutionMessage.addMessage("Employee " + j + " worked " + Math.abs(temp) + " hours too little at week nr " + (i+1));
                }
            }
        }


        return solutionMessage;
    }

    @Override
    public int calcFitness(Chromosome chromosome) {
        if (employeeWorkMin ==null || hoursPerShift == null || weekIndex == null) return 0;

        int fitness = 0;
        int[] genes = chromosome.getGenes();
        int[] workedHours;

        //Each week by itself
        for (int i = 0; i < weekIndex.length-1; i++) {
            //Sum of the hours worked per employee per week
            workedHours = SumWorkedHours(genes, weekIndex[i], weekIndex[i+1]);
            //For each employee
            for (int j = 0; j < workedHours.length; j++) {
                fitness += workedVsEmployment(workedHours[j], employeeWorkMin[j], false);
            }
        }
        return (int) (maxScore*(1.0 - (double)fitness/(double) worstScore));
    }

    /**
     * Sums the worked hours of any worker given a time span.
     * @param genes Takes a gene
     * @return A list of workers and their total working hours
     */
    private int[] SumWorkedHours(int[] genes, int from, int to){
        //Small check to avoid errors
        if (to>genes.length){
            to = genes.length;
        }
        //amount of hours worked per employee
        int[] workedHours = new int[employeeWorkMin.length];
        for (int i = from; i < to; i++) {
            //genes[i] is the index of the employee with that shift
            //hoursPerShift is the duration of that shift
            workedHours[genes[i]] += hoursPerShift[i];
        }
        return workedHours;
    }

    /**
     * Method that evaluates amount worked against amount in contract.
     * Returns an aggregate based on the logic below
     * @param worked Amount of min worked
     * @param employment Amount of min in employment contract
     * @return Returns amount of hours under or over the goal
     */
    private int workedVsEmployment(int worked, int employment, boolean includeNegative){
        if (includeNegative){
            return worked-employment;
        }
        int i = Math.abs(worked-employment);
        if (i > employment || overworkAllowed){
            return employment;
        }
        return i;
    }
}
