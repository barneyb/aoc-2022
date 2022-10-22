package com.barneyb.aoc.aoc2015.day02;

import com.barneyb.aoc.util.IntPair;
import com.barneyb.aoc.util.Parse;
import com.barneyb.aoc.util.Solver;
import lombok.Getter;
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
        val pair = Parse.lines(input, Present::parse)
                .map(p -> new IntPair(
                        p.getPaperNeeded(),
                        p.getRibbonNeeded()))
                .reduce(IntPair.zero(), IntPair::sum);
        paperNeeded = pair.getFirst();
        ribbonNeeded = pair.getSecond();
    }

    public static void main(String[] args) {
        Solver.execute(IWasToldThereWouldBeNoMath.class,
                IWasToldThereWouldBeNoMath::getPaperNeeded,
                IWasToldThereWouldBeNoMath::getRibbonNeeded);
    }

}
