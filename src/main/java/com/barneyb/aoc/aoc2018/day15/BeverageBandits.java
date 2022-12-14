package com.barneyb.aoc.aoc2018.day15;

import com.barneyb.aoc.util.Solver;
import com.barneyb.util.Vec2;
import lombok.Value;
import lombok.val;

import java.util.ArrayList;

@Value
public class BeverageBandits {

    public static void main(String[] args) {
        Solver.execute(
                BeverageBandits::parse,
                BeverageBandits::partOne, // 198,531
                BeverageBandits::partTwo); // 90,420
    }

    public static int partOne(String input) {
        return partOne(parse(input));
    }

    public static int partOne(Parse parse) {
        return partOne(parse.toBattlefield());
    }

    public static int partOne(Battlefield battlefield) {
        var round = 0;
        while (battlefield.doRound()) {
            round++;
        }
//        System.out.printf("After round %d:%n%s%n", round, print(battlefield));
        return (round) * battlefield.getVictors()
                .stream()
                .mapToInt(Unit::getHitPoints)
                .sum();
    }

    public static int partTwo(String input) {
        return partTwo(parse(input));
    }

    public static int partTwo(Parse parse) {
        var hasWon = false;
        val elfCount = parse.getElves().size();
        for (var power = 4; ; power++) {
            val bf = parse.toBattlefield();
            for (Unit e : bf.getElves().values()) {
                e.setAttackPower(power);
            }
            val outcome = partOne(bf);
            if (Species.ELF == bf.getVictoriousSpecies()) {
                if (elfCount == bf.getElves().size()) {
                    System.out.println("Perfect elven victory w/ " + power + " attack.");
                    return outcome;
                } else if (!hasWon) {
                    System.out.println("Elven victory w/ " + power + " attack.");
                }
                hasWon = true;
            }
        }
    }

    static Parse parse(String input) {
        val result = new Parse();
        int x = 0, y = 0;
        for (char c : input.toCharArray()) {
            if (c == '\n') {
                y += 1;
                x = 0;
                continue;
            }
            if (c == 'E') {
                result.addElf(x, y);
            } else if (c == 'G') {
                result.addGoblin(x, y);
            } else if (c == '.') {
                result.addSpace(x, y);
            }
            x += 1;
        }
        return result;
    }

    @SuppressWarnings("unused")
    private static String print(Battlefield field) {
        val spaces = field.getSpaces();
        int width = 0, height = 0;
        for (val s : spaces) {
            width = Math.max(width, s.getX());
            height = Math.max(height, s.getY());
        }
        val goblins = field.getGoblins();
        val elves = field.getElves();
        val sb = new StringBuilder();
        for (var y = 0; y < height + 2; y++) {
            val units = new ArrayList<Unit>();
            for (var x = 0; x < width + 2; x++) {
                val p = new Vec2(x, y);
                if (goblins.containsKey(p)) {
                    sb.append('G');
                    units.add(goblins.get(p));
                } else if (elves.containsKey(p)) {
                    sb.append('E');
                    units.add(elves.get(p));
                } else if (spaces.contains(p)) {
                    sb.append('.');
                } else {
                    sb.append('#');
                }
            }
            if (!units.isEmpty()) {
                sb.append("   ");
                var started = false;
                for (val u : units) {
                    if (started) sb.append(", ");
                    else started = true;
                    sb.append(u.species.toString().charAt(0))
                            .append('(')
                            .append(u.getHitPoints())
                            .append(')');
                }
            }
            sb.append('\n');
        }
        return sb.toString();
    }

}
