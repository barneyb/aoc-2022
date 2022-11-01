package com.barneyb.aoc.aoc2016.day01;

import com.barneyb.aoc.util.Dir;
import com.barneyb.aoc.util.Solver;
import com.barneyb.aoc.util.Vec2;
import lombok.Value;
import lombok.val;

import java.util.Arrays;

public class NoTimeForATaxicab {

    public static void main(String[] args) {
        Solver.execute(NoTimeForATaxicab::partOne);
    }

    @Value
    private static class State {
        Dir heading;
        Vec2 position;
    }

    private static int partOne(String input) {
        return Arrays.stream(input.trim().split(" *, *"))
                .sequential()
                .reduce(new State(Dir.NORTH, Vec2.origin()), (s, ins) -> {
                    val h = ins.charAt(0) == 'R'
                            ? s.heading.turnRight()
                            : s.heading.turnLeft();
                    return new State(h, s.position.move(h, Integer.parseInt(ins.substring(1))));
                }, (a, b) -> {
                    throw new RuntimeException("can't combine");
                })
                .position
                .getManhattanDistance();
    }

}
