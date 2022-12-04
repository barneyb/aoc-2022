package com.barneyb.aoc.aoc2022.day04

import com.barneyb.aoc.util.*

fun main() {
    Solver.execute(
        ::parse,
        ::countContained,
        ::countOverlapping
    )
}

typealias Assignment = IntRange
typealias PairsOfAssignments = List<Pair<Assignment, Assignment>>

fun countContained(pairs: PairsOfAssignments) =
    pairs.count { (a, b) ->
        a.fullyContains(b) || b.fullyContains(a)
    }

fun countOverlapping(pairs: PairsOfAssignments) =
    pairs.count { (a, b) ->
        a.overlaps(b)
    }

internal fun parse(input: String) =
    input.toSlice()
        .trim()
        .lines()
        .map(::parsePair)

internal fun parsePair(str: CharSequence) =
    //2-3,4-5
    str.indexOf(',').let { idx ->
        Pair(
            parseAssignment(str.subSequence(0, idx)),
            parseAssignment(str.subSequence(idx + 1, str.length))
        )
    }

internal fun parseAssignment(str: CharSequence) =
    //2-3
    str.indexOf('-').let { idx ->
        Assignment(
            str.subSequence(0, idx).toInt(),
            str.subSequence(idx + 1, str.length).toInt()
        )
    }
