package edu.ntnu.betweenAlgorithmAndBackend;

import edu.ntnu.bsc020backend.models.Employee;
import edu.ntnu.bsc020backend.models.Shift;
import edu.ntnu.bsc020backend.models.ShiftSchedule;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.List;
import java.util.Date;

public class Holidays implements DomainRemover{
    /**
     * Checks for discrepancies between holidays and shifts
     * If they overlap remove the employee from the shifts domain
     * @param shiftSchedule The Shift schedule
     * @param domain        The domain that will be altered
     * @return Returns the altered domain
     */
    @Override
    public int[][] removeDomain(ShiftSchedule shiftSchedule, int[][] domain) {
        DomainRemover.checkParameters(shiftSchedule, domain);
        List<Shift> shifts = shiftSchedule.getShifts();
        List<Employee> employees = shiftSchedule.getEmployees();

        for (int i = 0; i < domain.length; i++) {
            domain[i] = removeHolidays(domain[i], shifts.get(i), employees);
        }

        return domain;
    }

    /**
     * Removes employees from the shift domain if they have holiday during the shift
     * @param domain The shifts domain
     * @param shift The shift
     * @param employees The employees
     * @return The domain without the employees that has holidays
     */
    private int[] removeHolidays(int[] domain, Shift shift, List<Employee> employees){
        for (int i = 0; i < employees.size(); i++) {
            if(isHoliday(shift, employees.get(i))){
                domain[i] = -1;
            }
        }
        return domain;
    }

    /**
     * Checks if a shift overlaps with any of the employees holiday days
     * @param shift The shift to heck
     * @param employee The employee to check
     * @return If the shift overlap with any of the employees holidays return true, otherwise return false
     */
    private boolean isHoliday(Shift shift, Employee employee){
        LocalDateTime shiftStart = shift.getStartTime();
        LocalDateTime shiftEnd = shift.getStartTime().plusMinutes(shift.getDurationMin());

        Date[] days = employee.getHolidayDays();
        for (Date day : days) {
            LocalDateTime date = day.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            if (sameDay(date, shiftStart) || sameDay(date, shiftEnd)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if 2 dates fall on the same day
     * @param date1 The first date
     * @param date2 The second date
     * @return True if they fall on the same date, otherwise returns false
     */
    private boolean sameDay(LocalDateTime date1, LocalDateTime date2){
        return date1.getYear()==date2.getYear() && date1.getDayOfYear() == date2.getDayOfYear();
    }
}
