package com.barneyb.util;

import lombok.Value;

@Value
public class IntPair {
    int first;
    int second;

    public static IntPair zero() {
        return new IntPair(0, 0);
    }

    public IntPair sum(IntPair other) {
        return new IntPair(
                first + other.first,
                second + other.second);
    }
}
