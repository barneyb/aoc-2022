package com.barneyb.aoc.aoc2015;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IWasToldThereWouldBeNoMathTest {

    @Test
    void partOne_exampleOne() {
        assertEquals(58, new IWasToldThereWouldBeNoMath("2x3x4").getPaperNeeded());
        assertEquals(58, new IWasToldThereWouldBeNoMath("2x4x3").getPaperNeeded());
        assertEquals(58, new IWasToldThereWouldBeNoMath("3x2x4").getPaperNeeded());
        assertEquals(58, new IWasToldThereWouldBeNoMath("3x4x2").getPaperNeeded());
        assertEquals(58, new IWasToldThereWouldBeNoMath("4x3x2").getPaperNeeded());
        assertEquals(58, new IWasToldThereWouldBeNoMath("4x2x3").getPaperNeeded());
    }

    @Test
    void partOne_exampleTwo() {
        assertEquals(43, new IWasToldThereWouldBeNoMath("1x1x10").getPaperNeeded());
        assertEquals(43, new IWasToldThereWouldBeNoMath("1x10x1").getPaperNeeded());
        assertEquals(43, new IWasToldThereWouldBeNoMath("10x1x1").getPaperNeeded());
    }

    @Test
    void partOne() {
        assertEquals(58 + 43, new IWasToldThereWouldBeNoMath("2x3x4\n1x1x10").getPaperNeeded());
        assertEquals(250, new IWasToldThereWouldBeNoMath("1x10x10").getPaperNeeded());
        assertEquals(250, new IWasToldThereWouldBeNoMath("10x1x10").getPaperNeeded());
        assertEquals(250, new IWasToldThereWouldBeNoMath("10x10x1").getPaperNeeded());
    }

}
