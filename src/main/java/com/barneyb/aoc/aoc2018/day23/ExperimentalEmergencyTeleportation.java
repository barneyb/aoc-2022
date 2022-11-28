package com.barneyb.aoc.aoc2018.day23;

import com.barneyb.aoc.util.Solver;
import com.barneyb.util.Vec3;
import lombok.Value;
import lombok.val;

import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Value
public class ExperimentalEmergencyTeleportation {

    long partOne;
    long partTwo;

    private static Pattern RE_BOT = Pattern.compile("pos=<(-?\\d+),(-?\\d+),(-?\\d+)>, r=(\\d+)");

    public ExperimentalEmergencyTeleportation(String input) {
        val bots = input.lines()
                .map(RE_BOT::matcher)
                .filter(Matcher::matches)
                .map(m -> new Bot(
                        new Vec3(
                                Integer.parseInt(m.group(1)),
                                Integer.parseInt(m.group(2)),
                                Integer.parseInt(m.group(3))
                        ),
                        Integer.parseInt(m.group(4))
                ))
                .collect(Collectors.toList());
        val strongest = bots.stream()
                .max(Comparator.comparingInt(Bot::getRange))
                .orElseThrow();
        partOne = bots.stream()
                .filter(strongest::isInRange)
                .count();
        partTwo = bots.size();
    }

    public static void main(String[] args) {
        Solver.execute(ExperimentalEmergencyTeleportation.class,
                ExperimentalEmergencyTeleportation::getPartOne,
                ExperimentalEmergencyTeleportation::getPartTwo);
    }

}
