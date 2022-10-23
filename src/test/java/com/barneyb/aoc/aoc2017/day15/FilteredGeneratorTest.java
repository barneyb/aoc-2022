package com.barneyb.aoc.aoc2017.day15;

import lombok.val;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FilteredGeneratorTest {

    @Test
    void exampleTwo() {
        val g = new FilteredGenerator(new SequenceGenerator(65, 16807), 4);
        assertEquals(1352636452, g.next());
        assertEquals(1992081072, g.next());
        assertEquals(530830436, g.next());
        assertEquals(1980017072, g.next());
        assertEquals(740335192, g.next());
    }

}
