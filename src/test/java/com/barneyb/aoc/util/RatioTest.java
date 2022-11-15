package com.barneyb.aoc.util;

import com.barneyb.util.Ratio;
import lombok.val;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RatioTest {

    @Test
    void mixed() {
        val r = Ratio.mixed(2, 5, 4);
        assertEquals(new Ratio(13, 4), r);
        assertEquals(3, r.getInteger());
        assertEquals(1, r.getProperNumerator());
    }

    @Test
    void reduce() {
        assertEquals(new Ratio(1, 2),
                new Ratio(1, 2).reduce());
        assertEquals(new Ratio(1, 2),
                new Ratio(3, 6).reduce());
        assertEquals(new Ratio(1, 2),
                new Ratio(7, 14).reduce());
    }

    @Test
    void equivalence() {
        assertTrue(new Ratio(1, 2).equivalentTo(new Ratio(2, 4)));
        assertTrue(new Ratio(8, 2).equivalentTo(new Ratio(4, 1)));
    }

    @Test
    void sum() {
        assertEquals(new Ratio(1, 2),
                new Ratio(1, 2).sum(new Ratio(0, 2)));
        assertEquals(new Ratio(1, 2),
                new Ratio(1, 2).sum(new Ratio(0, 3)));
        assertEquals(new Ratio(1, 2),
                new Ratio(1, 6).sum(new Ratio(1, 3)));
        assertEquals(new Ratio(53, 60),
                new Ratio(7, 12).sum(new Ratio(3, 10)));
    }

}
