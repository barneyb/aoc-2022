package com.barneyb.aoc.aoc2018.day03;

import com.barneyb.aoc.util.Solver;
import com.barneyb.util.Hist;
import com.barneyb.util.Vec2;
import lombok.Getter;
import lombok.Value;
import lombok.val;

import java.util.Scanner;
import java.util.stream.Collectors;

public class NoMatterHowYouSliceIt {

    private static final String EXAMPLE = "#1 @ 1,3: 4x4\n" +
            "#2 @ 3,1: 4x4\n" +
            "#3 @ 5,5: 2x2";

    @Value
    private static class Claim {
        int id, x, y, w, h;

        boolean intersects(Claim c) {
            if (x + w < c.x) return false; // left of c
            if (x > c.x + c.w) return false; // right of c
            if (y + h < c.y) return false; // above c
            if (y > c.y + c.h) return false; // below c
            return true;
        }
    }

    @Getter
    private final long overlappingInches;

    @Getter
    private int isolatedClaimId;

    public NoMatterHowYouSliceIt(String input) {
        val claims = input
                .trim()
                .lines()
                .map(line -> {
                    val s = new Scanner(line.replaceAll("[^0-9]+", " ").trim());
                    return new Claim(s.nextInt(), s.nextInt(), s.nextInt(), s.nextInt(), s.nextInt());
                })
                .collect(Collectors.toList());
        val hist = new Hist<Vec2>();
        for (val c : claims) {
            for (var x = c.x + c.w; x > c.x; x--) {
                for (var y = c.y + c.h; y > c.y; y--) {
                    hist.put(new Vec2(x, y));
                }
            }
        }
        overlappingInches = hist.keySet()
                .stream()
                .filter(k -> hist.get(k) > 1)
                .count();
        outer:
        for (val a : claims) {
            for (val b : claims) {
                if (a != b && a.intersects(b)) {
                    continue outer;
                }
            }
            isolatedClaimId = a.id;
            break;
        }
    }

    public static void main(String[] args) {
        Solver.execute(NoMatterHowYouSliceIt.class,
                NoMatterHowYouSliceIt::getOverlappingInches,
                NoMatterHowYouSliceIt::getIsolatedClaimId);
    }

}
