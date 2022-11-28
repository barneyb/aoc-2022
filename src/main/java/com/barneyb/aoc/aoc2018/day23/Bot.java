package com.barneyb.aoc.aoc2018.day23;

import com.barneyb.util.Vec3;
import lombok.Value;

@Value
public class Bot {

    Vec3 pos;
    int range;

    public boolean isInRange(Bot target) {
        return getManhattanDistance(target) <= range;
    }

    public int getManhattanDistance(Bot other) {
        return pos.getManhattanDistance(other.pos);
    }

}
