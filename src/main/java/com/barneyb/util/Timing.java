package com.barneyb.util;

import lombok.Value;
import lombok.val;

import java.util.function.Supplier;

public class Timing {

    @Value
    public static class With<R> {
        R result;
        long elapsed;
    }

    public static <R> With<R> inMillis(Supplier<R> work) {
        var start = System.currentTimeMillis();
        var result = work.get();
        var end = System.currentTimeMillis();
        return new With<>(result, end - start);
    }

    public static <R> With<R> inNanos(Supplier<R> work) {
        var start = System.nanoTime();
        var result = work.get();
        var end = System.nanoTime();
        return new With<>(result, end - start);
    }

    @SuppressWarnings("unused")
    public static <R> With<R> benchMillis(int iterations, Supplier<R> work) {
        return benchmark(iterations, () -> inMillis(work));
    }

    @SuppressWarnings("unused")
    public static <R> With<R> benchNanos(int iterations, Supplier<R> work) {
        return benchmark(iterations, () -> inNanos(work));
    }

    private static <R> With<R> benchmark(int iterations, Supplier<With<R>> work) {
        if (iterations < 2) {
            throw new IllegalArgumentException("Benchmarking makes no sense with fewer than two iterations");
        }
        // warm up
        for (int i = Math.max(1, iterations / 100); i > 0; i--) {
            work.get();
        }
        // benchmark
        long total = 0;
        R result = null;
        for (int i = iterations; i > 0; i--) {
            val r = work.get();
            result = r.result;
            total += r.elapsed;
        }
        return new With<>(result, total / iterations);
    }

}
