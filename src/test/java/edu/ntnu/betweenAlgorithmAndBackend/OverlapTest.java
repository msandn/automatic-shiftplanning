package edu.ntnu.betweenAlgorithmAndBackend;

import edu.ntnu.bsc020backend.models.ShiftSchedule;
import edu.ntnu.bsc020backend.models.ShiftScheduleTest;
import edu.ntnu.sga.Chromosome;
import edu.ntnu.sga.SGAFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OverlapTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void testingTheMapping() {
        ShiftSchedule shiftSchedule = ShiftScheduleTest.getMockShiftSchedule(1, 1);
        Overlap overlap = new Overlap(1);
        overlap.initialize(shiftSchedule);
    }

    public static void main(String[] args) {
        ShiftSchedule shiftSchedule = ShiftScheduleTest.getMockShiftSchedule(1, 1);
        Overlap overlap = new Overlap(1);
        overlap.initialize(shiftSchedule);

        System.out.println(overlap.bitMapToString());
        System.out.println(overlap.mapToString());
    }
}