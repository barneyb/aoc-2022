package com.barneyb.aoc.aoc2018.day23;

import lombok.val;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ExperimentalEmergencyTeleportationTest {

    public static final String INPUT_ONE = "pos=<0,0,0>, r=4\n" +
            "pos=<1,0,0>, r=1\n" +
            "pos=<4,0,0>, r=3\n" +
            "pos=<0,2,0>, r=1\n" +
            "pos=<0,5,0>, r=3\n" +
            "pos=<0,0,3>, r=1\n" +
            "pos=<1,1,1>, r=1\n" +
            "pos=<1,1,2>, r=1\n" +
            "pos=<1,3,1>, r=1\n";

    public static final String INPUT_TWO = "pos=<10,12,12>, r=2\n" +
            "pos=<12,14,12>, r=2\n" +
            "pos=<16,12,12>, r=4\n" +
            "pos=<14,14,14>, r=6\n" +
            "pos=<50,50,50>, r=200\n" +
            "pos=<10,10,10>, r=5\n";

    @Test
    public void exampleOne() {
        val eet = new ExperimentalEmergencyTeleportation(INPUT_ONE);
        assertEquals(7, eet.getPartOne());
    }

}
