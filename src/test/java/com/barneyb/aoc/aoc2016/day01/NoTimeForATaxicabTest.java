package com.barneyb.aoc.aoc2016.day01;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class NoTimeForATaxicabTest {

    @Test
    void examplesOne() {
        assertAll(
                () -> assertEquals(5, new NoTimeForATaxicab("R2, L3").partOne()),
                () -> assertEquals(2, new NoTimeForATaxicab("R2, R2, R2").partOne()),
                () -> assertEquals(12, new NoTimeForATaxicab("R5, L5, R5, R3").partOne())
        );
    }

    @Test
    void exampleTwo() {
        assertEquals(4, new NoTimeForATaxicab("R8, R4, R4, R8").partTwo());
    }
}
