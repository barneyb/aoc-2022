package com.barneyb.util;

import lombok.Value;

@Value
public class Vec3 {

    int x, y, z;

    public int getManhattanDistance(Vec3 dest) {
        return Math.abs(x - dest.x) + Math.abs(y - dest.y) + Math.abs(z - dest.z);
    }

}
