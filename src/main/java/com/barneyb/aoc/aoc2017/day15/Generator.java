package com.barneyb.aoc.aoc2017.day15;

import lombok.val;

public class Generator {

    private long previous;
    private final long factor;

    Generator(int seed, int factor) {
        this.previous = seed;
        this.factor = factor;
    }

    int next() {
        val next = previous * factor % Integer.MAX_VALUE;
        previous = next;
        return (int) next;
    }

}
