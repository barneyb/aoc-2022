package com.barneyb.aoc.aoc2015.day03;

import com.barneyb.aoc.util.Dir;
import com.barneyb.aoc.util.Solver;
import com.barneyb.aoc.util.Vec2;
import lombok.Getter;
import lombok.val;

import java.util.HashSet;

/**
 * Solver for AoC 2015 Day 3. API:
 *
 * <pre>
 * {@link #PerfectlySphericalHousesInAVacuum(String)}
 * int {@link #getUniqueHousesVisited()}
 * </pre>
 */
public class PerfectlySphericalHousesInAVacuum {

    @Getter
    private final int uniqueHousesVisited;

    public PerfectlySphericalHousesInAVacuum(String input) {
        val visited = new HashSet<Vec2>();
        var curr = Vec2.origin();
        visited.add(curr);
        input = input.trim();
        for (var i = 0; i < input.length(); i++) {
            val d = Dir.parse(input.charAt(i));
            curr = curr.move(d);
            visited.add(curr);
        }
        uniqueHousesVisited = visited.size();
    }

    public static void main(String[] args) {
        Solver.execute(PerfectlySphericalHousesInAVacuum.class,
                PerfectlySphericalHousesInAVacuum::getUniqueHousesVisited);
    }

}
