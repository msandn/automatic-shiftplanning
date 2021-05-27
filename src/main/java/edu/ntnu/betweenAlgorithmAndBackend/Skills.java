package edu.ntnu.betweenAlgorithmAndBackend;

import edu.ntnu.bsc020backend.models.Employee;
import edu.ntnu.bsc020backend.models.Shift;
import edu.ntnu.bsc020backend.models.ShiftSchedule;

import java.util.List;

public class Skills implements DomainRemover{
    /**
     * Removes the employees from the shifts domain if they dont have the necessary skill sett
     * @param shiftSchedule The Shift schedule
     * @param domain        The domain that will be altered
     * @return An altered domain
     */
    @Override
    public int[][] removeDomain(ShiftSchedule shiftSchedule, int[][] domain) {
        DomainRemover.checkParameters(shiftSchedule, domain);

        List<Shift> shifts = shiftSchedule.getShifts();
        List<Employee> employees = shiftSchedule.getEmployees();

        System.out.println("Shifts: " + shifts.toString());
        System.out.println("Employees: "+ employees.toString());

        for (int i = 0; i < domain.length; i++) {
            for (int j = 0; j < domain[i].length; j++) {
                if(!hasSkill(shifts.get(i), employees.get(j))){
                    domain[i][j] = -1;
                }
            }
        }

        return domain;
    }

    /**
     * Checks if the given employee has the necessary skill
     * @param shift The shift with a skill
     * @param employee The employee with a skill
     * @return True if the employee has the skill, false if not
     */
    private boolean hasSkill(Shift shift, Employee employee){
        //If the skill id = 0 then the shift doesn't require any skills
        if (shift.getSkillId() == 0){
            return true;
        }

        for (int i: employee.getSkillIds()) {
            if(i == shift.getSkillId()){
                return true;
            }
        }
        return false;
    }
}
