package test;

import edu.ntnu.schedulinggeneticalgorithm.Fitness;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;

import static edu.ntnu.schedulinggeneticalgorithm.Fitness.isOverlapping;
import static java.lang.Thread.sleep;

public class Test {
    private ArrayList<ShiftSchedule> schedules;

    public static void main(String[] args) throws InterruptedException {
        String str = "2020-02-01 09:00";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(str, formatter);

        ArrayList<Employee> employees= new ArrayList<>();
        ArrayList<Shift> shifts = new ArrayList<>();
        Employee emp = new Employee(1, shifts);


        ShiftSchedule tom = new ShiftSchedule(dateTime);
        ShiftSchedule full = new ShiftSchedule(dateTime);
        ShiftSchedule overlapp = new ShiftSchedule(dateTime);

        for(int i = 0; i < 5; i++){
            ArrayList<Shift> scheduleshifts = new ArrayList<Shift>();
            int duration = 300;
            Shift skift1 = new Shift(dateTime.plusDays(i), duration);
            Shift skift2 = new Shift(dateTime.plusDays(i).plusMinutes(duration), duration);
            for(int j = 0; j<3; j++){
                scheduleshifts.add(skift1);
                scheduleshifts.add(skift2);
            }

            for (Shift scheduleshift : scheduleshifts) {
                scheduleshift.addEmployee(emp);

            }
            for (Shift shift : scheduleshifts) {
                full.addShift(shift);
            }
        }


        full.employees.add(emp);

        int overlap = isOverlapping(full);


        System.out.println(overlap);
    }
}
