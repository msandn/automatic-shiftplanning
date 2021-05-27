package edu.ntnu.betweenAlgorithmAndBackend;

import edu.ntnu.bsc020backend.models.ShiftSchedule;
import edu.ntnu.bsc020backend.models.ShiftScheduleTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.Assert;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class DomainTest {
    ShiftSchedule shiftSchedule;

    @BeforeEach
    void setUp() {
        shiftSchedule = ShiftScheduleTest.getMockShiftSchedule(1, 1);
    }

    @Test
    void createDomain_With_PastDays() {
        int[][] domain = Domain.buildDomain().addDomainRemover(new PastDate(LocalDate.of(2021, 1, 6))).createDomain(shiftSchedule);
        assertArrayEquals(domain[0], new int[]{});
        assertArrayEquals(domain[37], new int[]{});
        assertArrayEquals(domain[38], new int[]{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27});
        assertArrayEquals(domain[118], new int[]{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27});
    }

    @Test
    void createDomain_With_Holidays() {
        int[][] domain = Domain.buildDomain().addDomainRemover(new Holidays()).createDomain(shiftSchedule);
        assertArrayEquals(domain[0], new int[]{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26});
        assertArrayEquals(domain[56], new int[]{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26});
        assertArrayEquals(domain[57], new int[]{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27});
        assertArrayEquals(domain[118], new int[]{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27});

    }

    @Test
    void createDomain_With_Skills() {
        int[][] domain = Domain.buildDomain().addDomainRemover(new Skills()).createDomain(shiftSchedule);
        System.out.println(Arrays.toString(domain));
        assertArrayEquals(domain[0], new int[]{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27});
        assertArrayEquals(domain[9], new int[]{19,20,21,22,23,24,25,26,27});
        assertArrayEquals(domain[116], new int[]{19,20,21,22,23,24,25,26,27});
        assertArrayEquals(domain[118], new int[]{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27});
    }

    @Test
    void createDomain_With_Holidays_and_Skills() {
        int[][] domain = Domain.buildDomain().
                addDomainRemover(new Skills()).
                addDomainRemover(new Holidays()).
                createDomain(shiftSchedule);
        assertArrayEquals(domain[0], new int[]{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26});
        assertArrayEquals(domain[9], new int[]{19,20,21,22,23,24,25,26});
        assertArrayEquals(domain[116], new int[]{19,20,21,22,23,24,25,26,27});
        assertArrayEquals(domain[118], new int[]{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27});
    }

    @Test
    void createDomain_With_Holidays_and_Skills_and_PastDays() {
        int[][] domain = Domain.buildDomain().
                addDomainRemover(new Skills()).
                addDomainRemover(new Holidays()).
                addDomainRemover(new PastDate(LocalDate.of(2021, 1, 6))).
                createDomain(shiftSchedule);
        assertArrayEquals(domain[0], new int[]{});
        assertArrayEquals(domain[37], new int[]{});
        assertArrayEquals(domain[38], new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26});
        assertArrayEquals(domain[48], new int[]{19, 20, 21, 22, 23, 24, 25, 26});
        assertArrayEquals(domain[116], new int[]{19,20,21,22,23,24,25,26,27});
        assertArrayEquals(domain[118], new int[]{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27});
    }

    public static int[][] getTestDomain(ShiftSchedule shiftSchedule){
        return Domain.buildDomain().
                addDomainRemover(new Skills()).
                addDomainRemover(new Holidays()).
                createDomain(shiftSchedule);
    }

    public static void main(String[] args) {
        ShiftSchedule shiftSchedule = ShiftScheduleTest.getMockShiftSchedule(1, 1);
        int[][] domain = Domain.buildDomain().addDomainRemover(new Skills()).createDomain(shiftSchedule);
        System.out.println("Skills " + Domain.domainToString(domain));

        domain = Domain.buildDomain().addDomainRemover(new Holidays()).createDomain(shiftSchedule);
        System.out.println("Holiday " + Domain.domainToString(domain));

        domain = Domain.buildDomain().addDomainRemover(new PastDate(LocalDate.of(2021, 1, 6))).createDomain(shiftSchedule);
        System.out.println("PastDate " + Domain.domainToString(domain));

        domain = Domain.buildDomain().
                addDomainRemover(new Skills()).
                addDomainRemover(new Holidays()).
                createDomain(shiftSchedule);
        System.out.println("Skills and holidays " + Domain.domainToString(domain));

        domain = Domain.buildDomain().
                addDomainRemover(new Skills()).
                addDomainRemover(new Holidays()).
                addDomainRemover(new PastDate(LocalDate.of(2021, 1, 6))).
                createDomain(shiftSchedule);
        System.out.println("Everything " + Domain.domainToString(domain));
    }
}