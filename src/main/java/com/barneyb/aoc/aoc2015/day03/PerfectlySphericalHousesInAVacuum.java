package com.barneyb.aoc.aoc2015.day03;

import com.barneyb.aoc.util.Solver;
import com.barneyb.util.Dir;
import com.barneyb.util.Vec2;
import lombok.Getter;
import lombok.val;

import java.util.HashSet;

/**
 * Solver for AoC 2015 Day 3. API:
 *
 * <pre>
 * {@link #PerfectlySphericalHousesInAVacuum(String)}
 * int {@link #getSoloUniqueHousesVisited()}
 * int {@link #getPairedUniqueHousesVisited()}
 * </pre>
 */
public class PerfectlySphericalHousesInAVacuum {

    @Getter
    private final int soloUniqueHousesVisited;

    @Getter
    private final int pairedUniqueHousesVisited;

    public PerfectlySphericalHousesInAVacuum(String input) {
        val soloVisited = new HashSet<Vec2>();
        var solo = Vec2.origin();
        soloVisited.add(solo);
        val pairedVisited = new HashSet<Vec2>();
        var meat = Vec2.origin();
        var robo = Vec2.origin();
        pairedVisited.add(meat);
        pairedVisited.add(robo);
        input = input.trim();
        for (var i = 0; i < input.length(); i++) {
            val d = Dir.parse(input.charAt(i));
            solo = solo.move(d);
            soloVisited.add(solo);
            if (i % 2 == 0) {
                meat = meat.move(d);
                pairedVisited.add(meat);
            } else {
                robo = robo.move(d);
                pairedVisited.add(robo);
            }
        }
        soloUniqueHousesVisited = soloVisited.size();
        pairedUniqueHousesVisited = pairedVisited.size();
    }

    public static void main(String[] args) {
        Solver.execute(PerfectlySphericalHousesInAVacuum.class,
                PerfectlySphericalHousesInAVacuum::getSoloUniqueHousesVisited,
                PerfectlySphericalHousesInAVacuum::getPairedUniqueHousesVisited);
    }

}
