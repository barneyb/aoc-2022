package com.barneyb.aoc.aoc2019.day10;

import com.barneyb.aoc.util.Solver;
import com.barneyb.util.Chars;
import com.barneyb.util.Vec2;
import lombok.val;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.stream.Collectors;

import static com.barneyb.util.With.with;

public class MonitoringStation {

    private final Asteroid station;

    public MonitoringStation(String input) {
        val asteroids = parse(input);
        //noinspection OptionalGetWithoutIsPresent
        this.station = asteroids.stream()
                .max(Comparator.comparingInt(Asteroid::getDetectedCount))
                .get();
        System.out.println(station);
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

    public int getMaxDetectedAsteroids() {
        return station.getDetectedCount();
    }

    public Vec2 getNthVaporized(int n) {
        return station.getNthVaporized(n);
    }

    public static void main(String[] args) {
        Solver.execute(MonitoringStation.class,
                MonitoringStation::getMaxDetectedAsteroids,
                m -> with(m.getNthVaporized(200), a ->
                        a.getX() * 100 + a.getY()));
    }
}
