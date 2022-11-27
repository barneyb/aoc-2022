package com.barneyb.aoc.util;

import lombok.val;

import java.util.function.Function;
import java.util.regex.Pattern;

public final class Solver {

    private Solver() {
        throw new UnsupportedOperationException("really?");
    }

    public static <T> void execute(Class<T> clazz,
                                   Function<T, ?> partOne
    ) {
        execute(clazz, partOne, null);
    }

    public static <T> void execute(Class<T> clazz,
                                   Function<T, ?> partOne,
                                   Function<T, ?> partTwo
    ) {
        execute(input -> {
            try {
                return clazz.getConstructor(input.getClass())
                        .newInstance(input);
            } catch (RuntimeException e) {
                throw e;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }, partOne, partTwo);
    }

    public static void execute(Function<String, ?> partOne) {
        execute(partOne, null);
    }

    public static void execute(Function<String, ?> partOne,
                               Function<String, ?> partTwo) {
        execute(input -> input, partOne, partTwo);
    }

    private static <T> void execute(Function<String, T> initialize,
                                    Function<T, ?> partOne,
                                    Function<T, ?> partTwo) {
        System.out.println(labelForClass(partOne.getClass()));
        val input = Input.forProblem(partOne.getClass());
        val start = System.currentTimeMillis();
        System.out.printf("Part One   : %s%n", initialize
                .andThen(partOne)
                .apply(input));
        if (partTwo != null)
            System.out.printf("Part Two   : %s%n", initialize
                    .andThen(partTwo)
                    .apply(input));
        val elapsed = System.currentTimeMillis() - start;
        System.out.printf("Total Time : %,d ms%n", elapsed);
    }

    private static String labelForClass(Class<?> cls) {
        var name = cls.getName();
        val m = Pattern.compile("com\\.barneyb\\.aoc\\.aoc(\\d{4})\\.day(\\d{2})\\.([a-zA-Z0-9_]+)(\\$.+)?").matcher(name);
        if (m.matches()) {
            return m.group(3) +
                    " - " +
                    m.group(1) +
                    " day " +
                    Integer.parseInt(m.group(2));
        }
        return name;
    }

}
