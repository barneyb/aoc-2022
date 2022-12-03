package com.barneyb.aoc.aoc2022.day03

import com.barneyb.aoc.util.Solver

fun main() {
    Solver.execute(
        ::parse,
        ::partOne,
        ::partTwo,
    )
}

internal data class Rucksack(
    val left: Set<Int>,
    val right: Set<Int>,
) {
    val intersection = left.intersect(right).first()

    fun contains(n: Int) =
        left.contains(n) || right.contains(n)
}

internal fun parse(input: String) =
    input.trim().lines().map { content ->
        assert(content.length % 2 == 0) {
            "odd number (${content.length}) of rucksack items: '$content'?!"
        }
        val priorities = buildList(content.length) {
            for (c in content) {
                add(c - (if (c < 'a') 'A' - 26 else 'a') + 1)
            }
        }
        (priorities.size / 2).let { mid ->
            Rucksack(
                HashSet(priorities.subList(0, mid)),
                HashSet(priorities.subList(mid, priorities.size)),
            )
        }
    }

internal fun partOne(sacks: Collection<Rucksack>) =
    sacks.sumOf(Rucksack::intersection)

internal fun partTwo(sacks: Collection<Rucksack>): Int {
    assert(sacks.size % 3 == 0) {
        "Non-multiple of three (${sacks.size}) number of elves?!"
    }
    return sacks.windowed(3, 3).sumOf(::badge)
}

internal fun badge(sacks: List<Rucksack>): Int {
    val first = sacks.first()
    val rest = sacks.subList(1, sacks.size)
    for (n in (first.left + first.right)) {
        if (rest.all { it.contains(n) }) {
            return n
        }
    }
    throw IllegalArgumentException("No shared item found?!")
}
