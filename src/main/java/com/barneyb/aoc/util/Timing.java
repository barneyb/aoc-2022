package com.barneyb.aoc.util;

import lombok.val;

import java.util.function.Supplier;

public class Timing {

    public static <R> Pair<R, Long> inMillis(Supplier<R> work) {
        var start = System.currentTimeMillis();
        var result = work.get();
        var end = System.currentTimeMillis();
        return new Pair<>(result, end - start);
    }

    public static <R> Pair<R, Long> inNanos(Supplier<R> work) {
        var start = System.nanoTime();
        var result = work.get();
        var end = System.nanoTime();
        return new Pair<>(result, end - start);
    }

    public static <R> Pair<R, Long> benchMillis(int iterations, Supplier<R> work) {
        return benchmark(iterations, () -> inMillis(work));
    }

    public static <R> Pair<R, Long> benchNanos(int iterations, Supplier<R> work) {
        return benchmark(iterations, () -> inNanos(work));
    }

    private static <R> Pair<R, Long> benchmark(int iterations, Supplier<Pair<R, Long>> work) {
        if (iterations < 2) {
            throw new IllegalArgumentException("Benchmarking makes no sense with fewer than two iterations");
        }
        long total = 0;
        R result = null;
        for (int i = 0; i < iterations; i++) {
            val r = work.get();
            result = r.getFirst();
            total += r.getSecond();
        }
        return new Pair<>(result, total / iterations);
    }

}
