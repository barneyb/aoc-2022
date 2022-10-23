package com.barneyb.aoc.aoc2017.day15;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DuelingGeneratorsTest {

    @Test
    void masking() {
        assertEquals("1110001101001010", Integer.toString(245556042 & 0xFFFF, 2));
        assertEquals("1110001101001010", Integer.toString(1431495498 & 0xFFFF, 2));
    }

    @Test
    void partOne() {
        assertEquals(588, new DuelingGenerators(65, 8921).getPairsInFortyMillion());
    }

}
