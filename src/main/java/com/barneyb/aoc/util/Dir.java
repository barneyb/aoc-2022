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
}
