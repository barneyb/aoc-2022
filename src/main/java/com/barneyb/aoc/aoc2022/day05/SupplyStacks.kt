package com.barneyb.aoc.aoc2022.day05

import com.barneyb.aoc.util.Slice
import com.barneyb.aoc.util.Solver
import com.barneyb.aoc.util.toSlice
import com.barneyb.util.Stack

fun main() {
    Solver.execute(
        ::parse,
        ::shuffle,
    )
}

internal typealias Stacks = Array<Stack<Char>>
internal typealias Instructions = List<Ins>

internal data class Ins(val n: Int, val from: Int, val to: Int)

internal data class ParseResult(
    val stacks: Stacks,
    val instructions: Instructions
)

internal fun parse(input: String) =
    input.toSlice().let { raw ->
        raw.indexOf("\n\n").let { idxSplit ->
            val stacks = raw.subSequence(0, idxSplit)
            val instructions = raw.subSequence(idxSplit, raw.length)
            ParseResult(parseStacks(stacks), parseInstructions(instructions))
        }
    }

internal fun parseStacks(input: Slice): Stacks {
    val lines = input
        .lines()
        .filter { it.isNotBlank() }
    val numbers = lines.last()
    val stacks = Array<Stack<Char>>(
        numbers
            .last(Char::isDigit)
            .digitToInt()
    ) { Stack() }
    numbers.withIndex()
        .filter { (_, c) -> c.isDigit() }
        .forEach { (cIdx, c) ->
            stacks[c.digitToInt() - 1].apply {
                for (l in lines.size - 2 downTo 0) {
                    val v = lines[l][cIdx]
                    if (v.isWhitespace()) break // reached top
                    push(v)
                }
            }
        }
    return stacks
}

// move 2 from 2 to 1
internal fun parseInstructions(input: Slice): Instructions {
    return input.trim()
        .lines()
        .map { it.split(' ') }
        .map { Ins(it[1].toInt(), it[3].toInt() - 1, it[5].toInt() - 1) }
}

internal fun shuffle(parse: ParseResult): CharSequence {
    parse.instructions.forEach { ins ->
        repeat(ins.n) { i ->
            parse.stacks[ins.to].push(parse.stacks[ins.from].pop())
        }
    }
    return buildString {
        parse.stacks.forEach { append(it.peek()) }
    }
}
