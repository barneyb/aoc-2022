package com.barneyb.util;

import lombok.Value;
import lombok.val;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

public class Timing {

    @Value
    public static class With<R> {
        R result;
        long elapsed;
        TimeUnit unit;

        public With<R> humanize() {
            for (val u : TimeUnit.values()) {
                val e = u.convert(elapsed, unit);
                if (e < 10_000) {
                    return new With<>(result, e, u);
                }
            }
            return this;
        }

        public String toDurationString() {
            return toDurationString(1);
        }

        public String toDurationString(int width) {
            return String.format("%," + width + "d %s", elapsed, labelForUnit());
        }

        private String labelForUnit() {
            switch (unit) {
                case NANOSECONDS:
                    return "ns";
                case MICROSECONDS:
                    return "μs";
                case MILLISECONDS:
                    return "ms";
                case SECONDS:
                    return "sec";
                case MINUTES:
                    return "min";
                case HOURS:
                    return "hrs";
                case DAYS:
                    return "days";
                default:
                    throw new IllegalArgumentException("Unknown '" + unit + "' TimeUnit");
            }
        }
    }

    /**
     * Time the supplied work with nanosecond precision.
     */
    public static <R> With<R> timed(Supplier<R> work) {
        return timed(work, TimeUnit.NANOSECONDS);
    }

    public static <R> With<R> timed(Supplier<R> work, TimeUnit unit) {
        val start = System.nanoTime();
        val result = work.get();
        val elapsed = System.nanoTime() - start;
        return new With<>(
                result,
                unit == TimeUnit.NANOSECONDS
                        ? elapsed
                        : unit.convert(elapsed, TimeUnit.NANOSECONDS),
                unit);
    }

    private static int ITERATIONS_WIDTH = 6;

    public static <R> With<R> benchmark(int iterations, Supplier<R> work) {
        if (iterations < 1) {
            throw new IllegalArgumentException("Benchmarking requires at least one iteration");
        }
        // warm up, at least one iteration, up to 1% of the benchmark's
        for (int i = iterations / 100; i >= 0; i--) work.get();
        // benchmark
        var r = timed(() -> {
            // the last iteration will be for its result
            for (int i = iterations; i > 1; i--) work.get();
            return work.get();
        });
        r = new With<R>(r.result, r.elapsed / iterations, r.unit)
                .humanize();
        val itr = String.format("%,d", iterations);
        if (itr.length() > ITERATIONS_WIDTH) {
            ITERATIONS_WIDTH = itr.length();
        }
        System.err.printf("Benchmark(%" + ITERATIONS_WIDTH + "s): %s%n", itr, r.toDurationString(6));
        return r;
    }

}
