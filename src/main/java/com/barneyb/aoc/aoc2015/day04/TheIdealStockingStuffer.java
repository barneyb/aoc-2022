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
 * int {@link #getNumber()}
 * </pre>
 */
public class TheIdealStockingStuffer {

    @Getter
    private final int number;

    @SneakyThrows
    public TheIdealStockingStuffer(String input) {
        input = input.trim();
        val md = MessageDigest.getInstance("MD5");
        for (var i = 1; ; i++) {
            md.update((input + i).getBytes());
            val b = md.digest();
            if (b[0] == 0 && b[1] == 0 && (b[2] & 0xF0) == 0) {
                number = i;
                return;
            }
        }
    }

    public static void main(String[] args) {
        Solver.execute(TheIdealStockingStuffer.class,
                TheIdealStockingStuffer::getNumber);
    }
}
