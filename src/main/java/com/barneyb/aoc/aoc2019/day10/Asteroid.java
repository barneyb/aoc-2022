package com.barneyb.aoc.aoc2019.day10;

import com.barneyb.aoc.util.Vec2;
import lombok.val;

import java.util.*;
import java.util.stream.Collectors;

import static com.barneyb.aoc.util.With.with;

public class Asteroid {

    private final Vec2 position;
    private final SortedMap<Double, List<Vec2>> fieldByTheta;

    public Asteroid(Vec2 position, Collection<Vec2> field) {
        this.position = position;
        this.fieldByTheta = field.stream()
                .filter(a -> a != position)
                .collect(Collectors.groupingBy(
                        // theta=0 is along the X axis, but we want it along the
                        // Y axis, so rotate CCW by 90 degrees.
                        a -> with(Math.atan2(position.getY() - a.getY(), position.getX() - a.getX()) - Math.PI / 2,
                                t -> t < 0 ? t + 2 * Math.PI : t),
                        TreeMap::new,
                        Collectors.toList()
                ));
    }

    public int getDetectedCount() {
        return fieldByTheta.size();
    }

    public Vec2 getNthVaporized(int n) {
        assert getDetectedCount() >= n : "we don't handle multiple rotations";
        val itr = fieldByTheta.values().iterator();
        for (int i = 1; i < n; i++) itr.next();
        //noinspection OptionalGetWithoutIsPresent
        return itr.next().stream()
                // we want the closest one along this line of sight
                .min(Comparator.comparingInt(position::getManhattanDistance))
                .get();
    }

    public int getX() {
        return position.getX();
    }

    public int getY() {
        return position.getY();
    }

    @Override
    public String toString() {
        return "Asteroid(" + getX() + "," + getY() + " w/ " + getDetectedCount() + ")";
    }

    @Override
    @SuppressWarnings("com.haulmont.jpb.EqualsDoesntCheckParameterClass")
    public boolean equals(Object o) {
        return position.equals(o);
    }

    @Override
    public int hashCode() {
        return position.hashCode();
    }
}
