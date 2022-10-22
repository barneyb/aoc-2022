package com.barneyb.aoc.aoc2015;

import com.barneyb.aoc.util.IntPair;
import com.barneyb.aoc.util.Parse;
import com.barneyb.aoc.util.Solver;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.val;

/**
 * Solver for AoC 2015 day 2. API:
 *
 * <pre>
 * {@link #IWasToldThereWouldBeNoMath(String)}
 * int {@link #getPaperNeeded()}
 * int {@link #getRibbonNeeded()}
 * </pre>
 */
public class IWasToldThereWouldBeNoMath {

    @Getter
    private final int paperNeeded;

    @Getter
    private final int ribbonNeeded;

    public IWasToldThereWouldBeNoMath(String input) {
        val pair = input.trim()
                .lines()
                .map(spec ->
                        spec.split("x"))
                .map(Parse::ints)
                .map(Present::new)
                .map(p ->
                        new IntPair(p.getPaperNeeded(), p.getRibbonNeeded()))
                .reduce(IntPair.zero(), IntPair::sum);
        paperNeeded = pair.getFirst();
        ribbonNeeded = pair.getSecond();
    }

    @RequiredArgsConstructor
    private static class Present {
        final int w, h, l;

        Present(int[] dims) {
            w = dims[0];
            h = dims[1];
            l = dims[2];
        }

        int getSurfaceArea() {
            return 2 * l * w + 2 * w * h + 2 * h * l;
        }

        IntPair getSmallestFace() {
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

    public static void main(String[] args) {
        Solver.execute(IWasToldThereWouldBeNoMath.class,
                IWasToldThereWouldBeNoMath::getPaperNeeded,
                IWasToldThereWouldBeNoMath::getRibbonNeeded);
    }

}
