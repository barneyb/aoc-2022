package com.barneyb.util;

import lombok.Value;

@Value
public class Vec3 implements Comparable<Vec3> {

    int x, y, z;

    public int getManhattanDistance(Vec3 dest) {
        return Math.abs(x - dest.x) + Math.abs(y - dest.y) + Math.abs(z - dest.z);
    }

    @Override
    public int compareTo(Vec3 o) {
        if (x != o.x) return x - o.x;
        if (y != o.y) return y - o.y;
        if (z != o.z) return z - o.z;
        return 0;
    }

}
