package com.barneyb.aoc.aoc2015;

import com.barneyb.aoc.util.Input;

/**
 * Solver for AoC 2015 day 1. API:
 *
 * <pre>
 * NotQuiteLisp(String input)
 * int endFloor()
 * </pre>
 */
public class NotQuiteLisp {

    private final int endFloor;

    NotQuiteLisp(String input) {
        input = input.trim();
        int f = 0;
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
        }
        endFloor = f;
    }

    int endFloor() {
        return endFloor;
    }

    public static void main(String[] args) {
        var solver = new NotQuiteLisp(Input.forProblem(NotQuiteLisp.class));
        System.out.println(solver.endFloor());
    }

}
