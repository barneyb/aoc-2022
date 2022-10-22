package com.barneyb.aoc.aoc2015;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IWasToldThereWouldBeNoMathTest {

    private void paper(int expected, String input) {
        assertEquals(expected, new IWasToldThereWouldBeNoMath(input).getPaperNeeded());
    }

    @Test
    void partOne_exampleOne() {
        paper(58, "2x3x4");
        paper(58, "2x4x3");
        paper(58, "3x2x4");
        paper(58, "3x4x2");
        paper(58, "4x3x2");
        paper(58, "4x2x3");
    }

    @Test
    void partOne_exampleTwo() {
        paper(43, "1x1x10");
        paper(43, "1x10x1");
        paper(43, "10x1x1");
    }

    @Test
    void partOne() {
        paper(58 + 43, "2x3x4\n1x1x10");
        paper(250, "1x10x10");
        paper(250, "10x1x10");
        paper(250, "10x10x1");
    }

    private void ribbon(int expected, String input) {
        assertEquals(expected, new IWasToldThereWouldBeNoMath(input).getRibbonNeeded());
    }

    @Test
    void partTwo_exampleOne() {
        ribbon(34, "2x3x4");
        ribbon(34, "2x4x3");
        ribbon(34, "3x2x4");
        ribbon(34, "3x4x2");
        ribbon(34, "4x3x2");
        ribbon(34, "4x2x3");
    }

    @Test
    void partTwo_exampleTwo() {
        ribbon(14, "1x1x10");
        ribbon(14, "1x10x1");
        ribbon(14, "10x1x1");
    }

    @Test
    void partTwo() {
        ribbon(34 + 14, "2x3x4\n1x1x10");
        ribbon(122, "1x10x10");
        ribbon(122, "10x1x10");
        ribbon(122, "10x10x1");
    }

}
