package edu.ntnu.bsc020backend.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ShiftScheduleTest {

    public static ShiftSchedule getMockShiftSchedule(int i, int nrOfWeeks){
        switch (i){
            case 1:
                return homeNursingMockSchedule(nrOfWeeks);
            case 2:
                return homeNursingMockScheduleV2(nrOfWeeks);
            default:
                return createMockSchedule();
        }
    }

    static ShiftSchedule homeNursingMockScheduleV2(int nrOfWeeks){
        /* Description of the test data in norweagian
        12 uker turnus
        20 pers 7 med 50%, 10 med 75%, 3 med 100%
        9 sykepleier, 7 med 100%, 2 med 75%
        Noen som kun har helgestillinger, 12 % feks

        Hverdag
        Dag: 9 vanlig + 2 inne sykepleier + 2 sykepleier vakter
        Kveld: 3 + 1 vakter
        Natt: 2 vakter

        HELG
        Dag: 5  + 1 vakter
        Kveld: 3 + 1 vakter
        Natt: 2 vakter
         */
        int specialNurses = 9;
        int nurses = 20;
        List<Employee> employees = new ArrayList<>();
        List<Shift> shifts = new ArrayList<>();

        //Nurse with 50%
        for (int i = 0; i < nurses/3; i++) { employees.add(new Employee(new int[]{1}, 20*60, null)); }
        //Nurse with 75%
        for (int i = 0; i < nurses/2; i++) { employees.add(new Employee(new int[]{1}, 30*60, null)); }
        //Nurse with 100%
        for (int i = 0; i < nurses/6; i++) { employees.add(new Employee(new int[]{1}, 40*60, null)); }
        //Special Nurse with 75%
        for (int i = 0; i < 7; i++) { employees.add(new Employee(new int[]{1, 2}, 30*60, null)); }
        //Special Nurse with 100%
        for (int i = 0; i < 2; i++) { employees.add(new Employee(new int[]{1, 2}, 40*60, null)); }

        //Adding holidays for first and last employee, should have holiday the 3 days
        Date[] holidays = new Date[3];
        for (int i = 0; i < holidays.length; i++) {
            holidays[i] = new Date(121, Calendar.JANUARY, 4+i);
        }

        employees.get(employees.size()-1).setHolidayDays(holidays);
        employees.get(0).setHolidayDays(holidays);

        //Shifts
        for (int i = 0; i < nrOfWeeks; i++) {
            //Weekdays
            for (int j = 0; j < 5; j++) {
                //9 normal shifts every day
                LocalDateTime day = LocalDateTime.of(2021, 1, 4+j+i, 8, 0);
                int dayDuration = 480;
                shifts.add(new Shift(day, dayDuration, 1));
                shifts.add(new Shift(day, dayDuration, 1));
                shifts.add(new Shift(day, dayDuration, 1));
                shifts.add(new Shift(day, dayDuration, 1));
                shifts.add(new Shift(day, dayDuration, 1));
                shifts.add(new Shift(day, dayDuration, 1));
                shifts.add(new Shift(day, dayDuration, 1));
                shifts.add(new Shift(day, dayDuration, 1));
                shifts.add(new Shift(day, dayDuration, 1));
                //4 special shift each day
                shifts.add(new Shift(day, dayDuration, 2));
                shifts.add(new Shift(day, dayDuration, 2));
                shifts.add(new Shift(day, dayDuration, 2));
                shifts.add(new Shift(day, dayDuration, 2));

                //3 every weekend evening
                LocalDateTime evening = LocalDateTime.of(2021, 1, 4+j+i, 15, 45);
                int eveningDuration = 480;
                shifts.add(new Shift(evening, eveningDuration, 1));
                shifts.add(new Shift(evening, eveningDuration, 1));
                shifts.add(new Shift(evening, eveningDuration, 1));
                //1 Special shift each weekend evening
                shifts.add(new Shift(evening, eveningDuration, 2));

                //4 every night
                LocalDateTime night = LocalDateTime.of(2021, 1, 4+j+i, 23, 30);
                int nightDuration = 525;
                shifts.add(new Shift(night, nightDuration, 1));
                shifts.add(new Shift(night, nightDuration, 1));
            }
            //Weekend
            for (int j = 5; j < 7; j++) {
                //5 normal shifts every weekend day
                LocalDateTime day = LocalDateTime.of(2021, 1, 4+j+i, 8, 0);
                int dayDuration = 480;
                shifts.add(new Shift(day, dayDuration, 1));
                shifts.add(new Shift(day, dayDuration, 1));
                shifts.add(new Shift(day, dayDuration, 1));
                shifts.add(new Shift(day, dayDuration, 1));
                shifts.add(new Shift(day, dayDuration, 1));
                //1 special shift each weekend day
                shifts.add(new Shift(day, dayDuration, 2));

                //3 every evening
                LocalDateTime evening = LocalDateTime.of(2021, 1, 4+j+i, 15, 45);
                int eveningDuration = 480;
                shifts.add(new Shift(evening, eveningDuration, 1));
                shifts.add(new Shift(evening, eveningDuration, 1));
                shifts.add(new Shift(evening, eveningDuration, 1));
                //1 Special shift each evening
                shifts.add(new Shift(evening, eveningDuration, 2));

                //4 every night
                LocalDateTime night = LocalDateTime.of(2021, 1, 4+j+i, 23, 30);
                int nightDuration = 525;
                shifts.add(new Shift(night, nightDuration, 1));
                shifts.add(new Shift(night, nightDuration, 1));
            }
        }

        LocalDate startDate = LocalDate.of(2021, 1, 4);
        return new ShiftSchedule(startDate, employees, shifts);
    }

    static ShiftSchedule homeNursingMockSchedule(int nrOfWeeks){
        /* Description of the test data in norweagian
        12 uker turnus
        20 pers 7 med 50%, 10 med 75%, 3 med 100%
        9 sykepleier, 7 med 100%, 2 med 75%
        Noen som kun har helgestillinger, 12 % feks

        Hverdag
        Dag: 9 vanlig + 2 inne sykepleier + 2 sykepleier vakter
        Kveld: 3 + 1 vakter
        Natt: 2 vakter

        HELG
        Dag: 5  + 1 vakter
        Kveld: 3 + 1 vakter
        Natt: 2 vakter
         */
        int specialNurses = 9;
        int nurses = 20;
        List<Employee> employees = new ArrayList<>();
        List<Shift> shifts = new ArrayList<>();

        //Nurse with 50%
        for (int i = 0; i < nurses/3; i++) { employees.add(new Employee(new int[]{1}, 20*60, null)); }
        //Nurse with 75%
        for (int i = 0; i < nurses/2; i++) { employees.add(new Employee(new int[]{1}, 30*60, null)); }
        //Nurse with 100%
        for (int i = 0; i < nurses/6; i++) { employees.add(new Employee(new int[]{1}, 40*60, null)); }
        //Special Nurse with 75%
        for (int i = 0; i < 7; i++) { employees.add(new Employee(new int[]{1, 2}, 30*60, null)); }
        //Special Nurse with 100%
        for (int i = 0; i < 2; i++) { employees.add(new Employee(new int[]{1, 2}, 40*60, null)); }

        //Adding holidays for first and last employee, should have holiday the 3 days
        Date[] holidays = new Date[3];
        for (int i = 0; i < holidays.length; i++) {
            holidays[i] = new Date(121, Calendar.JANUARY, 4+i);
        }

        employees.get(employees.size()-1).setHolidayDays(holidays);
        employees.get(0).setHolidayDays(holidays);

        //Shifts
        for (int i = 0; i < nrOfWeeks; i++) {
            //Weekdays
            for (int j = 0; j < 5; j++) {
                //9 normal shifts every day
                LocalDateTime day = LocalDateTime.of(2021, 1, 4+j+i, 8, 0);
                int dayDuration = 480;
                shifts.add(new Shift(day, dayDuration, 1));
                shifts.add(new Shift(day, dayDuration, 1));
                shifts.add(new Shift(day, dayDuration, 1));
                shifts.add(new Shift(day, dayDuration, 1));
                shifts.add(new Shift(day, dayDuration, 1));
                shifts.add(new Shift(day, dayDuration, 1));
                shifts.add(new Shift(day, dayDuration, 1));
                shifts.add(new Shift(day, dayDuration, 1));
                shifts.add(new Shift(day, dayDuration, 1));
                //4 special shift each day
                shifts.add(new Shift(day, dayDuration, 2));
                shifts.add(new Shift(day, dayDuration, 2));
                shifts.add(new Shift(day, dayDuration, 2));
                shifts.add(new Shift(day, dayDuration, 2));

                //3 every weekend evening
                LocalDateTime evening = LocalDateTime.of(2021, 1, 4+j+i, 15, 45);
                int eveningDuration = 480;
                shifts.add(new Shift(evening, eveningDuration, 1));
                shifts.add(new Shift(evening, eveningDuration, 1));
                shifts.add(new Shift(evening, eveningDuration, 1));
                //1 Special shift each weekend evening
                shifts.add(new Shift(evening, eveningDuration, 2));

                //4 every night
                LocalDateTime night = LocalDateTime.of(2021, 1, 4+j+i, 23, 30);
                int nightDuration = 525;
                shifts.add(new Shift(night, nightDuration, 1));
                shifts.add(new Shift(night, nightDuration, 1));
            }
            //Weekend
            for (int j = 5; j < 7; j++) {
                //5 normal shifts every weekend day
                LocalDateTime day = LocalDateTime.of(2021, 1, 4+j+i, 8, 0);
                int dayDuration = 480;
                shifts.add(new Shift(day, dayDuration, 1));
                shifts.add(new Shift(day, dayDuration, 1));
                shifts.add(new Shift(day, dayDuration, 1));
                shifts.add(new Shift(day, dayDuration, 1));
                shifts.add(new Shift(day, dayDuration, 1));
                //1 special shift each weekend day
                shifts.add(new Shift(day, dayDuration, 2));

                //3 every evening
                LocalDateTime evening = LocalDateTime.of(2021, 1, 4+j+i, 15, 45);
                int eveningDuration = 480;
                shifts.add(new Shift(evening, eveningDuration, 1));
                shifts.add(new Shift(evening, eveningDuration, 1));
                shifts.add(new Shift(evening, eveningDuration, 1));
                //1 Special shift each evening
                shifts.add(new Shift(evening, eveningDuration, 2));

                //4 every night
                LocalDateTime night = LocalDateTime.of(2021, 1, 4+j+i, 23, 30);
                int nightDuration = 525;
                shifts.add(new Shift(night, nightDuration, 1));
                shifts.add(new Shift(night, nightDuration, 1));
            }
        }

        LocalDate startDate = LocalDate.of(2021, 1, 4);
        return new ShiftSchedule(startDate, employees, shifts);
    }

    static ShiftSchedule createMockSchedule(){
        int numberOfEmployees = 6;
        int numberOfShifts = 42;
        List<Employee> employees = new ArrayList<>();
        List<Shift> shifts = new ArrayList<>();

        //New employees
        Employee temp;
        for(int i = 0; i<numberOfEmployees; i++){
            temp = new Employee(i);
            employees.add(temp);
        }
        LocalDateTime date = LocalDateTime.of(2021, 2, 28, 16, 20);
        int counter = 0;
        int weekendCounter = 0;
        for(int i = 0; i<numberOfShifts; i = i+6){
            shifts.add(new Shift(date.plusHours(16), 480, counter++));
            shifts.add(new Shift(date.plusHours(16), 480, counter++));
            shifts.add(new Shift(date.plusHours(16), 480, counter++));

            shifts.add(new Shift(date.plusHours(24), 480, counter++));
            shifts.add(new Shift(date.plusHours(24), 480, counter++));
            shifts.add(new Shift(date.plusHours(24), 480, counter++));
            weekendCounter++;
            if(weekendCounter == 5){
                weekendCounter = 0;
                date = date.plusDays(3);
            } else {
                date = date.plusDays(1);
            }
        }
        return new ShiftSchedule(LocalDate.of(2021, 3, 1), employees, shifts);
    }

    public static void main(String[] args) {
        ShiftSchedule testSchedule = ShiftScheduleTest.getMockShiftSchedule(1, 12);
        System.out.println("Too String");
        System.out.println(testSchedule.toString());
    }
}
