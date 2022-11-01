package com.barneyb.aoc.util;

import lombok.val;

import java.util.function.Function;

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
            } catch (Exception e) {
                throw e instanceof RuntimeException
                        ? (RuntimeException) e
                        : new RuntimeException(e);
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
        val input = Input.forProblem(partOne.getClass());
        val solver = initialize.apply(input);
        val start = System.currentTimeMillis();
        System.out.printf("Part One   : %s%n", partOne.apply(solver));
        if (partTwo != null)
            System.out.printf("Part Two   : %s%n", partTwo.apply(solver));
        val elapsed = System.currentTimeMillis() - start;
        System.out.printf("Total Time : %,d ms", elapsed);
    }

}
