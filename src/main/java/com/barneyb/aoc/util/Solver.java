package com.barneyb.aoc.util;

import java.util.function.Function;

public final class Solver {

    private Solver() {
        throw new UnsupportedOperationException("really?");
    }

    public static <T> void execute(Class<T> clazz,
                                   Function<T, ?> partOne,
                                   Function<T, ?> partTwo
    ) {
        try {
            var constructor = clazz.getConstructor(String.class);
            var start = System.currentTimeMillis();
            T solver = constructor.newInstance(Input.forProblem(clazz));
            System.out.printf("Part One   : %s%n", partOne.apply(solver));
            System.out.printf("Part Two   : %s%n", partTwo.apply(solver));
            var elapsed = System.currentTimeMillis() - start;
            System.out.printf("Total Time : %,d ms", elapsed);
        } catch (Exception e) {
            throw e instanceof RuntimeException
                    ? (RuntimeException) e
                    : new RuntimeException(e);
        }
    }

}
