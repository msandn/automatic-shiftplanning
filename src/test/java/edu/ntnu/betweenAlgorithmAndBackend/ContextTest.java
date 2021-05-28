package edu.ntnu.betweenAlgorithmAndBackend;

import edu.ntnu.bsc020backend.models.GASettings;
import edu.ntnu.bsc020backend.models.ShiftSchedule;
import edu.ntnu.bsc020backend.models.ShiftScheduleTest;
import edu.ntnu.bsc020backend.models.Solution;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ContextTest {
    public static void main(String[] args) {
        ShiftSchedule shiftSchedule;
        shiftSchedule = ShiftScheduleTest.getMockShiftSchedule(1,   12);
        GASettings gaSettings = new GASettings();
        Context context = new Context(gaSettings);
        Solution solution = context.getShiftScheduleSolution(shiftSchedule);
        System.out.println(solution.toString());
    }
}