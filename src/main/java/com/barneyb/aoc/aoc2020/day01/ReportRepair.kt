package com.barneyb.aoc.aoc2020.day01

import com.barneyb.aoc.util.Input
import com.barneyb.aoc.util.Solver
import com.barneyb.aoc.util.toLong
import com.barneyb.util.Timing

fun main() {
    val input = Input.forProblem(::main)
    val parsed = Timing.benchmark(100) { parse(input) }.result
    Timing.benchmark(1000) { productOfTwo(parsed) }
    Timing.benchmark(1000) { productOfThree(parsed) }
    Solver.execute(
        ::parse,
        ::productOfTwo,
        ::productOfThree,
    )
}

internal fun parse(input: String) =
    input.trim()
        .lines()
        .map(CharSequence::toLong)
        .sorted()

internal fun productOfTwo(sortedExpenses: List<Long>) =
    complement(sortedExpenses, 2020).let { n ->
        n!! * (2020 - n)
    }

private fun complement(sortedExpenses: List<Long>, sum: Long) =
    sortedExpenses.find { n ->
        n < sum && sortedExpenses.binarySearch(sum - n) > 0
    }

internal fun productOfThree(sortedExpenses: List<Long>) =
    sortedExpenses.firstNotNullOfOrNull { a ->
        complement(sortedExpenses, 2020 - a)?.let { b ->
            Pair(a, b)
        }
    }.let { pair ->
        pair!!.first * pair.second * (2020 - pair.first - pair.second)
    }
