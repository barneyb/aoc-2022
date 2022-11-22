package com.barneyb.aoc.aoc2018.day15;

import lombok.Data;

import java.util.Comparator;

@Data
public class Unit {

    public static final Comparator<Unit> WEAKEST = Comparator.comparingInt(Unit::getHitPoints);

    final Species species;
    private int hitPoints = 200;

    public boolean isDead() {
        return hitPoints <= 0;
    }
}
