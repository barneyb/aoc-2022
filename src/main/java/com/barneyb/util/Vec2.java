package com.barneyb.util;

import lombok.Value;

import java.util.Comparator;

@SuppressWarnings("unused")
@Value
public class Vec2 {

    /**
     * Note that this uses "graphic" coordinates, not "math" coordinates. That
     * is, "rightward" is positive-x and "downward" is positive-y. Math treats
     * "upward" as positive-y.
     */
    public static Comparator<Vec2> READING_ORDER = (a, b) ->
            a.y != b.y
                    ? a.y - b.y
                    : a.x - b.x;

    int x, y;

    public static Vec2 origin() {
        return new Vec2(0, 0);
    }

    public Vec2 plus(Vec2 other) {
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

    // @formatter:off
    public Vec2 north() { return north(1); }
    public Vec2 south() { return south(1); }
    public Vec2  east() { return  east(1); }
    public Vec2  west() { return  west(1); }

    public Vec2 north(int delta) { return new Vec2(x,         y - delta); }
    public Vec2 south(int delta) { return new Vec2(x,         y + delta); }
    public Vec2  east(int delta) { return new Vec2(x + delta, y        ); }
    public Vec2  west(int delta) { return new Vec2(x - delta, y        ); }

    public Vec2    up()          { return north(1); }
    public Vec2  down()          { return south(1); }
    public Vec2  left()          { return  west(1); }
    public Vec2 right()          { return  east(1); }

    public Vec2    up(int delta) { return north(delta); }
    public Vec2  down(int delta) { return south(delta); }
    public Vec2  left(int delta) { return  west(delta); }
    public Vec2 right(int delta) { return  east(delta); }
    // @formatter:on

    public int getManhattanDistance() {
        return getManhattanDistance(origin());
    }

    public int getManhattanDistance(Vec2 dest) {
        return Math.abs(x - dest.x) + Math.abs(y - dest.y);
    }
}
