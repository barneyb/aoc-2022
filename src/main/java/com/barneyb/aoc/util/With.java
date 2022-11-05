package com.barneyb.aoc.util;

import java.util.function.Function;

import static com.barneyb.aoc.util.Timing.inMillis;
import static com.barneyb.aoc.util.Timing.inNanos;

public final class With {

    private With() {
        throw new UnsupportedOperationException("really?");
    }

    public static <T, R> R with(T obj, Function<T, R> work) {
        return work.apply(obj);
    }

    public static <T, R> Pair<R, Long> withMillis(T obj, Function<T, R> work) {
        return inMillis(() -> work.apply(obj));
    }

    public static <T, R> Pair<R, Long> withNanos(T obj, Function<T, R> work) {
        return inNanos(() -> work.apply(obj));
    }

}
