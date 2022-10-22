package com.barneyb.aoc.aoc2015;

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
 * </pre>
 */
public class IWasToldThereWouldBeNoMath {

    @Getter
    private final int paperNeeded;

    public IWasToldThereWouldBeNoMath(String input) {
        input = input.trim();
        paperNeeded = input.lines().mapToInt(spec -> {
            val dims = spec.split("x");
            val p = new Present(
                    Integer.parseInt(dims[0]),
                    Integer.parseInt(dims[1]),
                    Integer.parseInt(dims[2]));
            return p.getPaperNeeded();
        }).sum();
    }

    @RequiredArgsConstructor
    private static class Present {
        final int w, h, l;

        int getSurfaceArea() {
            return 2 * l * w + 2 * w * h + 2 * h * l;
        }

        int getSlack() {
            if (w <= l && h <= l) {
                return w * h;
            } else if (w <= h) {
                return w * l;
            } else {
                return h * l;
            }
        }

        int getPaperNeeded() {
            return getSurfaceArea() + getSlack();
        }
    }

    public static void main(String[] args) {
        Solver.execute(IWasToldThereWouldBeNoMath.class,
                IWasToldThereWouldBeNoMath::getPaperNeeded);
    }

}
