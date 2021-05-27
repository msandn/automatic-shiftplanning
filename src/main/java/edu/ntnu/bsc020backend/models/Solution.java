package edu.ntnu.bsc020backend.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Solution class that contains a solution to a shift schedule problem
 */
public class Solution {
    private int id;
    private List<Integer> solution;
    private List<SolutionMessage> exceptions;

    public Solution(List<Integer> solution){
        this.solution = solution;
    }

    public Solution(List<Integer> solution, SolutionMessage... solutionMessages){
        this.solution = solution;
        exceptions = new ArrayList<>();
        addException(solutionMessages);
    }

    public int get(int i){
        return solution.get(i);
    }

    public int size() {
        return solution.size();
    }

    public List<Integer> getSolution() {
        return solution;
    }

    public void setSolution(List<Integer> solution) {
        this.solution = solution;
    }

    public List<SolutionMessage> getExceptions() {
        return exceptions;
    }

    public SolutionMessage getException(int index){
        return exceptions.get(index);
    }

    public void addException(SolutionMessage exception){
        this.exceptions.add(exception);
    }

    public void addException(SolutionMessage... exception){
        Collections.addAll(this.exceptions, exception);
    }

    public boolean removeException(SolutionMessage exception){
        return exceptions.remove(exception);
    }

    public SolutionMessage removeException(int index){
        return exceptions.remove(index);
    }

    public Solution mergeSolutions(Solution... solution){
        List<Integer> integers = new ArrayList<>();
        for (Solution sol: solution) {
            for (Integer i: sol.getSolution()) {
                integers.add(i);
            }
        }
        return new Solution(integers);
    }

    public static int[] shiftScheduleToArr(ShiftSchedule shiftSchedule){
        int[] arr = new int[shiftSchedule.getShifts().size()];
        Employee temp;
        for (int i = 0; i < arr.length; i++) {
            temp = shiftSchedule.getShifts().get(i).getEmployee();
            if (temp == null){
                arr[i] = -1;
            } else {
                arr[i] = temp.getId();
            }
        }

        return arr;
    }

    @Override
    public String toString() {
        return "Solution{" +
                "id=" + id +
                ", solution=" + solution +
                ", exceptions=" + exceptions +
                '}';
    }
}
