package com.barneyb.aoc.aoc2022.day12

import com.barneyb.aoc.util.Slice
import com.barneyb.aoc.util.Solver
import com.barneyb.aoc.util.toSlice
import com.barneyb.util.Dir
import com.barneyb.util.HashSet
import com.barneyb.util.Queue
import com.barneyb.util.Vec2

fun main() {
    Solver.benchmark(
        ::parse,
        ::shortestPath, // 352
        ::shortestPathFromBestStart, // 345
    )
}

private typealias Elevation = Char
private typealias Map = List<Slice>

private val Map.height
    get() = this.size

private val Map.width
    get() = this[0].length

private fun Map.elevationAt(p: Vec2): Elevation =
    if (contains(p))
        when (val c = this[p.y][p.x]) {
            'S' -> 'a'
            'E' -> 'z'
            else -> c
        }
    else
        throw IllegalArgumentException("$p isn't a valid position")

private fun Map.contains(p: Vec2) =
    p.x in 0 until width && p.y in 0 until height

private fun Map.findMarker(marker: Char): Vec2 {
    for ((y, l) in withIndex()) {
        val x = l.indexOf(marker)
        if (x >= 0) return Vec2(x, y)
    }
    throw IllegalArgumentException("No position with marker '$marker' found")
}

private fun Map.stepsUntil(
    start: Vec2,
    testStep: (Elevation, Elevation) -> Boolean,
    testGoal: (Vec2) -> Boolean
): Int {
    val visited = HashSet<Vec2>()
    val queue = Queue<Step>()
    queue.enqueue(Step(start, elevationAt(start), 0))
    while (queue.isNotEmpty()) {
        val (pos, elev, steps) = queue.dequeue()
        if (visited.contains(pos)) {
            continue
        }
        if (testGoal(pos)) return steps
        visited.add(pos)
        for (d in Dir.values()) {
            val next = pos.move(d)
            if (!contains(next)) continue
            val e = elevationAt(next)
            if (testStep(elev, e)) {
                queue.enqueue(Step(next, e, steps + 1))
            }
        }
    }
    throw IllegalArgumentException("No path from $start found?!")
}

private data class Step(val pos: Vec2, val elev: Elevation, val steps: Int)

internal fun parse(input: String) =
    input.toSlice().trim().lines()

internal fun shortestPath(map: Map) =
    map.findMarker('E').let { goal ->
        map.stepsUntil(
            map.findMarker('S'),
            { curr, next -> next - curr <= 1 },
            goal::equals,
        )
    }

fun shortestPathFromBestStart(map: Map): Int {
    return map.stepsUntil(
        map.findMarker('E'),
        { curr, next -> curr - next <= 1 },
        { pos -> map.elevationAt(pos) == 'a' },
    )
}
