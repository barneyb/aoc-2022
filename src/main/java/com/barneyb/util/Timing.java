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
        val r = benchmark(iterations, () -> inMillis(work));
        printBenchmark(iterations, r, "ms");
        return r;
    }

    @SuppressWarnings("unused")
    public static <R> With<R> benchNanos(int iterations, Supplier<R> work) {
        val r = benchmark(iterations, () -> inNanos(work));
        printBenchmark(iterations, r, "ns");
        return r;
    }

    private static <R> void printBenchmark(int iterations, With<R> r, String unit) {
        System.err.printf("Benchmark(%,6d): %,9d %s%n", iterations, r.elapsed, unit);
    }

    private static <R> With<R> benchmark(int iterations, Supplier<With<R>> work) {
        if (iterations < 2) {
            throw new IllegalArgumentException("Benchmarking makes no sense with fewer than two iterations");
        }
        // warm up
        val result = work.get().result;
        for (int i = iterations / 100; i > 0; i--) {
            work.get();
        }
        // benchmark
        long total = 0;
        for (int i = iterations; i > 0; i--) {
            total += work.get().elapsed;
        }
        return new With<>(result, total / iterations);
    }

}
