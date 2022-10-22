package com.barneyb.aoc.aoc2015.day01;

import com.barneyb.aoc.util.Solver;
import lombok.Getter;
import lombok.val;

/**
 * Solver for AoC 2015 day 1. API:
 *
 * <pre>
 * {@link #NotQuiteLisp(String)}
 * int {@link #getEndFloor()}
 * int {@link #getBasementPosition()}
 * </pre>
 */
public class NotQuiteLisp {

    @Getter
    private final int endFloor;

    @Getter
    private final int basementPosition;

    public NotQuiteLisp(String input) {
        input = input.trim();
        var f = 0;
        var basement = -1;
        for (var i = 0; i < input.length(); i++) {
            val c = input.charAt(i);
            switch (c) {
                case '(':
                    f += 1;
                    break;
                case ')':
                    f -= 1;
                    break;
                default:
                    throw new IllegalArgumentException(String.format("Unrecognized character %d ('%c') in input at index %d", (int) c, c, i));
            }
            if (f == -1 && basement < 0) {
                basement = i;
            }
        }
        endFloor = f;
        basementPosition = basement + 1; // one-indexed positions
    }

    public static void main(String[] args) {
        Solver.execute(
                NotQuiteLisp.class,
                NotQuiteLisp::getEndFloor,
                NotQuiteLisp::getBasementPosition);
    }

}
