package com.barneyb.aoc.aoc2015.day02;

import com.barneyb.aoc.util.IntPair;
import com.barneyb.aoc.util.Parse;
import lombok.RequiredArgsConstructor;
import lombok.val;

@RequiredArgsConstructor
class Present {

    static Present parse(String spec) {
        return new Present(Parse.ints(spec.split("x")));
    }

    private final int w, h, l;

    Present(int[] dims) {
        w = dims[0];
        h = dims[1];
        l = dims[2];
    }

    int getSurfaceArea() {
        return 2 * l * w + 2 * w * h + 2 * h * l;
    }

    private IntPair getSmallestFace() {
        if (w <= l && h <= l) {
            return new IntPair(w, h);
        } else if (w <= h) {
            return new IntPair(w, l);
        } else {
            return new IntPair(h, l);
        }
    }

    int getAreaOfSmallestFace() {
        val f = getSmallestFace();
        return f.getFirst() * f.getSecond();
    }

    int getPaperNeeded() {
        return getSurfaceArea() + getAreaOfSmallestFace();
    }

    int getVolume() {
        return w * h * l;
    }

    int getPerimeterOfSmallestFace() {
        val f = getSmallestFace();
        return 2 * (f.getFirst() + f.getSecond());
    }

    int getRibbonNeeded() {
        return getPerimeterOfSmallestFace() + getVolume();
    }
}
