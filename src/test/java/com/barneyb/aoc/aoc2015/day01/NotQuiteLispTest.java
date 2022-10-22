package com.barneyb.aoc.aoc2015.day01;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NotQuiteLispTest {

    @Test
    void partOneExampleOne() {
        assertEquals(0, new NotQuiteLisp("(())").getEndFloor());
        assertEquals(0, new NotQuiteLisp("()()").getEndFloor());
    }

    @Test
    void partOneExampleTwo() {
        assertEquals(3, new NotQuiteLisp("(((").getEndFloor());
        assertEquals(3, new NotQuiteLisp("(()(()(").getEndFloor());
    }

    @Test
    void partOneExampleThree() {
        assertEquals(3, new NotQuiteLisp("))(((((").getEndFloor());
    }

    @Test
    void partOneExampleFour() {
        assertEquals(-1, new NotQuiteLisp("())").getEndFloor());
        assertEquals(-1, new NotQuiteLisp("))(").getEndFloor());
    }

    @Test
    void partOneExampleFive() {
        assertEquals(-3, new NotQuiteLisp(")))").getEndFloor());
        assertEquals(-3, new NotQuiteLisp(")())())").getEndFloor());
    }

    @Test
    void partTwoExampleOne() {
        assertEquals(1, new NotQuiteLisp(")").getBasementPosition());
    }

    @Test
    void partTwoExampleTwo() {
        assertEquals(5, new NotQuiteLisp("()())").getBasementPosition());
    }

}
