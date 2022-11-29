package com.barneyb.aoc.aoc2016.day09;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ExplosivesInCyberspaceTest {

    @Test
    void exampleOne() {
        assertEquals(6, ExplosivesInCyberspace.len("ADVENT"));
    }

    @Test
    void exampleTwo() {
        assertEquals(7, ExplosivesInCyberspace.len("A(1x5)BC"));
    }

    @Test
    void exampleThree() {
        assertEquals(9, ExplosivesInCyberspace.len("(3x3)XYZ"));
    }

    @Test
    void exampleFour() {
        assertEquals(11, ExplosivesInCyberspace.len("A(2x2)BCD(2x2)EFG"));
    }

    @Test
    void exampleFive() {
        assertEquals(6, ExplosivesInCyberspace.len("(6x1)(1x3)A"));
    }

    @Test
    void exampleSix() {
        assertEquals(18, ExplosivesInCyberspace.len("X(8x2)(3x3)ABCY"));
    }

    @Test
    void multiDigit() {
        assertEquals(134, ExplosivesInCyberspace.len("a(11x12)12345678901z"));
    }

    @Test
    void whitespace() {
        assertEquals(7, ExplosivesInCyberspace.len("A(1x5)BC\n"));
    }

    @Test
    void partTwoExampleOne() {
        assertEquals(9, ExplosivesInCyberspace.len2("(3x3)XYZ"));
    }

    @Test
    void partTwoExampleTwo() {
        assertEquals(20, ExplosivesInCyberspace.len2("X(8x2)(3x3)ABCY"));
    }

    @Test
    void partTwoExampleThree() {
        assertEquals(241920, ExplosivesInCyberspace.len2("(27x12)(20x12)(13x14)(7x10)(1x12)A"));
    }

    @Test
    void partTwoExampleFour() {
        assertEquals(445, ExplosivesInCyberspace.len2("(25x3)(3x3)ABC(2x3)XY(5x2)PQRSTX(18x9)(3x2)TWO(5x7)SEVEN"));
    }

}
