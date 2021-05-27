package edu.ntnu.betweenAlgorithmAndBackend;

import edu.ntnu.bsc020backend.models.Employee;
import edu.ntnu.bsc020backend.models.ShiftSchedule;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.List;

/**
 * Interface for a domain remover
 */
interface DomainRemover {
    /**
     * A method that alters a domain based on some custom rules given information from a shift schedule
     * If the employee should be removed from the domain set the entry to 0
     * @param shiftSchedule The Shift schedule
     * @param domain The domain that will be altered
     * @return Domain that has been altered
     */
    public int[][] removeDomain(ShiftSchedule shiftSchedule, int[][] domain);

    /**
     * Checks for inconsistencies between the given domain and the shift schedule
     * @param shiftSchedule The Shift schedule
     * @param domain The domain
     * @throws Error If the size is mismatches throw the Error
     */
    static void checkParameters(ShiftSchedule shiftSchedule, int[][] domain){
        if (shiftSchedule.getShifts().size() != domain.length){
            throw new Error("Inconsistency between the domain size and the amount of shifts");
        }
        for (int i = 0; i < domain.length; i++) {
            if (shiftSchedule.getEmployees().size() != domain[i].length){
                throw new Error("Inconsistency between the domain[" + i + "] size and the amount of employees");
            }
        }
    }
}
