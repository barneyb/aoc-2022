package com.barneyb.aoc.util;

import com.barneyb.util.Vec2;
import lombok.val;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Vec2Test {

    @Test
    void readingOrderComparison() {
        var o = Vec2.origin();
        assertTrue(Vec2.READING_ORDER.compare(o, new Vec2(1, 0)) < 0);
        assertTrue(Vec2.READING_ORDER.compare(o, new Vec2(0, 1)) < 0);
        assertTrue(Vec2.READING_ORDER.compare(o, new Vec2(-1, 0)) > 0);
        assertTrue(Vec2.READING_ORDER.compare(o, new Vec2(0, -1)) > 0);
    }

    @Test
    void readingOrderSort() {
        val list = new ArrayList<Vec2>();
        list.add(new Vec2(0, 0));
        list.add(new Vec2(1, 1));
        list.add(new Vec2(1, -1));
        list.add(new Vec2(-1, 1));
        list.add(new Vec2(-1, -1));
        list.sort(Vec2.READING_ORDER);
        assertEquals(List.of(
                new Vec2(-1, -1),
                new Vec2(1, -1),
                new Vec2(0, 0),
                new Vec2(-1, 1),
                new Vec2(1, 1)
        ), list);
    }

}
