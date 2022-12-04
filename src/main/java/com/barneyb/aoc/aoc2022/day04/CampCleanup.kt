package com.barneyb.aoc.aoc2022.day04

import com.barneyb.aoc.util.Solver

fun main() {
    Solver.execute(
        ::parse,
        ::countContained
    )
}

typealias Assignment = IntRange
typealias PairsOfAssignments = List<Pair<Assignment, Assignment>>

private fun IntRange.fullyContains(other: IntRange): Boolean =
    other.first >= first && other.last <= last

fun countContained(pairs: PairsOfAssignments) =
    pairs.count { (a, b) ->
        a.fullyContains(b) || b.fullyContains(a)
    }

internal fun parse(input: String) =
    input.trim().lines().map(::parsePair)

internal fun parsePair(str: String) =
    //2-3,4-5
    str.indexOf(',').let { idx ->
        Pair(
            parseAssignment(str.substring(0, idx)),
            parseAssignment(str.substring(idx + 1, str.length))
        )
    }

internal fun parseAssignment(str: String) =
    //2-3
    str.indexOf('-').let { idx ->
        Assignment(
            str.substring(0, idx).toInt(),
            str.substring(idx + 1, str.length).toInt()
        )
    }
