package com.barneyb.aoc.util;

import lombok.Value;

@Value
public class Pair<F, S> {

    public static <F, S> Pair<F, S> of(F first, S second) {
        return new Pair<>(first, second);
    }

    F first;
    S second;
}
