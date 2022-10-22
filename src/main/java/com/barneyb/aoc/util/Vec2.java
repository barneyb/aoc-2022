package com.barneyb.aoc.util;

import lombok.Data;

@SuppressWarnings("unused")
@Data
public class Vec2 {
    final int x, y;

    public static Vec2 origin() {
        return new Vec2(0, 0);
    }

    public Vec2 sum(Vec2 other) {
        return new Vec2(x + other.x, y + other.y);
    }

    public Vec2 move(Dir dir) {
        return move(dir, 1);
    }

    public Vec2 move(Dir dir, int delta) {
        switch (dir) {
            case NORTH:
                return north(delta);
            case SOUTH:
                return south(delta);
            case EAST:
                return east(delta);
            case WEST:
                return west(delta);
        }
        throw new IllegalArgumentException("Unrecognized direction: " + dir);
    }

    public Vec2 north() {
        return north(1);
    }

    public Vec2 north(int delta) {
        return new Vec2(x, y - delta);
    }

    public Vec2 south() {
        return south(1);
    }

    public Vec2 south(int delta) {
        return new Vec2(x, y + delta);
    }

    public Vec2 east() {
        return east(1);
    }

    public Vec2 east(int delta) {
        return new Vec2(x + delta, y);
    }

    public Vec2 west() {
        return west(1);
    }

    public Vec2 west(int delta) {
        return new Vec2(x - delta, y);
    }

}
