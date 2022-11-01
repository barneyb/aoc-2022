package com.barneyb.aoc.aoc2019.day10;

import com.barneyb.aoc.util.Chars;
import com.barneyb.aoc.util.Solver;
import com.barneyb.aoc.util.Vec2;
import lombok.Getter;
import lombok.val;

import java.util.HashSet;
import java.util.Set;

public class MonitoringStation {

    @Getter
    private final long maxVisibleAsteroids;

    public MonitoringStation(String input) {
        val asteroids = parse(input);
        maxVisibleAsteroids = asteroids.stream()
                .mapToLong(base -> asteroids.stream()
                        .filter(a -> a != base)
                        .map(a -> Math.atan2(a.getY() - base.getY(), a.getX() - base.getX()))
                        .distinct()
                        .count())
                .max()
                .orElse(-1);
    }

    private static Set<Vec2> parse(String input) {
        val asteroids = new HashSet<Vec2>();
        int x = 0, y = 0;
        for (val c : new Chars(input.trim())) {
            if (c == '#') {
                asteroids.add(new Vec2(x, y));
            }
            if (c == '\n') {
                y++;
                x = 0;
            } else {
                x++;
            }
        }
        return asteroids;
    }

    public static void main(String[] args) {
        Solver.execute(MonitoringStation.class,
                MonitoringStation::getMaxVisibleAsteroids);
    }
}
