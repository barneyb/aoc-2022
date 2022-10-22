package com.barneyb.aoc.util;

import lombok.Data;

@Data
public class IntPair {
    final int first;
    final int second;

    public static IntPair zero() {
        return new IntPair(0, 0);
    }

    public IntPair sum(IntPair other) {
        return new IntPair(
                first + other.first,
                second + other.second);
    }
}
