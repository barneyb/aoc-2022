package com.barneyb.aoc.aoc2016.day17

import com.barneyb.aoc.util.Solver
import java.security.MessageDigest
import java.util.*
import kotlin.experimental.and

const val WIDTH = 4
const val HEIGHT = 4

fun main() {
    Solver.execute(
        { it.trim() },
        ::shortestPath,
        { longestPath(it).length })
}

internal data class State(
    val x: Int,
    val y: Int,
    val passcode: String,
    val path: String = ""
) {

    companion object {
        fun start(passcode: String) =
            State(1, 1, passcode)
    }

    fun up() =
        State(x, y - 1, passcode, path + "U")

    fun down() =
        State(x, y + 1, passcode, path + "D")

    fun left() =
        State(x - 1, y, passcode, path + "L")

    fun right() =
        State(x + 1, y, passcode, path + "R")
}

fun shortestPath(passcode: String): String {
    val queue: Deque<State> = LinkedList()
    queue.add(State.start(passcode))
    while (queue.isNotEmpty()) {
        val curr = queue.remove()!!
        if (curr.x == WIDTH && curr.y == HEIGHT) {
            return curr.path
        }
        queue.addAll(adjacent(curr))
    }
    throw IllegalArgumentException("Passcode '$passcode' doesn't offer a path to the vault")
}

fun longestPath(passcode: String): String {
    val queue: Deque<State> = LinkedList()
    queue.add(State.start(passcode))
    var longest = ""
    while (queue.isNotEmpty()) {
        val curr = queue.remove()!!
        if (curr.x == WIDTH && curr.y == HEIGHT) {
            if (curr.path.length > longest.length) {
                longest = curr.path
            }
        } else {
            queue.addAll(adjacent(curr))
        }
    }
    if (longest.isEmpty()) {
        throw IllegalArgumentException("Passcode '$passcode' doesn't offer a path to the vault")
    }
    return longest
}

internal fun adjacent(
    curr: State,
): Collection<State> {
    val result = mutableListOf<State>()
    val doors = doors(curr.passcode, curr.path)
    //@formatter:off
    if (doors[0] && curr.y > 1     ) result.add(curr.up())
    if (doors[1] && curr.y < HEIGHT) result.add(curr.down())
    if (doors[2] && curr.x > 1     ) result.add(curr.left())
    if (doors[3] && curr.x < WIDTH ) result.add(curr.right())
    //@formatter:on
    return result
}

internal fun doors(passcode: String, path: String = ""): BooleanArray {
    val digest = MessageDigest.getInstance("MD5")
    digest.update(passcode.toByteArray())
    digest.update(path.toByteArray())
    val bytes = digest.digest()
    return booleanArrayOf(
        //@formatter:off
        bytes[0].rotateRight(4) and 0xf >= 0xb,
        bytes[0]                         and 0xf >= 0xb,
        bytes[1].rotateRight(4) and 0xf >= 0xb,
        bytes[1]                         and 0xf >= 0xb,
        //@formatter:on
    )
}
