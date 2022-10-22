package com.barneyb.aoc.aoc2015;

import com.barneyb.aoc.util.Solver;

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

    private final int endFloor;
    private final int basementPosition;

    public NotQuiteLisp(String input) {
        input = input.trim();
        int f = 0;
        int basement = -1;
        for (int i = 0; i < input.length(); i++) {
            var c = input.charAt(i);
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

    public int getEndFloor() {
        return endFloor;
    }

    public int getBasementPosition() {
        return basementPosition;
    }

    public static void main(String[] args) {
        Solver.execute(
                NotQuiteLisp.class,
                NotQuiteLisp::getEndFloor,
                NotQuiteLisp::getBasementPosition);
    }

}
