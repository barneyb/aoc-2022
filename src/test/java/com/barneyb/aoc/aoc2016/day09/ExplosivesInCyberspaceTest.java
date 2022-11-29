package com.barneyb.aoc.aoc2016.day09;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ExplosivesInCyberspaceTest {

    @Test
    void exampleOne() {
        assertEquals(6, ExplosivesInCyberspace.decompress("ADVENT"));
    }

    @Test
    void exampleTwo() {
        assertEquals(7, ExplosivesInCyberspace.decompress("A(1x5)BC"));
    }

    @Test
    void exampleThree() {
        assertEquals(9, ExplosivesInCyberspace.decompress("(3x3)XYZ"));
    }

    @Test
    void exampleFour() {
        assertEquals(11, ExplosivesInCyberspace.decompress("A(2x2)BCD(2x2)EFG"));
    }

    @Test
    void exampleFive() {
        assertEquals(6, ExplosivesInCyberspace.decompress("(6x1)(1x3)A"));
    }

    @Test
    void exampleSix() {
        assertEquals(18, ExplosivesInCyberspace.decompress("X(8x2)(3x3)ABCY"));
    }

    @Test
    void multiDigit() {
        assertEquals(134, ExplosivesInCyberspace.decompress("a(11x12)12345678901z"));
    }

    @Test
    void whitespace() {
        assertEquals(7, ExplosivesInCyberspace.decompress("A(1x5)BC\n"));
    }

}
