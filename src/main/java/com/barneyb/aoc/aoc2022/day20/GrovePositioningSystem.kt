package com.barneyb.aoc.aoc2022.day20

import com.barneyb.aoc.util.Slice
import com.barneyb.aoc.util.Solver
import com.barneyb.aoc.util.toInt
import com.barneyb.aoc.util.toSlice

fun main() {
    Solver.execute(
        ::parse,
        ::sumOfCoords, // 14888
    )
}

internal fun parse(input: String) =
    input.toSlice()
        .trim()
        .lines()
        .map(Slice::toInt)

private class Node(
    val value: Int,
    var _prev: Node? = null,
    var _next: Node? = null,
) {
    var prev
        get() = _prev!!
        set(value) {
            _prev = value
        }

    var next
        get() = _next!!
        set(value) {
            _next = value
        }

    override fun toString(): String {
        return "Node(value=$value)"
    }

}

internal fun sumOfCoords(list: List<Int>): Int {
    val nodes = Array(list.size) { Node(list[it]) }
    nodes.first().prev = nodes.last()
    nodes.last().next = nodes.first()
    for (i in 1 until nodes.size) {
        nodes[i].prev = nodes[i - 1]
        nodes[i - 1].next = nodes[i]
    }
    var zero: Node? = null
    for (n in nodes) {
        if (n.value == 0) {
            zero = n
            continue
        }
        // pull it out of the ring
        n.prev.next = n.next
        n.next.prev = n.prev
        val offset = n.value
        var curr = n
        if (offset < 0) {
            repeat(-offset + 1) { curr = curr.prev }
        } else {
            repeat(offset) { curr = curr.next }
        }
        n.prev = curr
        n.next = curr.next
        curr.next = n
        n.next.prev = n
    }
    var curr = zero!!
    repeat(1000) { curr = curr.next }
    val one = curr.value
    repeat(1000) { curr = curr.next }
    val two = curr.value
    repeat(1000) { curr = curr.next }
    val three = curr.value
    return one + two + three
}
