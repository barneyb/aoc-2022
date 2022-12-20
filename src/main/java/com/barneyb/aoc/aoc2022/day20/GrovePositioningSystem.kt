package com.barneyb.aoc.aoc2022.day20

import com.barneyb.aoc.util.Slice
import com.barneyb.aoc.util.Solver
import com.barneyb.aoc.util.toLong
import com.barneyb.aoc.util.toSlice

fun main() {
    Solver.execute(
        ::parse,
        ::sumOfCoords, // 14888
        ::fullDecryption, // 3760092545849
    )
}

internal const val DECRYPTION_KEY: Long = 811589153

internal const val ROUNDS = 10

internal fun parse(input: String) =
    input.toSlice()
        .trim()
        .lines()
        .map(Slice::toLong)

private class Node(
    val value: Long,
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

internal fun sumOfCoords(
    list: List<Long>,
    key: Long = 1,
    rounds: Int = 1
): Long {
    val nodes = Array(list.size) { Node(list[it] * key) }
//    println("Initial arrangement:")
//    println(nodes.map(Node::value).joinToString(", "))
    nodes.first().prev = nodes.last()
    nodes.last().next = nodes.first()
    var zero: Node? = if (nodes[0].value == 0L)
        nodes[0]
    else null
    for (i in 1 until nodes.size) {
        if (nodes[i].value == 0L) zero = nodes[i]
        nodes[i].prev = nodes[i - 1]
        nodes[i - 1].next = nodes[i]
    }
//    println(draw(zero!!))
    val countOfOthers = list.size - 1
    val mid = list.size / 2
    val negMid = -mid
    repeat(rounds) {
        for (n in nodes) {
            var offset = (n.value % countOfOthers).toInt()
            if (offset == 0) continue
            else if (offset < negMid) offset += countOfOthers
            else if (offset > mid) offset -= countOfOthers
            var curr = n
            if (offset < 0)
                repeat(list.size - offset + 1) { curr = curr.prev }
            else
                repeat(offset) { curr = curr.next }
            // pull it out of the ring
            n.prev.next = n.next
            n.next.prev = n.prev
            // put it back after curr
            n.prev = curr
            n.next = curr.next
            curr.next = n
            n.next.prev = n
//            println(draw(zero))
        }
//        println("\nAfter ${it + 1} round${if (it == 0) "" else "s"} of mixing:")
//        println(draw(zero))
    }
    var curr = zero!!
    var sum = 0L
    repeat(3) {
        repeat(1000) { curr = curr.next }
        sum += curr.value
    }
    return sum
}

@Suppress("unused")
private fun draw(zero: Node) =
    generateSequence(zero) {
        if (it.next == zero) null else it.next
    }.map(Node::value)
        .joinToString(", ")

internal fun fullDecryption(list: List<Long>) =
    sumOfCoords(list, DECRYPTION_KEY, ROUNDS)
