package com.barneyb.aoc.aoc2016.day01;

import com.barneyb.aoc.util.*;
import com.barneyb.util.Dir;
import com.barneyb.util.Vec2;
import kotlin.text.StringsKt;
import lombok.Value;
import lombok.val;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class NoTimeForATaxicab {

    private final List<Ins> instructions;

    public NoTimeForATaxicab(String input) {
        this.instructions = SliceKt.toSlice(input)
                .split(',')
                .stream()
                .map(Slice::trim)
                .map(ins -> new Ins(
                        ins.charAt(0) == 'R' ? Turn.RIGHT : Turn.LEFT,
                        CharSequenceKt.toInt(StringsKt.drop(ins, 1))
                ))
                .collect(Collectors.toList());
    }

    public static void main(String[] args) {
        var it = new NoTimeForATaxicab(Input.forProblem(NoTimeForATaxicab.class));
        it.draw();
        Solver.benchmark(garbage -> it,
                NoTimeForATaxicab::partOne,
                NoTimeForATaxicab::partTwo);
    }

    private enum Turn {
        LEFT,
        RIGHT;

        Dir execute(Dir d) {
            return this == Turn.RIGHT
                    ? d.turnRight()
                    : d.turnLeft();
        }
    }

    @Value
    private static class Ins {
        Turn turn;
        int move;
    }

    @Value
    private static class State {
        Dir heading;
        Vec2 position;
    }

    int partOne() {
        return instructions.stream()
                .reduce(new State(Dir.NORTH, Vec2.origin()), (s, ins) -> {
                    val h = ins.turn.execute(s.heading);
                    return new State(h, s.position.move(h, ins.move));
                }, (a, b) -> {
                    throw new RuntimeException("can't combine");
                })
                .position
                .getManhattanDistance();
    }

    @SuppressWarnings("unused")
    int partOneAgain() {
        var h = Dir.NORTH;
        var pos = Vec2.origin();
        for (val ins : instructions) {
            h = ins.turn.execute(h);
            pos = pos.move(h, ins.move);
        }
        return pos.getManhattanDistance();
    }

    int partTwo() {
        val visited = new HashSet<Vec2>();
        var h = Dir.NORTH;
        var pos = Vec2.origin();
        visited.add(pos);
        for (val ins : instructions) {
            h = ins.turn.execute(h);
            for (var i = 0; i < ins.move; i++) {
                pos = pos.move(h);
                if (!visited.add(pos)) {
                    return pos.getManhattanDistance();
                }
            }
        }
        throw new IllegalStateException("Path never crosses itself?!");
    }

    void draw() {
        val visited = new HashSet<Vec2>();
        val turns = new HashMap<Vec2, Dir>();
        var h = Dir.NORTH;
        var pos = Vec2.origin();
        var rOne = -1;
        visited.add(pos);
        for (val ins : instructions) {
            h = ins.turn.execute(h);
            turns.put(pos, h);
            for (var i = 0; i < ins.move; i++) {
                pos = pos.move(h);
                if (!visited.add(pos) && rOne < 0) {
                    rOne = pos.getManhattanDistance();
                }
            }
        }
        val rTwo = pos.getManhattanDistance();
        var min = visited.iterator().next();
        var max = min;
        for (val p : visited) {
            if (p.getX() < min.getX() || p.getY() < min.getY()) {
                min = new Vec2(
                        Math.min(p.getX(), min.getX()),
                        Math.min(p.getY(), min.getY())
                );
            }
            if (p.getX() < max.getX() || p.getY() < max.getY()) {
                max = new Vec2(
                        Math.max(p.getX(), max.getX()),
                        Math.max(p.getY(), max.getY())
                );
            }
        }
        System.out.println();
        for (int y = min.getY() - 1, my = max.getY() + 1; y < my; y++) {
            for (int x = min.getX() - 1, mx = max.getX() + 1; x < mx; x++) {
                pos = new Vec2(x, y);
                var md = pos.getManhattanDistance();
                System.out.print(
                        turns.containsKey(pos)
                                ? render(turns.get(pos))
                                : visited.contains(pos)
                                ? '·'
                                : md == rOne || md == rTwo
                                ? '◌'
                                : x == 0
                                ? '|'
                                : y == 0
                                ? '-'
                                : ' ');
            }
            System.out.println();
        }
    }

    private char render(Dir d) {
        switch (d) {
            case NORTH:
                return '▲';
            case SOUTH:
                return '▼';
            case EAST:
                return '▶';
            case WEST:
                return '◀';
        }
        throw new IllegalArgumentException();
    }

}
