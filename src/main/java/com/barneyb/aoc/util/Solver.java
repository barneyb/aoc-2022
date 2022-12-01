package com.barneyb.aoc.util;

import lombok.val;

import java.util.function.Function;
import java.util.regex.Pattern;

public final class Solver {

    private Solver() {
        throw new UnsupportedOperationException("really?");
    }

    public static <T> void execute(Function<String, T> init,
                                   Function<T, ?> partOne) {
        execute(init, partOne, null);
    }

    public static <T> void execute(Function<String, T> init,
                                   Function<T, ?> partOne,
                                   Function<T, ?> partTwo) {
        System.out.println(labelForClass(partOne.getClass()));
        val input = Input.forProblem(partOne.getClass());
        val start = System.currentTimeMillis();
        val solver = init.apply(input);
        System.out.printf("Part One   : %s%n", partOne.apply(solver));
        if (partTwo != null)
            System.out.printf("Part Two   : %s%n", partTwo.apply(solver));
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
