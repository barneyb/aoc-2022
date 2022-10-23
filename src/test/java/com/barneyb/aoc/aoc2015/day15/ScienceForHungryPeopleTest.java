package com.barneyb.aoc.aoc2015.day15;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ScienceForHungryPeopleTest {

    private static final String EXAMPLE_ONE =
            "Butterscotch: capacity -1, durability -2, flavor 6, texture 3, calories 8\n" +
                    "Cinnamon: capacity 2, durability 3, flavor -2, texture -1, calories 3\n";

    @Test
    void exampleOne() {
        assertEquals(62842880, new ScienceForHungryPeople(EXAMPLE_ONE).getHighestTotalScore());
    }

}
