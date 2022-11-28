package com.barneyb.aoc.aoc2018.day23;

import lombok.val;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ExperimentalEmergencyTeleportationTest {

    @Test
    public void exampleOne() {
        val eet = new ExperimentalEmergencyTeleportation("pos=<0,0,0>, r=4\n" +
                "pos=<1,0,0>, r=1\n" +
                "pos=<4,0,0>, r=3\n" +
                "pos=<0,2,0>, r=1\n" +
                "pos=<0,5,0>, r=3\n" +
                "pos=<0,0,3>, r=1\n" +
                "pos=<1,1,1>, r=1\n" +
                "pos=<1,1,2>, r=1\n" +
                "pos=<1,3,1>, r=1\n");
        assertEquals(7, eet.getPartOne());
    }

}
