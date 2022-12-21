package com.barneyb.aoc.util;

import com.barneyb.util.Timing;
import lombok.val;

import java.util.function.Function;
import java.util.regex.Pattern;

public final class Solver {

    private Solver() {
        throw new UnsupportedOperationException("really?");
    }

    @SuppressWarnings("unused")
    public static <T> void benchmark(Function<String, T> init,
                                     Function<T, ?> partOne,
                                     Function<T, ?> partTwo) {
        benchmark(100, init, 1000, partOne, 1000, partTwo);
    }

    @SuppressWarnings("unused")
    public static <T> void benchmark(int iterations,
                                     Function<String, T> init,
                                     Function<T, ?> partOne,
                                     Function<T, ?> partTwo) {
        benchmark(iterations, init, iterations, partOne, iterations, partTwo);
    }

    public static <T> void benchmark(int initIterations, Function<String, T> init,
                                     int oneIterations, Function<T, ?> partOne,
                                     int twoIterations, Function<T, ?> partTwo) {
        val result = Timing.timed(() -> {
            val input = Input.forProblem(init.getClass());
            val parsed = Timing.benchmark(initIterations, () ->
                    init.apply(input)).getResult();
            Timing.benchmark(oneIterations, () ->
                    partOne.apply(parsed));
            Timing.benchmark(twoIterations, () ->
                    partTwo.apply(parsed));
            execute(init, partOne, partTwo);
            return null;
        }).humanize();
        System.err.printf("%-10s : %s%n", "Overall", result.toDurationString());
    }

    public static <T> void execute(Function<String, T> init) {
        execute(init, null, null);
    }

    public static <T> void execute(Function<String, T> init,
                                   Function<T, ?> partOne) {
        execute(init, partOne, null);
    }

    public static <T> void execute(Function<String, T> init,
                                   Function<T, ?> partOne,
                                   Function<T, ?> partTwo) {
        val func = partOne == null ? init : partOne;
        System.out.println(labelForClass(func.getClass()));
        val input = Input.forProblem(func.getClass());
        val result = Timing.timed(() -> {
            val parsed = init.apply(input);
            if (partOne == null) {
                printResult("Parsed", parsed);
            } else {
                printResult("Part One", partOne.apply(parsed));
                if (partTwo != null)
                    printResult("Part Two", partTwo.apply(parsed));
            }
            return null;
        }).humanize();
        printResult("Total Time", result.toDurationString());
    }

    private static void printResult(String label, Object v) {
        if ((v instanceof Integer || v instanceof Long) && Math.abs(((Number) v).longValue()) > 1000)
            //noinspection MalformedFormatString
            System.out.printf("%-10s : %s (%,d)%n", label, v, v);
        else
            System.out.printf("%-10s : %s%n", label, v);
    }

    private static String labelForClass(Class<?> cls) {
        var name = cls.getName();
        val m = Pattern.compile("com\\.barneyb\\.aoc\\.aoc(\\d{4})\\.day(\\d{2})\\.([a-zA-Z0-9_]+)(\\$.+)?").matcher(name);
        if (m.matches()) {
            return CharSequenceKt.camelToTitle(m.group(3)) +
                    " - " +
                    m.group(1) +
                    " day " +
                    Integer.parseInt(m.group(2));
        }
        return name;
    }

}
