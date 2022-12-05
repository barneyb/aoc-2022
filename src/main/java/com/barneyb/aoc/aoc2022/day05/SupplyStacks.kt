package com.barneyb.aoc.aoc2022.day05

import com.barneyb.aoc.util.*
import com.barneyb.util.Stack
import com.barneyb.util.Timing

fun main() {
    val input = Input.forProblem(::crateMover9000)
    val parsed = Timing.benchmark(1000) { parse(input) }.result
    Timing.benchmark(1000) { crateMover9000(parsed) }
    Timing.benchmark(1000) { crateMover9001(parsed) }
    Solver.execute(
        ::parse,
        ::crateMover9000,
        ::crateMover9001,
    )
}

internal typealias Stacks = Array<Stack<Char>>
internal typealias Instructions = List<Ins>

internal data class Ins(val n: Int, val from: Int, val to: Int)

internal class ParseResult(
    private val stacks: Stacks,
    val instructions: Instructions,
) {
    fun freshStacks() =
        Array(stacks.size) { stacks[it].clone() }
}

internal fun parse(input: String) =
    input.toSlice().let { raw ->
        raw.indexOf("\n\n").let { idxSplit ->
            ParseResult(
                parseStacks(raw[0, idxSplit]),
                parseInstructions(raw[idxSplit, raw.length])
            )
        }
    }

internal fun parseStacks(input: Slice) =
    input.lines()
        .filter(Slice::isNotBlank)
        .let { lines ->
            lines.last()
                .withIndex()
                .filter { (_, c) -> c.isDigit() }
                .map { (cIdx, _) ->
                    Stack<Char>().apply {
                        for (l in lines.size - 2 downTo 0) {
                            val v = lines[l][cIdx]
                            if (v.isWhitespace()) break // reached top
                            push(v)
                        }
                    }
                }
                .toTypedArray()
        }

// move 2 from 2 to 1
internal fun parseInstructions(input: Slice) =
    input.trim()
        .lines()
        .map { it.split(' ') }
        .map { Ins(it[1].toInt(), it[3].toInt() - 1, it[5].toInt() - 1) }

internal fun crateMover9000(parse: ParseResult) =
    parse.freshStacks().apply {
        parse.instructions.forEach { ins ->
            repeat(ins.n) {
                this[ins.to].push(this[ins.from].pop())
            }
        }
    }.topsAsString()

private fun Stacks.topsAsString() =
    buildString {
        this@topsAsString.forEach { append(it.peek()) }
    }

internal fun crateMover9001(parse: ParseResult) =
    parse.freshStacks().apply {
        val temp = Stack<Char>()
        parse.instructions.forEach { ins ->
            repeat(ins.n) {
                temp.push(this[ins.from].pop())
            }
            while (temp.isNotEmpty()) {
                this[ins.to].push(temp.pop())
            }
        }
    }.topsAsString()
