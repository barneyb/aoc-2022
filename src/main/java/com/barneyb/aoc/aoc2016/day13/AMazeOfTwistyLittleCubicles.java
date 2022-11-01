package com.barneyb.aoc.aoc2016.day13;

import com.barneyb.aoc.util.Solver;
import com.barneyb.aoc.util.Vec2;

import java.util.HashMap;
import java.util.Map;

public class AMazeOfTwistyLittleCubicles {

    private static final Vec2 START = new Vec2(1, 1);
    private static final Vec2 GOAL = new Vec2(31, 39);

    private final int magic;

    private final Map<Vec2, Integer> visited;

    public AMazeOfTwistyLittleCubicles(String input) {
        this(Integer.parseInt(input.trim()));
    }

    AMazeOfTwistyLittleCubicles(int favNum) {
        magic = favNum;
        visited = new HashMap<>();
    }

    public int getStepsTo(Vec2 goal) {
        if (!visited.containsKey(goal)) {
            stepsToGoal(goal, START, 0);
        }
        return visited.getOrDefault(goal, -1);
    }

    public int getStepsToGoal() {
        return getStepsTo(GOAL);
    }

    private void stepsToGoal(Vec2 goal, Vec2 pos, int soFar) {
        if (pos.getX() < 0 || pos.getY() < 0) return; // outside
        if (!isOpen(pos)) return; // wall
        if (visited.containsKey(pos) && visited.get(pos) <= soFar) {
            return; // too long
        }
        visited.put(pos, soFar);
        if (goal.equals(pos)) return;// done!
        stepsToGoal(goal, pos.north(), soFar + 1);
        stepsToGoal(goal, pos.south(), soFar + 1);
        stepsToGoal(goal, pos.east(), soFar + 1);
        stepsToGoal(goal, pos.west(), soFar + 1);
    }

    public long getCubesWithin(int steps) {
        Vec2 goal;
        for (int y = 0; ; y++) {
            goal = new Vec2(steps, y);
            if (isOpen(goal)) break;
        }
        getStepsTo(goal);
        return visited.keySet().stream().filter(k -> visited.get(k) <= steps).count();
    }

    public long getCubesWithinFiftySteps() {
        return getCubesWithin(50);
    }

    private boolean isOpen(Vec2 pos) {
        int x = pos.getX(), y = pos.getY();
        int n = x * x + 3 * x + 2 * x * y + y + y * y + magic;
        int bits = Integer.bitCount(n);
        return bits % 2 == 0;
    }

    public static void main(String[] args) {
        Solver.execute(AMazeOfTwistyLittleCubicles.class,
                AMazeOfTwistyLittleCubicles::getStepsToGoal,
                AMazeOfTwistyLittleCubicles::getCubesWithinFiftySteps);
    }

}
