package com.barneyb.aoc.util;

import lombok.val;

public final class Parse {

    private Parse() {
        throw new UnsupportedOperationException("really?");
    }

    public static int[] ints(String[] ints) {
        val result = new int[ints.length];
        for (var i = 0; i < ints.length; i++) {
            result[i] = Integer.parseInt(ints[i]);
        }
        return result;
    }
}
