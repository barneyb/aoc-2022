package com.barneyb.aoc.aoc2015;

import com.barneyb.aoc.util.Input;

/**
 * Solver for AoC 2015 day 1. API:
 *
 * <pre>
 * {@link #NotQuiteLisp(String)}
 * int {@link #endFloor()}
 * int {@link #basementPosition()}
 * </pre>
 */
public class NotQuiteLisp {

    private final int endFloor;
    private final int basementPosition;

    NotQuiteLisp(String input) {
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
        basementPosition = basement + 1; // one-indexed
    }

    int endFloor() {
        return endFloor;
    }

    int basementPosition() {
        return basementPosition;
    }

    public static void main(String[] args) {
        var solver = new NotQuiteLisp(Input.forProblem(NotQuiteLisp.class));
        System.out.println(solver.endFloor());
        System.out.println(solver.basementPosition());
    }

}
