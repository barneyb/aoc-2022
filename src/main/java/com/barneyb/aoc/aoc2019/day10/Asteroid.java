package com.barneyb.aoc.aoc2019.day10;

import com.barneyb.aoc.util.Vec2;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Asteroid {

    private final Vec2 position;
    private final Map<Double, List<Vec2>> fieldByTheta;

    public Asteroid(Vec2 position, Collection<Vec2> field) {
        this.position = position;
        this.fieldByTheta = field.stream()
                .filter(a -> a != position)
                .collect(Collectors.groupingBy(a ->
                        Math.atan2(a.getY() - position.getY(), a.getX() - position.getX())));
    }

    public int getDetectedCount() {
        return fieldByTheta.size();
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
