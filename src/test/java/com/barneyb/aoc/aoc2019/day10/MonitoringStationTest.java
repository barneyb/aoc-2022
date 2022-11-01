package com.barneyb.aoc.aoc2019.day10;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MonitoringStationTest {

    public static final String EXAMPLE_ONE = ".#..#\n" +
            ".....\n" +
            "#####\n" +
            "....#\n" +
            "...##";

    public static final String EXAMPLE_SIX = ".#..##.###...#######\n" +
            "##.############..##.\n" +
            ".#.######.########.#\n" +
            ".###.#######.####.#.\n" +
            "#####.##.#.##.###.##\n" +
            "..#####..#.#########\n" +
            "####################\n" +
            "#.####....###.#.#.##\n" +
            "##.#################\n" +
            "#####.##.###..####..\n" +
            "..######..##.#######\n" +
            "####.##.####...##..#\n" +
            ".#####..#.######.###\n" +
            "##...#.##########...\n" +
            "#.##########.#######\n" +
            ".####.#.###.###.#.##\n" +
            "....##.##.###..#####\n" +
            ".#.#.###########.###\n" +
            "#.#.#.#####.####.###\n" +
            "###.##.####.##.#..##";

    @Test
    void exampleOne() {
        assertEquals(8, new MonitoringStation(EXAMPLE_ONE)
                .getMaxDetectedAsteroids());
    }

    @Test
    void exampleSix() {
        assertEquals(210, new MonitoringStation(EXAMPLE_SIX)
                .getMaxDetectedAsteroids());
    }

}
