package com.barneyb.aoc.aoc2018.day15;

import com.barneyb.util.Dir;
import com.barneyb.util.Vec2;
import lombok.Value;
import lombok.val;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Battlefield {

    public static final int ATTACK_POWER = 3;

    private final Set<Vec2> field = new HashSet<>();
    private final Map<Vec2, Unit> goblins = new HashMap<>();
    private final Map<Vec2, Unit> elves = new HashMap<>();

    public void addSpace(int x, int y) {
        field.add(new Vec2(x, y));
    }

    public void addGoblin(int x, int y) {
        val p = new Vec2(x, y);
        field.add(p);
        goblins.put(p, new Unit(Species.GOBLIN));
    }

    public void addElf(int x, int y) {
        val p = new Vec2(x, y);
        field.add(p);
        elves.put(p, new Unit(Species.ELF));
    }

    public Set<Vec2> getSpaces() {
        return Collections.unmodifiableSet(field);
    }

    public Map<Vec2, Unit> getGoblins() {
        return Collections.unmodifiableMap(goblins);
    }

    public Map<Vec2, Unit> getElves() {
        return Collections.unmodifiableMap(elves);
    }

    public boolean isBattleComplete() {
        return goblins.isEmpty() || elves.isEmpty();
    }

    public Collection<Unit> getVictors() {
        if (goblins.isEmpty()) return elves.values();
        if (elves.isEmpty()) return goblins.values();
        throw new IllegalStateException("The battle continues!");
    }

    private Stream<Vec2> adjacentPositions(Vec2 p) {
        return Arrays.stream(Dir.values())
                .map(p::move)
                .filter(field::contains)
                .sorted(Vec2.READING_ORDER);
    }

    private Stream<Vec2> adjacentEnemies(Vec2 p, Map<Vec2, Unit> enemies) {
        return adjacentPositions(p)
                .filter(enemies::containsKey);
    }

    private Stream<Vec2> adjacentOpenPositions(Vec2 p) {
        return adjacentPositions(p)
                .filter(Predicate.not(goblins::containsKey))
                .filter(Predicate.not(elves::containsKey));
    }

    @Value
    static class Turn {
        Vec2 position;
        Unit unit;
        Map<Vec2, Unit> allies;
        Map<Vec2, Unit> enemies;

        Turn move(Vec2 p) {
            allies.remove(position);
            allies.put(p, unit);
            return new Turn(p, unit, allies, enemies);
        }
    }

    /**
     * Do a round, and return whether it was completed.
     *
     * @return Whether the full round was completed.
     */
    public boolean doRound() {
        val queue = new PriorityQueue<>(Comparator.comparing(Turn::getPosition, Vec2.READING_ORDER));
        goblins.forEach((p, u) ->
                queue.add(new Turn(p, u, goblins, elves)));
        elves.forEach((p, u) ->
                queue.add(new Turn(p, u, elves, goblins)));
        while (!queue.isEmpty()) {
            if (isBattleComplete()) return false;
            doTurn(queue.remove());
        }
        return true;
    }

    public void doTurn(Turn t) {
        if (t.unit.isDead()) return;
        if (adjacentEnemies(t.position, t.enemies).findAny().isEmpty()) {
            t = performTurnMove(t);
        }
        performTurnAttack(t);
    }

    private void performTurnAttack(Turn t) {
        adjacentEnemies(t.position, t.enemies)
                .min((a, b) ->
                        Unit.WEAKEST.compare(
                                t.enemies.get(a),
                                t.enemies.get(b)
                        ))
                .ifPresent(p -> {
                    val e = t.enemies.get(p);
                    e.setHitPoints(e.getHitPoints() - ATTACK_POWER);
                    if (e.isDead()) {
                        t.enemies.remove(p);
                    }
                });
    }


    @Value
    static class Step {
        Vec2 position;
        int distance;
        Vec2 firstStep;
    }

    public Turn performTurnMove(Turn t) {
        val inRange = t.enemies
                .keySet()
                .stream()
                .flatMap(this::adjacentOpenPositions)
                .collect(Collectors.toSet());
        int distance = Integer.MAX_VALUE;
        Set<Vec2> visited = new HashSet<>();
        Queue<Step> nearest = new PriorityQueue<>(Comparator.comparing(Step::getFirstStep, Vec2.READING_ORDER));
        Queue<Step> queue = new LinkedList<>();
        adjacentOpenPositions(t.position)
                .forEach(s ->
                        queue.add(new Step(s, 1, s)));
        while (!queue.isEmpty()) {
            val s = queue.remove();
            if (!visited.add(s.position)) continue;
            if (s.distance > distance) continue; // drain...
            if (inRange.contains(s.position)) {
                if (s.distance < distance) {
                    distance = s.distance;
                    nearest.clear();
                }
                nearest.add(s);
            } else {
                adjacentOpenPositions(s.position)
                        .forEach(n ->
                                queue.add(new Step(n, s.distance + 1, s.firstStep)));
            }
        }
        if (nearest.isEmpty()) {
            // nothing's available...
            return t;
        }
        return t.move(nearest.element().firstStep);
    }

}
