package com.barneyb.aoc.aoc2017.day15;

import lombok.val;

class FilteredGenerator implements Generator {

    private final Generator generator;
    private final int modulus;

    FilteredGenerator(Generator generator, int modulus) {
        this.generator = generator;
        this.modulus = modulus;
    }

    public int next() {
        while (true) {
            val n = generator.next();
            if (n % modulus == 0) {
                return n;
            }
        }
    }

}
