package com.barneyb.aoc.aoc2015.day03;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PerfectlySphericalHousesInAVacuumTest {

    private void houseCount(int expected, String input) {
        assertEquals(expected, new PerfectlySphericalHousesInAVacuum(input).getUniqueHousesVisited());
    }

    @Test
    void partOne_exampleOne() {
        houseCount(2, ">");
    }

    @Test
    void partOne_exampleTwo() {
        houseCount(4, "^>v<");
    }

    @Test
    void partOne_exampleThree() {
        houseCount(2, "^v^v^v^v^v");
    }

}
