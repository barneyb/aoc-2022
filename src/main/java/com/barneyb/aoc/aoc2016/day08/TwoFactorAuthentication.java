package com.barneyb.aoc.aoc2016.day08;

import com.barneyb.aoc.util.Solver;
import com.barneyb.util.IntPair;
import com.barneyb.util.Vec2;
import lombok.Value;
import lombok.val;

import java.util.HashSet;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Value
public class TwoFactorAuthentication {

    public static final int WIDTH = 50;
    public static final int HEIGHT = 6;

    private static final Pattern RE_RECT = Pattern.compile("rect (\\d+)x(\\d+)");
    private static final Pattern RE_COLUMN = Pattern.compile("rotate column x=(\\d+) by (\\d+)");
    private static final Pattern RE_ROW = Pattern.compile("rotate row y=(\\d+) by (\\d+)");

    int partOne;

    public TwoFactorAuthentication(String input) {
        val pixels = input.lines()
                .sequential()
                .filter(Predicate.not(String::isBlank))
                .map(TwoFactorAuthentication::parse)
                .reduce(new HashSet<Vec2>(), (ps, op) -> op.apply(ps), (a, b) -> {
                    throw new UnsupportedOperationException("no!");
                });
        partOne = pixels.size();
    }

    private static Function<HashSet<Vec2>, HashSet<Vec2>> parse(String line) {
        switch (line.charAt(7)) {
            case 'c': { // rotate column x=1 by 1
                val ins = rip(RE_COLUMN, line);
                return ps -> ps.stream()
                        .map(p -> p.getX() == ins.getFirst()
                                ? new Vec2(p.getX(), (p.getY() + ins.getSecond()) % HEIGHT)
                                : p)
                        .collect(Collectors.toCollection(HashSet::new));
            }
            case 'r': { // rotate row y=0 by 4
                val ins = rip(RE_ROW, line);
                return ps -> ps.stream()
                        .map(p -> p.getY() == ins.getFirst()
                                ? new Vec2((p.getX() + ins.getSecond()) % WIDTH, p.getY())
                                : p)
                        .collect(Collectors.toCollection(HashSet::new));
            }
            default: { // rect 3x2
                val ins = rip(RE_RECT, line);
                return ps -> {
                    val next = new HashSet<>(ps);
                    for (int x = 0; x < ins.getFirst(); x++) {
                        for (int y = 0; y < ins.getSecond(); y++) {
                            next.add(new Vec2(x, y));
                        }
                    }
                    return next;
                };
            }
        }
    }

    private static IntPair rip(Pattern p, String l) {
        val m = p.matcher(l);
        if (!m.matches()) {
            throw new IllegalArgumentException("Failed to match '" + l + "' with '" + p + "'");
        }
        return new IntPair(
                Integer.parseInt(m.group(1)),
                Integer.parseInt(m.group(2))
        );
    }

    public static void main(String[] args) {
        Solver.execute(TwoFactorAuthentication.class,
                TwoFactorAuthentication::getPartOne);
    }

}
