package com.barneyb.aoc.aoc2015;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NotQuiteLispTest {

    @Test
    void exampleOne() {
        assertEquals(0, new NotQuiteLisp("(())").endFloor());
        assertEquals(0, new NotQuiteLisp("()()").endFloor());
    }

    @Test
    void exampleTwo() {
        assertEquals(3, new NotQuiteLisp("(((").endFloor());
        assertEquals(3, new NotQuiteLisp("(()(()(").endFloor());
    }

    @Test
    void exampleThree() {
        assertEquals(3, new NotQuiteLisp("))(((((").endFloor());
    }

    @Test
    void exampleFour() {
        assertEquals(-1, new NotQuiteLisp("())").endFloor());
        assertEquals(-1, new NotQuiteLisp("))(").endFloor());
    }

    @Test
    void exampleFive() {
        assertEquals(-3, new NotQuiteLisp(")))").endFloor());
        assertEquals(-3, new NotQuiteLisp(")())())").endFloor());
    }

}
