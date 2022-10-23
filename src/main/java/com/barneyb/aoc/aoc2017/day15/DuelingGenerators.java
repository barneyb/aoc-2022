package com.barneyb.aoc.aoc2017.day15;

import com.barneyb.aoc.util.Solver;
import lombok.Getter;
import lombok.val;

import java.util.stream.Collectors;

/**
 * Solver for 2017 day 15. API:
 *
 * <pre>
 * {@link #DuelingGenerators(String)}
 * {@link #DuelingGenerators(int, int)}
 * int {@link #getPairsInFortyMillion()}
 * </pre>
 */
public class DuelingGenerators {

    private static final int FACTOR_A = 16807;
    private static final int FACTOR_B = 48271;

    @Getter
    private final int pairsInFortyMillion;

    @SuppressWarnings("unused") // used via reflection in Solver
    public DuelingGenerators(String input) {
        val generators = input.trim()
                .lines()
                .map(l -> Integer.valueOf(l.substring(l.lastIndexOf(' ') + 1)))
                .collect(Collectors.toList());
        assert 2 == generators.size() : "Expected exactly two generators";
        pairsInFortyMillion = getMatches(generators.get(0), generators.get(1));
    }

    public DuelingGenerators(int seedA, int seedB) {
        pairsInFortyMillion = getMatches(seedA, seedB);
    }

    private static int getMatches(int seedA, int seedB) {
        val a = new Generator(seedA, FACTOR_A);
        val b = new Generator(seedB, FACTOR_B);
        var matches = 0;
        for (var i = 0; i < 40_000_000; i++) {
            if ((a.next() & 0xFFFF) == (b.next() & 0xFFFF)) {
                matches++;
            }
        }
        return matches;
    }

    public static void main(String[] args) {
        Solver.execute(DuelingGenerators.class,
                DuelingGenerators::getPairsInFortyMillion);
    }

}
