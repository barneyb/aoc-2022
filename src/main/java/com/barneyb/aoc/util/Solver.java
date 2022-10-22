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
        try {
            val constructor = clazz.getConstructor(String.class);
            val start = System.currentTimeMillis();
            val solver = constructor.newInstance(Input.forProblem(clazz));
            System.out.printf("Part One   : %s%n", partOne.apply(solver));
            if (partTwo != null)
                System.out.printf("Part Two   : %s%n", partTwo.apply(solver));
            val elapsed = System.currentTimeMillis() - start;
            System.out.printf("Total Time : %,d ms", elapsed);
        } catch (Exception e) {
            throw e instanceof RuntimeException
                    ? (RuntimeException) e
                    : new RuntimeException(e);
        }
    }

}
