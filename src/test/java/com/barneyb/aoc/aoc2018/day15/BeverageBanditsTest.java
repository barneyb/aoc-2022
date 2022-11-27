package com.barneyb.aoc.aoc2018.day15;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BeverageBanditsTest {

    public static final String EXAMPLE_ONE = "#######\n" +
            "#.G...#\n" +
            "#...EG#\n" +
            "#.#.#G#\n" +
            "#..G#E#\n" +
            "#.....#\n" +
            "#######";

    public static final String EXAMPLE_TWO = "#######\n" +
            "#G..#E#\n" +
            "#E#E.E#\n" +
            "#G.##.#\n" +
            "#...#E#\n" +
            "#...E.#\n" +
            "#######";

    public static final String EXAMPLE_THREE = "#######\n" +
            "#E..EG#\n" +
            "#.#G.E#\n" +
            "#E.##E#\n" +
            "#G..#.#\n" +
            "#..E#.#\n" +
            "#######";

    public static final String EXAMPLE_FOUR = "#######\n" +
            "#E.G#.#\n" +
            "#.#G..#\n" +
            "#G.#.G#\n" +
            "#G..#.#\n" +
            "#...E.#\n" +
            "#######";

    public static final String EXAMPLE_FIVE = "#######\n" +
            "#.E...#\n" +
            "#.#..G#\n" +
            "#.###.#\n" +
            "#E#G#G#\n" +
            "#...#G#\n" +
            "#######";

    public static final String EXAMPLE_SIX = "#########\n" +
            "#G......#\n" +
            "#.E.#...#\n" +
            "#..##..G#\n" +
            "#...##..#\n" +
            "#...#...#\n" +
            "#.G...G.#\n" +
            "#.....G.#\n" +
            "#########";

    @Test
    public void partOneExampleOne() {
        assertEquals(27730, BeverageBandits.partOne(EXAMPLE_ONE));
    }

    @Test
    public void partOneExampleTwo() {
        assertEquals(36334, BeverageBandits.partOne(EXAMPLE_TWO));
    }

    @Test
    public void partOneExampleThree() {
        assertEquals(39514, BeverageBandits.partOne(EXAMPLE_THREE));
    }

    @Test
    public void partOneExampleFour() {
        assertEquals(27755, BeverageBandits.partOne(EXAMPLE_FOUR));
    }

    @Test
    public void partOneExampleFive() {
        assertEquals(28944, BeverageBandits.partOne(EXAMPLE_FIVE));
    }

    @Test
    public void partOneExampleSix() {
        assertEquals(18740, BeverageBandits.partOne(EXAMPLE_SIX));
    }

    @Test
    public void partTwoExampleOne() {
        assertEquals(4988, BeverageBandits.partTwo(EXAMPLE_ONE));
    }

    // example two isn't used in part two

    @Test
    public void partTwoExampleThree() {
        assertEquals(31284, BeverageBandits.partTwo(EXAMPLE_THREE));
    }

    @Test
    public void partTwoExampleFour() {
        assertEquals(3478, BeverageBandits.partTwo(EXAMPLE_FOUR));
    }

    @Test
    public void partTwoExampleFive() {
        assertEquals(6474, BeverageBandits.partTwo(EXAMPLE_FIVE));
    }

    @Test
    public void partTwoExampleSix() {
        assertEquals(1140, BeverageBandits.partTwo(EXAMPLE_SIX));
    }

}
