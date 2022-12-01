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
 * int {@link #getFilteredPairsInFiveMillion()}
 * </pre>
 */
public class DuelingGenerators {

    private static final int FACTOR_A = 16807;
    private static final int FACTOR_B = 48271;

    private static final int FILTER_A = 4;
    private static final int FILTER_B = 8;

    @Getter
    private final int pairsInFortyMillion;

    @Getter
    private final int filteredPairsInFiveMillion;

    @SuppressWarnings("unused") // used via reflection in Solver
    public DuelingGenerators(String input) {
        val seeds = input.trim()
                .lines()
                .map(l -> Integer.valueOf(l.substring(l.lastIndexOf(' ') + 1)))
                .collect(Collectors.toList());
        assert 2 == seeds.size() : "Expected exactly two generators";
        pairsInFortyMillion = getMatches(seeds.get(0), seeds.get(1));
        filteredPairsInFiveMillion = getFilteredMatches(seeds.get(0), seeds.get(1));
    }

    public DuelingGenerators(int seedA, int seedB) {
        pairsInFortyMillion = getMatches(seedA, seedB);
        filteredPairsInFiveMillion = getFilteredMatches(seedA, seedB);
    }

    private static int countMatches(Generator a, Generator b, int iterations) {
        var matches = 0;
        for (var i = 0; i < iterations; i++) {
            if ((a.next() & 0xFFFF) == (b.next() & 0xFFFF)) {
                matches++;
            }
        }
        return matches;
    }

    private static int getMatches(int seedA, int seedB) {
        val a = new SequenceGenerator(seedA, FACTOR_A);
        val b = new SequenceGenerator(seedB, FACTOR_B);
        return countMatches(a, b, 40_000_000);
    }

    private static int getFilteredMatches(int seedA, int seedB) {
        val a = new FilteredGenerator(new SequenceGenerator(seedA, FACTOR_A), FILTER_A);
        val b = new FilteredGenerator(new SequenceGenerator(seedB, FACTOR_B), FILTER_B);
        return countMatches(a, b, 5_000_000);
    }

    public static void main(String[] args) {
        Solver.execute(DuelingGenerators::new,
                DuelingGenerators::getPairsInFortyMillion,
                DuelingGenerators::getFilteredPairsInFiveMillion);
    }

}
