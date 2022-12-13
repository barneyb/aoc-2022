package com.barneyb.aoc.aoc2022.day13

import com.barneyb.aoc.util.Solver
import com.barneyb.aoc.util.toSlice
import com.barneyb.util.Stack

fun main() {
    Solver.execute(
        ::parse,
        ::partOne, // 4809
        ::decoderKey, // 22600
    )
}

internal fun parse(input: String) =
    input.toSlice()
        .trim()
        .split('\n')
        .filter(CharSequence::isNotBlank)
        .map(::parsePacket)

internal fun parsePacket(str: CharSequence): List<*> {
    val stack = Stack<MutableList<Any>>()
    var n: Int? = null
    for (c in str) {
        when (c) {
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
                if (n == null) n = 0
                else n *= 10
                n += c.digitToInt()
            }
        }
    }
    throw IllegalArgumentException("mismatched brackets?")
}

internal fun comparePackets(left: List<*>, right: List<*>): Int {
    val lItr = left.iterator()
    val rItr = right.iterator()
    while (lItr.hasNext() && rItr.hasNext()) {
        val l = lItr.next()
        val r = rItr.next()
        val c = if (l is Int) {
            if (r is List<*>) {
                comparePackets(listOf(l), r)
            } else { // must be Int
                l - r as Int
            }
        } else if (l is List<*>) {
            if (r is List<*>) {
                comparePackets(l, r)
            } else { // must be Int
                comparePackets(l, listOf(r))
            }
        } else {
            throw IllegalStateException("non-int, non-List found?")
        }
        if (c != 0) return c
    }
    return left.size - right.size
}

internal fun partOne(packets: List<List<*>>) =
    (packets.indices step 2).map { i ->
        Pair(packets[i], packets[i + 1])
    }
        .withIndex()
        .filter { (_, p) -> comparePackets(p.first, p.second) <= 0 }
        .sumOf { (i, _) -> i + 1 }

internal fun decoderKey(packets: List<List<*>>): Int {
    val one = parsePacket("[[2]]")
    val two = parsePacket("[[6]]")
    val aug = ArrayList<List<*>>(packets.size + 2)
    aug.addAll(packets)
    aug.add(one)
    aug.add(two)
    return aug.sortedWith(::comparePackets)
        .withIndex()
        .filter { (_, p) -> p === one || p === two }
        .fold(1) { r, (i, _) -> r * (i + 1) }
}
