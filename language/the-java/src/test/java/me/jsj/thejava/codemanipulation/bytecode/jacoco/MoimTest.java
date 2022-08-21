package me.jsj.thejava.codemanipulation.bytecode.jacoco;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MoimTest {

    @Test
    void testIsFull() {
        Moim moim = new Moim();
        moim.setMaxNumberOfAttendees(100);
        moim.setNumberOfEnrollment(10);

        assertFalse(moim.isEnrollmentFull());
    }
}