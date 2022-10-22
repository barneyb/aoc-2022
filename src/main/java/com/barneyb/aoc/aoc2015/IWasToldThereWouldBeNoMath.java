package com.barneyb.aoc.aoc2015;

import com.barneyb.aoc.util.Pair;
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
        input = input.trim();
        val pair = input.lines().map(spec -> {
            val dims = spec.split("x");
            return new Present(
                    Integer.parseInt(dims[0]),
                    Integer.parseInt(dims[1]),
                    Integer.parseInt(dims[2]));
        }).reduce(
                new Pair<>(0, 0),
                (r, p) -> new Pair<>(r.getFirst() + p.getPaperNeeded(), r.getSecond() + p.getRibbonNeeded()),
                (a, b) -> new Pair<>(a.getFirst() + b.getFirst(), a.getSecond() + b.getSecond())
        );
        paperNeeded = pair.getFirst();
        ribbonNeeded = pair.getSecond();
    }

    @RequiredArgsConstructor
    private static class Present {
        final int w, h, l;

        int getSurfaceArea() {
            return 2 * l * w + 2 * w * h + 2 * h * l;
        }

        int getAreaOfSmallestFace() {
            if (w <= l && h <= l) {
                return w * h;
            } else if (w <= h) {
                return w * l;
            } else {
                return h * l;
            }
        }

        int getPaperNeeded() {
            return getSurfaceArea() + getAreaOfSmallestFace();
        }

        int getVolume() {
            return w * h * l;
        }

        int getPerimiterOfSmallestFace() {
            if (w <= l && h <= l) {
                return 2 * w + 2 * h;
            } else if (w <= h) {
                return 2 * w + 2 * l;
            } else {
                return 2 * h + 2 * l;
            }
        }

        int getRibbonNeeded() {
            return getPerimiterOfSmallestFace() + getVolume();
        }
    }

    public static void main(String[] args) {
        Solver.execute(IWasToldThereWouldBeNoMath.class,
                IWasToldThereWouldBeNoMath::getPaperNeeded,
                IWasToldThereWouldBeNoMath::getRibbonNeeded);
    }

}
