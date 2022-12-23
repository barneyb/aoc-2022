package com.barneyb.aoc.aoc2022.day23

import com.barneyb.aoc.util.Solver
import com.barneyb.aoc.util.toSlice
import com.barneyb.util.HashSet
import com.barneyb.util.Vec2

fun main() {
    Solver.execute(
        ::parse,
        ::openSpacesAfterTenRounds, // 3,684
        ::firstNoOpRound, // 862
    )
}

internal fun parse(input: String) =
    HashSet<Vec2>().apply {
        input.toSlice()
            .trim()
            .lines()
            .forEachIndexed { y, line ->
                line.forEachIndexed { x, c ->
                    if (c == '#')
                        add(Vec2(x, y))
                }
            }
    }

internal fun openSpacesAfterTenRounds(elves: HashSet<Vec2>): Int {
    var game = Game(elves)
    repeat(10) { game = game.tick() }
    return game.emptySpaceCount
}

internal fun firstNoOpRound(elves: HashSet<Vec2>): Int {
    var game = Game(elves)
    while (true) {
        val next = game.tickWithMotionCount()
        game = next.first
        if (next.second == 0)
            break
    }
    return game.rounds
}
