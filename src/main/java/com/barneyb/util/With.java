package com.barneyb.util;

import java.util.function.Function;

public final class With {

    private With() {
        throw new UnsupportedOperationException("really?");
    }

    public static <T, R> R with(T obj, Function<T, R> work) {
        return work.apply(obj);
    }

    @SuppressWarnings("unused")
    public static <T, R> Timing.With<R> withMillis(T obj, Function<T, R> work) {
        return Timing.inMillis(() -> work.apply(obj));
    }

    @SuppressWarnings("unused")
    public static <T, R> Timing.With<R> withNanos(T obj, Function<T, R> work) {
        return Timing.inNanos(() -> work.apply(obj));
    }

}
