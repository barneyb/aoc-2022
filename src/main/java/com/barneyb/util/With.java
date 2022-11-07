package com.barneyb.util;

import java.util.function.Function;

public final class With {

    private With() {
        throw new UnsupportedOperationException("really?");
    }

    public static <T, R> R with(T obj, Function<T, R> work) {
        return work.apply(obj);
    }

    public static <T, R> Pair<R, Long> withMillis(T obj, Function<T, R> work) {
        return Timing.inMillis(() -> work.apply(obj));
    }

    public static <T, R> Pair<R, Long> withNanos(T obj, Function<T, R> work) {
        return Timing.inNanos(() -> work.apply(obj));
    }

}
