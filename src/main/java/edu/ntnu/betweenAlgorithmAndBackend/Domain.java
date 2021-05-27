package edu.ntnu.betweenAlgorithmAndBackend;

import edu.ntnu.bsc020backend.models.Employee;
import edu.ntnu.bsc020backend.models.Shift;
import edu.ntnu.bsc020backend.models.ShiftSchedule;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that makes the domain of the shift schedule
 * What employees can be assigned to what shifts
 * Considering stuff like Skills, Holidays and Available time
 */
public class Domain {
    private List<DomainRemover> domainRemovers;

    /**
     * Private constructor that creates a list of domain removers
     */
    private Domain() {
        domainRemovers = new ArrayList<>();
    }

    /**A domain builder so you can add Domain removers or domain setters to it.
     * @return A new empty domain
     */
    public static Domain buildDomain(){
        return new Domain();
    }

    /**
     * Methods that removes s domain remover from Domain object
     * @param domainRemover The domain remover to remove
     * @return Returns the domain Objects
     */
    public Domain removeDomainRemover(DomainRemover domainRemover) throws Exception {
        if(!domainRemovers.remove(domainRemover)){
            throw new Exception("Can't remove non existent Domain Remover");
        }
        return this;
    }

    /**
     * Methods that adds a domain remover to the Domain object
     * @param domainRemover Required a domain remover
     * @return Returns the domain Objects
     */
    public Domain addDomainRemover(DomainRemover domainRemover){
        domainRemovers.add(domainRemover);
        return this;
    }

    /**
     * Methods that resets the list of Domain Removers
     * @return Returns the domain Objects
     */
    public Domain reset(){
        domainRemovers = new ArrayList<>();
        return this;
    }

    /**
     * The method that creates a full domain where every shift can have every employee
     * then runs it trough the defined domain removers that removes the
     * @param shiftSchedule Take the shift schedule that requires a domain
     * @return Domain in the form of a int array of int arrays
     */
    public int[][] createDomain(ShiftSchedule shiftSchedule) {
        int[][] domain = new int[shiftSchedule.getShifts().size()][shiftSchedule.getEmployees().size()];
        List<Employee> employees = shiftSchedule.getEmployees();

        for (int i = 0; i < domain.length; i++) {
            for (int j = 0; j < employees.size(); j++) {
                //getID needed to get if index is other than 0, 1, 2, 3 ....
                domain[i][j] = j;
            }
        }

        for (DomainRemover domainRemover: domainRemovers) {
            domain = domainRemover.removeDomain(shiftSchedule, domain);
        }

        return removeIntFromArr(domain, -1);
    }

    /**
     * Removes all occurrences of specified int
     * @param array Array to remove all occurrences of specified int from
     * @param intToRemove Specified int to remove from the array
     * @return A new array with all occurrences of the int remove
     */
    private static int[][] removeIntFromArr(int[][] array, int intToRemove){
        int[][] ret = new int[array.length][];
        int targetIndex;

        for (int i = 0; i < ret.length; i++) {
            targetIndex = 0;
            for(int j = 0; j < array[i].length; j++ ) {
                if( array[i][j] != intToRemove)
                    array[i][targetIndex++] = array[i][j];
            }
            ret[i] = new int[targetIndex];
            System.arraycopy( array[i], 0, ret[i], 0, targetIndex);
        }
        return ret;
    }

    public static String domainToString(int[][] domain){
        StringBuilder str = new StringBuilder("Domain: { ");
        for (int i = 0; i < domain.length; i++) {
            if (domain[i].length == 0){
                str.append("\n Shift nr ") .append(i+1).append(": []");
                continue;
            }
            str.append("\n Shift nr ").append(i+1).append(": [").append(domain[i][0]);
            for (int j = 1; j < domain[i].length; j++) {
                str.append(", ").append(domain[i][j]);
            }
            str.append("]");
        }
        return str.append(" }").toString();
    }
}
