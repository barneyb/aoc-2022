package com.barneyb.aoc.aoc2019.day10;

import com.barneyb.aoc.util.Chars;
import com.barneyb.aoc.util.Solver;
import com.barneyb.aoc.util.Vec2;
import lombok.Getter;
import lombok.val;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.stream.Collectors;

public class MonitoringStation {

    @Getter
    private final long maxDetectedAsteroids;

    public MonitoringStation(String input) {
        val asteroids = parse(input);
        //noinspection OptionalGetWithoutIsPresent
        val base = asteroids.stream()
                .max(Comparator.comparingInt(Asteroid::getDetectedCount))
                .get();
        System.out.println(base);
        maxDetectedAsteroids = base.getDetectedCount();
    }

    private static Collection<Asteroid> parse(String input) {
        val field = new HashSet<Vec2>();
        int x = 0, y = 0;
        for (val c : new Chars(input.trim())) {
            if (c == '#') {
                field.add(new Vec2(x, y));
            }
            if (c == '\n') {
                y++;
                x = 0;
            } else {
                x++;
            }
        }
        return field.stream()
                .map(p -> new Asteroid(p, field))
                .collect(Collectors.toList());
    }

    public static void main(String[] args) {
        Solver.execute(MonitoringStation.class,
                MonitoringStation::getMaxDetectedAsteroids);
    }
}
