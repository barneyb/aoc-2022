package com.barneyb.aoc.aoc2018.day03;

import com.barneyb.aoc.util.Hist;
import com.barneyb.aoc.util.Input;
import com.barneyb.aoc.util.Vec2;
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
    }

    public static void main(String[] args) {
        val claims = Input.forProblem(NoMatterHowYouSliceIt.class)
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
        System.out.println(hist.keySet()
                .stream()
                .filter(k -> hist.get(k) > 1)
                .count());
    }

}
