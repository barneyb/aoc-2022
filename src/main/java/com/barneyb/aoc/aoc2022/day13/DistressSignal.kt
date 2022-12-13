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
                val c = l - r as Int
                if (c != 0) return c
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
