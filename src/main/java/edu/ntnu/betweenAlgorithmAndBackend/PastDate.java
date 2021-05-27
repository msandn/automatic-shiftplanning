package edu.ntnu.betweenAlgorithmAndBackend;

import edu.ntnu.bsc020backend.models.Employee;
import edu.ntnu.bsc020backend.models.Shift;
import edu.ntnu.bsc020backend.models.ShiftSchedule;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class PastDate implements DomainRemover{
    LocalDate now;

    public PastDate(LocalDate now){
        this.now = now;
    }

    /** Given a date, checks if the individual shifts already has been finished
     * If finished, set the domain to the single employee who worked the shift
     * Then remove all other employees from the domain
     * @param shiftSchedule The Shift schedule
     * @param domain        The domain that will be altered
     * @return Returns the altered domain
     */
    @Override
    public int[][] removeDomain(ShiftSchedule shiftSchedule, int[][] domain) {
        DomainRemover.checkParameters(shiftSchedule, domain);

        List<Shift> shifts = shiftSchedule.getShifts();
        List<Employee> employees = shiftSchedule.getEmployees();

        //If the shift schedule starts after now skip the whole check
        if (shiftSchedule.getStartDate().isAfter(now)){
            return domain;
        }

        boolean isNotDecided = false;
        for (int i = 0; i < domain.length; i++) {
            if (shifts.get(i).getStartTime().toLocalDate().isBefore(now)){
                Arrays.fill(domain[i], -1);
                if (shifts.get(i).getEmployee() == null){
                    continue;
                }
                for (int j = 0; j < domain[i].length; j++) {
                    if ((shifts.get(i).getEmployee() == employees.get(j))){
                        domain[i][j] = j;
                    }
                }
            }
        }

        return domain;
    }
}
