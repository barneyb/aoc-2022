package com.barneyb.aoc.aoc2015.day04;

import com.barneyb.aoc.util.Solver;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.val;

import java.security.MessageDigest;

/**
 * AoC 2015 day 4 solver. API:
 *
 * <pre>
 * {@link #TheIdealStockingStuffer(String)}
 * int {@link #getFiveZeroNumber()}
 * int {@link #getSixZeroNumber()}
 * </pre>
 */
public class TheIdealStockingStuffer {

    @Getter
    private final int fiveZeroNumber;

    @Getter
    private final int sixZeroNumber;

    @SneakyThrows
    public TheIdealStockingStuffer(String input) {
        input = input.trim();
        val baseMd = MessageDigest.getInstance("MD5");
        baseMd.update(input.getBytes());
        int fiveZeros = 0;
        for (var i = 1; ; i++) {
            val md = (MessageDigest) baseMd.clone();
            md.update(("" + i).getBytes());
            val b = md.digest();
            if (b[0] == 0 && b[1] == 0) {
                if (fiveZeros == 0 && (b[2] & 0xF0) == 0) {
                    fiveZeros = i;
                }
                if (b[2] == 0) {
                    fiveZeroNumber = fiveZeros;
                    sixZeroNumber = i;
                    return;
                }
            }
        }
    }

    public static void main(String[] args) {
        Solver.execute(TheIdealStockingStuffer::new,
                TheIdealStockingStuffer::getFiveZeroNumber,
                TheIdealStockingStuffer::getSixZeroNumber);
    }
}
