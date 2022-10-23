package com.barneyb.aoc.aoc2017.day15;

import lombok.val;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SequenceGeneratorTest {

    @Test
    void exampleOne() {
        val g = new SequenceGenerator(65, 16807);
        assertEquals(1092455, g.next());
        assertEquals(1181022009, g.next());
        assertEquals(245556042, g.next());
        assertEquals(1744312007, g.next());
        assertEquals(1352636452, g.next());
    }

}
