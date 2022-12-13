package com.barneyb.aoc.aoc2022.day13

import com.barneyb.aoc.util.Solver
import com.barneyb.aoc.util.toSlice
import com.barneyb.util.Stack

fun main() {
    Solver.execute(
        ::parse,
        ::partOne, // 4809
    )
}

internal fun parse(input: String) =
    input.toSlice()
        .trim()
        .split('\n')
        .filter(CharSequence::isNotBlank)
        .map(::parseList)

internal fun parseList(str: CharSequence): List<*> {
    var i = 0
    val stack = Stack<MutableList<Any>>()
    var n: Int? = null
    while (i < str.length) {
        when (val c = str[i]) {
            '[' -> {
                stack.push(mutableListOf())
            }

            ']' -> {
                val l = stack.pop()
                if (n != null) {
                    l.add(n)
                    n = null
                }
                if (stack.isEmpty()) return l
                else stack.peek().add(l)
            }

            ',' -> {
                if (n != null) {
                    stack.peek().add(n)
                    n = null
                }
            }

            else -> {
                if (n == null) {
                    n = 0
                } else {
                    n *= 10
                }
                n += c.digitToInt()
            }
        }
        i++
    }
    throw IllegalArgumentException("mismatched brackets?")
}

internal fun comparePackets(left: List<*>, right: List<*>): Int {
    val lItr = left.iterator()
    val rItr = right.iterator()
    while (lItr.hasNext() && rItr.hasNext()) {
        val l = lItr.next()
        val r = rItr.next()
        if (l is Int) {
            if (r is List<*>) {
                val c = comparePackets(listOf(l), r)
                if (c != 0) return c
            } else { // must be Int
                if (l < r as Int) return -1
                if (l > r) return 1
            }
        } else if (l is List<*>) {
            if (r is List<*>) {
                val c = comparePackets(l, r)
                if (c != 0) return c
            } else { // must be Int
                val c = comparePackets(l, listOf(r))
                if (c != 0) return c
            }
        }
    }
    if (lItr.hasNext()) return 1
    return if (rItr.hasNext()) -1 else 0
}

internal fun partOne(signals: List<List<*>>) =
    (signals.indices step 2).map { i ->
        Pair(signals[i], signals[i + 1])
    }
        .withIndex()
        .filter { (_, p) ->
            comparePackets(p.first, p.second) <= 0
        }
        .sumOf { (i, _) ->
            i + 1
        }
