package com.barneyb.aoc.util;

import com.barneyb.util.Timing;
import lombok.val;

import java.util.function.Function;
import java.util.regex.Pattern;

public final class Solver {

    private Solver() {
        throw new UnsupportedOperationException("really?");
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
                System.out.printf("%-10s : %s%n", "Parsed", parsed);
            } else {
                System.out.printf("%-10s : %s%n", "Part One", partOne.apply(parsed));
                if (partTwo != null)
                    System.out.printf("%-10s : %s%n", "Part Two", partTwo.apply(parsed));
            }
            return null;
        }).humanize();
        System.out.printf("%-10s : %s%n", "Total Time", result.toDurationString());
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
