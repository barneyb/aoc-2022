package com.barneyb.aoc.aoc2018.day23;

import lombok.val;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WizardlyFortranMagicTest {

    @Test
    public void exampleOne() {
        val wfm = new WizardlyFortranMagic(ExperimentalEmergencyTeleportationTest.INPUT_ONE);
        assertEquals(7, wfm.getPartOne());
    }

    @Test
    public void exampleTwo() {
        val wfm = new WizardlyFortranMagic(ExperimentalEmergencyTeleportationTest.INPUT_TWO);
        assertEquals(36, wfm.getPartTwo());
    }

}
