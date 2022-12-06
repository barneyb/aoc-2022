package com.barneyb.aoc.aoc2016.day01;

import com.barneyb.aoc.util.CharSequenceKt;
import com.barneyb.aoc.util.Slice;
import com.barneyb.aoc.util.SliceKt;
import com.barneyb.aoc.util.Solver;
import com.barneyb.util.Dir;
import com.barneyb.util.Vec2;
import lombok.Value;
import lombok.val;

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
                        CharSequenceKt.toInt(CharSequenceKt.subSequence(ins, 1))
                ))
                .collect(Collectors.toList());
    }

    public static void main(String[] args) {
        Solver.benchmark(NoTimeForATaxicab::new,
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

}
