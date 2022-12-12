package com.barneyb.aoc.aoc2022.day12

import com.barneyb.aoc.util.Slice
import com.barneyb.aoc.util.Solver
import com.barneyb.aoc.util.toSlice
import com.barneyb.util.Dir
import com.barneyb.util.Queue
import com.barneyb.util.Vec2

fun main() {
    Solver.execute(
        ::parse,
        ::shortestPath,
        ::shortestPathFromBestStart,
    )
}

typealias Grid = List<Slice>

val Grid.height
    get() = this.size

val Grid.width
    get() = this[0].length

operator fun Grid.get(p: Vec2) =
    if (contains(p))
        this[p.y][p.x]
    else
        throw IllegalArgumentException("$p isn't a valid position")

fun Grid.contains(p: Vec2) =
    p.x in 0 until width && p.y in 0 until height

private data class Step(val pos: Vec2, val elev: Char, val steps: Int)

internal fun parse(input: String) =
    input.toSlice().trim().lines()

internal fun Grid.allAt(elev: Char) =
    sequence {
        for ((y, l) in withIndex()) {
            var x = -1
            while (true) {
                x = l.indexOf(elev, x + 1)
                if (x < 0) break
                yield(Vec2(x, y))
            }
        }
    }

internal fun shortestPath(grid: Grid): Int {
    val start = grid.allAt('S').first()
    val end = grid.allAt('E').first()
    val visited = HashMap<Vec2, Int>()
    val queue = Queue<Step>()
    queue.enqueue(Step(start, 'a', 0))
    while (queue.isNotEmpty()) {
        val (pos, elev, steps) = queue.dequeue()
        if (visited.contains(pos) && steps >= visited[pos]!!) {
            continue
        }
        visited[pos] = steps
        for (d in Dir.values()) {
            val next = pos.move(d)
            if (!grid.contains(next)) continue
            val e = grid[next]
            if (e == 'E' && elev >= 'y') return steps + 1
            if (e - elev <= 1) queue.enqueue(Step(next, e, steps + 1))
        }
    }
    throw IllegalArgumentException("No path from $start to $end found?!")
}

fun shortestPathFromBestStart(grid: Grid): Int {
    val end = grid.allAt('E').first()
    val visited = HashMap<Vec2, Int>()
    val queue = Queue<Step>()
    queue.enqueue(Step(end, 'z', 0))
    while (queue.isNotEmpty()) {
        val (pos, elev, steps) = queue.dequeue()
        if (visited.contains(pos) && steps >= visited[pos]!!) {
            continue
        }
        visited[pos] = steps
        for (d in Dir.values()) {
            val next = pos.move(d)
            if (!grid.contains(next)) continue
            val e = grid[next]
            if (elev <= 'b') return steps + 1
            if (elev - e <= 1) queue.enqueue(Step(next, e, steps + 1))
        }
    }
    throw IllegalArgumentException("No path from elevation 'a' to $end found?!")
}
