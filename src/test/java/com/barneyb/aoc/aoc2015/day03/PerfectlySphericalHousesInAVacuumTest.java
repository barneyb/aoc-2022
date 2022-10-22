package com.barneyb.aoc.aoc2015.day03;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PerfectlySphericalHousesInAVacuumTest {

    private void houseCountOne(int expected, String input) {
        assertEquals(expected, new PerfectlySphericalHousesInAVacuum(input).getSoloUniqueHousesVisited());
    }

    @Test
    void partOne_exampleOne() {
        houseCountOne(2, ">");
    }

    @Test
    void partOne_exampleTwo() {
        houseCountOne(4, "^>v<");
    }

    @Test
    void partOne_exampleThree() {
        houseCountOne(2, "^v^v^v^v^v");
    }

    private void houseCountTwo(int expected, String input) {
        assertEquals(expected, new PerfectlySphericalHousesInAVacuum(input).getPairedUniqueHousesVisited());
    }

    @Test
    void partTwo_exampleOne() {
        houseCountTwo(3, "^v");
    }

    @Test
    void partTwo_exampleTwo() {
        houseCountTwo(3, "^>v<");
    }

    @Test
    void partTwo_exampleThree() {
        houseCountTwo(11, "^v^v^v^v^v");
    }

}
