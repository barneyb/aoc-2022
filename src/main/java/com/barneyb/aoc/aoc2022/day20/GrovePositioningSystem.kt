package com.barneyb.aoc.aoc2022.day20

import com.barneyb.aoc.util.Slice
import com.barneyb.aoc.util.Solver
import com.barneyb.aoc.util.toLong
import com.barneyb.aoc.util.toSlice

fun main() {
    Solver.execute(
        ::parse,
        ::sumOfCoords_ptr, // 14888
        ::fullDecryption_ptr, // 3760092545849
    )
    Solver.execute(
        ::parse,
        ::sumOfCoords_arr, // 14888
        ::fullDecryption_arr, // 3760092545849
    )
}

internal const val DECRYPTION_KEY: Long = 811589153

internal const val ROUNDS = 10

internal fun parse(input: String) =
    input.toSlice()
        .trim()
        .lines()
        .map(Slice::toLong)

internal fun sumOfCoords_arr(
    list: List<Long>,
    key: Long = 1,
    rounds: Int = 1
): Long {
    val arr = ArrayList<Pair<Int, Long>>(list.size)
    arr.addAll(list.mapIndexed { i, n ->
        Pair(i, n * key)
    })
    repeat(rounds) { _ ->
        for (n in 0 until arr.size) {
            val src = arr.indexOfFirst { (i, _) -> i == n }
            val it = arr.removeAt(src)
            val tgt = (src + it.second) % arr.size
            arr.add(tgt.toInt() + if (tgt < 0) arr.size else 0, it)
        }
    }
    val idx = arr.indexOfFirst { (_, n) -> n == 0L }
    return listOf(1000, 2000, 3000).sumOf {
        arr[(idx + it) % arr.size].second
    }
}

internal fun fullDecryption_arr(list: List<Long>) =
    sumOfCoords_arr(list, DECRYPTION_KEY, ROUNDS)

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

internal fun sumOfCoords_ptr(
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

internal fun fullDecryption_ptr(list: List<Long>) =
    sumOfCoords_ptr(list, DECRYPTION_KEY, ROUNDS)
