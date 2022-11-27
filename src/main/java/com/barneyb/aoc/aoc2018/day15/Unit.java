package com.barneyb.aoc.aoc2018.day15;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Comparator;

@Data
@RequiredArgsConstructor
public class Unit {

    public static final int DEFAULT_ATTACK_POWER = 3;

    public static final Comparator<Unit> WEAKEST = Comparator.comparingInt(Unit::getHitPoints);

    final Species species;
    int attackPower = DEFAULT_ATTACK_POWER;
    private int hitPoints = 200;

    public Unit(Unit basis) {
        this.species = basis.species;
        this.attackPower = basis.attackPower;
        this.hitPoints = basis.hitPoints;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public boolean isDead() {
        return hitPoints <= 0;
    }
}
