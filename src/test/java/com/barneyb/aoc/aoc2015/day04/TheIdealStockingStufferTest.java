package com.barneyb.aoc.aoc2015.day04;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TheIdealStockingStufferTest {

    private void number(int expected, String key) {
        assertEquals(expected, new TheIdealStockingStuffer(key).getFiveZeroNumber());
    }

    @Test
    void partOne_exampleOne() {
        number(609043, "abcdef");
    }

    @Test
    void partOne_exampleTwo() {
        number(1048970, "pqrstuv");
    }

}
