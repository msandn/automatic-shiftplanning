package edu.ntnu.bsc020backend;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ShiftScheduleControllerTest {

    @Test
    void index() {
        ShiftScheduleController shiftScheduleController = new ShiftScheduleController();
        String result = shiftScheduleController.index();
        assertEquals(result, "test");
    }
}