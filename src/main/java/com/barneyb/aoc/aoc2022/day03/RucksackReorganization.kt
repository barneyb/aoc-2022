package com.barneyb.aoc.aoc2022.day03

import com.barneyb.aoc.util.Solver

fun main() {
    Solver.execute(
        ::parse,
        ::partOne
    )
}

internal data class Rucksack(
    val left: List<Int>,
    val right: List<Int>,
) {
    val intersection = left.find {
        right.binarySearch(it) >= 0
    }!!
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
                priorities.subList(0, mid).sorted(),
                priorities.subList(mid, priorities.size).sorted(),
            )
        }
    }

internal fun partOne(sacks: Collection<Rucksack>) =
    sacks.sumOf(Rucksack::intersection)
