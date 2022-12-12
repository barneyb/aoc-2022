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

private data class Step(val pos: Vec2, val height: Char, val steps: Int)

internal fun parse(input: String) =
    input.toSlice().trim().lines().let { grid ->
        var start: Vec2? = null
        var end: Vec2? = null
        for ((y, l) in grid.withIndex()) {
            var x = l.indexOf('S')
            if (x >= 0) start = Vec2(x, y)
            x = l.indexOf('E')
            if (x >= 0) end = Vec2(x, y)
            if (start != null && end != null) break
        }
        if (start == null) throw IllegalArgumentException("didn't find start?!")
        if (end == null) throw IllegalArgumentException("didn't find end?!")
        val visited = HashMap<Vec2, Int>()
        val queue = Queue<Step>()
        queue.enqueue(Step(start, 'a', 0))
        while (queue.isNotEmpty()) {
            val (pos, height, steps) = queue.dequeue()
            if (visited.contains(pos) && steps >= visited[pos]!!) {
                continue
            }
            visited[pos] = steps
            for (d in Dir.values()) {
                val next = pos.move(d)
                if (!grid.contains(next)) continue
                val h = grid[next]
                if (h == 'E' && height >= 'y') return@let steps + 1
                if (h - height <= 1) queue.enqueue(Step(next, h, steps + 1))
            }
        }
    }
