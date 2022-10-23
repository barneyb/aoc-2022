package com.barneyb.aoc.aoc2017.day15;

class SequenceGenerator implements Generator {

    private int curr;
    private final long factor;

    SequenceGenerator(int seed, int factor) {
        this.curr = seed;
        this.factor = factor;
    }

    public int next() {
        curr = (int) (curr * factor % Integer.MAX_VALUE);
        return curr;
    }

}
