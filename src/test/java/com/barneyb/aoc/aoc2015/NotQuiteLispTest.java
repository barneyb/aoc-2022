package com.barneyb.aoc.aoc2015;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NotQuiteLispTest {

    @Test
    void partOneExampleOne() {
        assertEquals(0, new NotQuiteLisp("(())").endFloor());
        assertEquals(0, new NotQuiteLisp("()()").endFloor());
    }

    @Test
    void partOneExampleTwo() {
        assertEquals(3, new NotQuiteLisp("(((").endFloor());
        assertEquals(3, new NotQuiteLisp("(()(()(").endFloor());
    }

    @Test
    void partOneExampleThree() {
        assertEquals(3, new NotQuiteLisp("))(((((").endFloor());
    }

    @Test
    void partOneExampleFour() {
        assertEquals(-1, new NotQuiteLisp("())").endFloor());
        assertEquals(-1, new NotQuiteLisp("))(").endFloor());
    }

    @Test
    void partOneExampleFive() {
        assertEquals(-3, new NotQuiteLisp(")))").endFloor());
        assertEquals(-3, new NotQuiteLisp(")())())").endFloor());
    }

    @Test
    void partTwoExampleOne() {
        assertEquals(1, new NotQuiteLisp(")").basementPosition());
    }

    @Test
    void partTwoExampleTwo() {
        assertEquals(5, new NotQuiteLisp("()())").basementPosition());
    }

}
