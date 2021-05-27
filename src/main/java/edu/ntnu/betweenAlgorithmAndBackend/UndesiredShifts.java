package edu.ntnu.betweenAlgorithmAndBackend;

import edu.ntnu.bsc020backend.models.Employee;
import edu.ntnu.bsc020backend.models.Shift;
import edu.ntnu.bsc020backend.models.ShiftSchedule;
import edu.ntnu.bsc020backend.models.SolutionMessage;
import edu.ntnu.sga.Chromosome;

import java.time.LocalDateTime;

public class UndesiredShifts implements Restrictions {
    int[][] undesiredShifts;
    int nrOfWorkers;

    @Override
    public void initialize(ShiftSchedule shiftSchedule) {
        nrOfWorkers = shiftSchedule.getEmployees().size();
        Employee[] employees = (Employee[]) shiftSchedule.getEmployees().toArray();
        Shift[] shifts = (Shift[]) shiftSchedule.getShifts().toArray();

        undesiredShifts = new int[employees.length][shifts.length];

        for (int i = 0; i < employees.length; i++) {
            for (int j = 0; j < shifts.length; j++) {
                //undesiredShifts[i][j] = overlappingMin(shifts[j], employees[i]);
            }
        }
    }

    @Override
    public SolutionMessage evaluate(Chromosome chromosome) {
        return null;
    }

    @Override
    public int calcFitness(Chromosome chromosome) {
        if (undesiredShifts==null) return 0;
        int fitness = 0;
        int[] genes = chromosome.getGenes();
        for (int i = 0; i < genes.length; i++) {
            fitness += undesiredShifts[genes[i]][i];
        }
        return fitness;
    }
/*
    public static int overlappingMin(Shift shift, Employee employee) {
        LocalDateTime[][] undesirable = employee.getUndesirableWorkingHours();
        for (int i = 0; i < employee.getUndesirableWorkingHours().length; i++) {
            if (isOverlapping(shift.getStartTime(), shift.getStartTime().plusMinutes((long) shift.getDurationMin()), undesirable[i][0], undesirable[i][1])){
                return 0;
            }
        }
        return 1;
    }
    */

    public static boolean isOverlapping(LocalDateTime start1, LocalDateTime end1, LocalDateTime start2, LocalDateTime end2) {
        return start1.isBefore(end2) && start2.isBefore(end1);
    }
}
