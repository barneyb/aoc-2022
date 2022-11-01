package com.barneyb.aoc.aoc2019.day10;

import com.barneyb.aoc.util.Vec2;
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
        var station = new MonitoringStation(EXAMPLE_SIX);
        assertEquals(210, station.getMaxDetectedAsteroids());
        assertEquals(new Vec2(11, 12), station.getNthVaporized(1));
        assertEquals(new Vec2(12, 8), station.getNthVaporized(10));
        assertEquals(new Vec2(8, 2), station.getNthVaporized(200));
    }

}
