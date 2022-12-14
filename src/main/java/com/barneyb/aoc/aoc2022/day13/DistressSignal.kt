package com.barneyb.aoc.aoc2022.day13

import com.barneyb.aoc.util.Solver
import com.barneyb.aoc.util.toSlice
import com.barneyb.util.Stack

fun main() {
    Solver.benchmark(
        ::parse,
        ::partOne, // 4809
        ::decoderKey, // 22600
    )
}

typealias Packet = List<*> // List<Int | Packet>

internal fun parse(input: String) =
    input.toSlice()
        .trim()
        .split('\n')
        .filter(CharSequence::isNotBlank)
        .map(::parsePacket)

internal fun parsePacket(str: CharSequence): Packet {
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

internal fun comparePackets(left: Packet, right: Packet): Int {
    val lItr = left.iterator()
    val rItr = right.iterator()
    while (lItr.hasNext() && rItr.hasNext()) {
        val l = lItr.next()
        val r = rItr.next()
        val c = if (l is Int) {
            if (r is Packet) {
                comparePackets(listOf(l), r)
            } else { // must be Int
                l - r as Int
            }
        } else if (l is Packet) {
            if (r is Packet) {
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

internal fun partOne(packets: List<Packet>) =
    (packets.indices step 2).map { i ->
        Pair(packets[i], packets[i + 1])
    }
        .withIndex()
        .filter { (_, p) -> comparePackets(p.first, p.second) <= 0 }
        .sumOf { (i, _) -> i + 1 }

internal fun decoderKey(packets: List<Packet>): Int {
    val one = parsePacket("[[2]]")
    val two = parsePacket("[[6]]")
    val aug = ArrayList<Packet>(packets.size + 2)
    aug.addAll(packets)
    aug.add(one)
    aug.add(two)
    return aug.sortedWith(::comparePackets)
        .withIndex()
        .filter { (_, p) -> p === one || p === two }
        .fold(1) { r, (i, _) -> r * (i + 1) }
}
