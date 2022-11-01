package com.barneyb.aoc.util;

public enum Dir {
    NORTH,
    SOUTH,
    EAST,
    WEST;

    public static Dir parse(int c) {
        switch (c) {
            case '^':
                return NORTH;
            case 'v':
                return SOUTH;
            case '<':
                return WEST;
            case '>':
                return EAST;
        }
        throw new IllegalArgumentException("Unrecognized direction literal: " + c);
    }

    public Dir turnLeft() {
        switch (this) {
            case NORTH:
                return WEST;
            case SOUTH:
                return EAST;
            case EAST:
                return NORTH;
            case WEST:
                return SOUTH;
        }
        throw new IllegalArgumentException("Unrecognized direction: " + this);
    }

    public Dir turnRight() {
        switch (this) {
            case NORTH:
                return EAST;
            case SOUTH:
                return WEST;
            case EAST:
                return SOUTH;
            case WEST:
                return NORTH;
        }
        throw new IllegalArgumentException("Unrecognized direction: " + this);
    }
}
