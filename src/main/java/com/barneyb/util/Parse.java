package com.barneyb.util;

import lombok.val;

import java.util.function.Function;
import java.util.stream.Stream;

public final class Parse {

    private Parse() {
        throw new UnsupportedOperationException("really?");
    }

    public static <T> Stream<T> lines(String input, Function<String, T> parse) {
        return input.trim().lines().map(parse);
    }

    public static int[] ints(String[] ints) {
        val result = new int[ints.length];
        for (var i = 0; i < ints.length; i++) {
            result[i] = Integer.parseInt(ints[i]);
        }
        return result;
    }
}
