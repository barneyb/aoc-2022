package com.barneyb.aoc.aoc2018.day15;

import com.barneyb.aoc.util.Solver;
import com.barneyb.util.Vec2;
import lombok.Value;
import lombok.val;

import java.util.ArrayList;

@Value
public class BeverageBandits {

    public static void main(String[] args) {
        System.out.println(BeverageBandits.partOne(parse("#######\n" +
                "#.G...#\n" +
                "#...EG#\n" +
                "#.#.#G#\n" +
                "#..G#E#\n" +
                "#.....#\n" +
                "#######")));
        System.out.println(BeverageBandits.partOne(parse("#######\n" +
                "#G..#E#\n" +
                "#E#E.E#\n" +
                "#G.##.#\n" +
                "#...#E#\n" +
                "#...E.#\n" +
                "#######")));
        System.out.println(BeverageBandits.partOne(parse("#######\n" +
                "#E..EG#\n" +
                "#.#G.E#\n" +
                "#E.##E#\n" +
                "#G..#.#\n" +
                "#..E#.#\n" +
                "#######")));
        System.out.println(BeverageBandits.partOne(parse("#######\n" +
                "#E.G#.#\n" +
                "#.#G..#\n" +
                "#G.#.G#\n" +
                "#G..#.#\n" +
                "#...E.#\n" +
                "#######")));
        System.out.println(BeverageBandits.partOne(parse("#######\n" +
                "#.E...#\n" +
                "#.#..G#\n" +
                "#.###.#\n" +
                "#E#G#G#\n" +
                "#...#G#\n" +
                "#######")));
        System.out.println(BeverageBandits.partOne(parse("#########\n" +
                "#G......#\n" +
                "#.E.#...#\n" +
                "#..##..G#\n" +
                "#...##..#\n" +
                "#...#...#\n" +
                "#.G...G.#\n" +
                "#.....G.#\n" +
                "#########")));
        Solver.execute(BeverageBandits::parse,
                BeverageBandits::partOne,
                null);
    }

    private static int partOne(Battlefield battlefield) {
        var round = 0;
        while (battlefield.doRound()) {
            round++;
        }
        System.out.printf("After round %d:%n%s%n", round, print(battlefield));
        return (round) * battlefield.getVictors()
                .stream()
                .mapToInt(Unit::getHitPoints)
                .sum();
    }

    private static Battlefield parse(String input) {
        val field = new Battlefield();
        int x = 0, y = 0;
        for (char c : input.toCharArray()) {
            if (c == '\n') {
                y += 1;
                x = 0;
                continue;
            }
            if (c == 'E') {
                field.addElf(x, y);
            } else if (c == 'G') {
                field.addGoblin(x, y);
            } else if (c == '.') {
                field.addSpace(x, y);
            }
            x += 1;
        }
        return field;
    }

    private static String print(Battlefield field) {
        var spaces = field.getSpaces();
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
