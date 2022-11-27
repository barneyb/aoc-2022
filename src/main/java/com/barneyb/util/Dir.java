package com.barneyb.util;

public enum Dir {
    NORTH,
    SOUTH,
    EAST,
    WEST;

    public static Dir parse(int c) {
        switch (c) {
            case 'n':
            case 'N':
            case 'u':
            case 'U':
            case '^':
                return NORTH;
            case 's':
            case 'S':
            case 'd':
            case 'D':
            case 'v':
                return SOUTH;
            case 'e':
            case 'E':
            case 'r':
            case 'R':
            case '>':
                return EAST;
            case 'w':
            case 'W':
            case 'l':
            case 'L':
            case '<':
                return WEST;
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
