package com.barneyb.aoc.aoc2022.day14

import com.barneyb.aoc.util.Slice
import com.barneyb.aoc.util.Solver
import com.barneyb.aoc.util.toInt
import com.barneyb.aoc.util.toSlice
import com.barneyb.util.HashMap
import com.barneyb.util.HashSet
import com.barneyb.util.Vec2
import kotlin.math.max
import kotlin.math.min

fun main() {
    Solver.execute(
        ::parse,
        ::sandAtRestAbyss, // 838
        ::sandAtRestFloor, // 27539
    )
}

private val sourceOfSand = Vec2(500, 0)

internal enum class Fill(var indicator: Char) {
    SAND('o'),
    ROCK('#')
}

internal class Map(rocks: HashSet<Vec2>) {
    private val sites = HashMap<Vec2, Fill>()
    private val xRange: IntRange
    private val yRange: IntRange
    val width: Int
    val height: Int

    init {
        var minx = 500
        var maxx = 500
        var maxy = 0
        for (r in rocks) {
            minx = min(minx, r.x)
            maxx = max(maxx, r.x)
            maxy = max(maxy, r.y)
            sites[r] = Fill.ROCK
        }
        xRange = minx - 1..maxx + 1
        yRange = 0..maxy
        width = xRange.last - xRange.first + 1
        height = yRange.last - yRange.first + 1
    }

    var useFloor = false
        private set

    fun addFloor() {
        useFloor = true
    }

    var sandAtRest = 0
        private set

    private fun rest(curr: Vec2) {
        sites[curr] = Fill.SAND
        sandAtRest++
    }

    /** Drop a unit of sand, and return whether it came to rest. */
    fun dropSand(): Boolean {
        var curr = sourceOfSand
        if (sites.contains(curr)) return false
        while (true) {
            val below = curr.down()
            if (sites.contains(below)) {
                val toLeft = below.left()
                if (sites.contains(toLeft)) {
                    val toRight = below.right()
                    if (sites.contains(toRight)) {
                        rest(curr)
                        return true
                    } else {
                        curr = toRight
                    }
                } else {
                    curr = toLeft
                }
            } else {
                curr = below
            }
            if (curr.y !in yRange)
                return if (useFloor) {
                    rest(curr)
                    true
                } else false
        }
    }

    override fun toString() =
        buildString {
            val rowLabelWidth = yRange.last.toString().length
            var mag = 100
            while (true) {
                append('\n')
                append(" ".repeat(rowLabelWidth + 1))
                for (x in xRange) {
                    if (x == xRange.first || x == xRange.last || x % 20 == 0)
                        append(x / mag % 10)
                    else append(' ')
                }
                if (mag == 1) break
                mag /= 10
            }
            for (y in yRange) {
                append('\n')
                append(y.toString().padStart(rowLabelWidth))
                append(' ')
                for (x in xRange) {
                    val p = Vec2(x, y)
                    when {
                        p == sourceOfSand -> append('+')
                        sites.contains(p) -> append(sites[p].indicator)
                        else -> append('.')
                    }
                }
            }
        }

}

internal fun parse(input: String) =
    input.toSlice()
        .trim()
        .lines()
        .map(::parseFormation)
        .fold(HashSet<Vec2>()) { agg, it ->
            agg.addAll(it)
            agg
        }

// 498,4 -> 498,6 -> 496,6
internal fun parseFormation(line: Slice) =
    HashSet<Vec2>().apply {
        line.split(" -> ")
            .map { it.split(",").map(Slice::toInt) }
            .map { (x, y) -> Vec2(x, y) }
            .windowed(2)
            .forEach { (a, b) ->
                val step: (Vec2) -> Vec2 =
                    if (a.x == b.x)
                        if (a.y < b.y) Vec2::south
                        else Vec2::north
                    else if (a.x < b.x) Vec2::east
                    else Vec2::west
                var curr = a
                add(curr)
                while (curr != b) {
                    curr = step(curr)
                    add(curr)
                }
            }
    }

internal fun sandAtRestAbyss(rocks: HashSet<Vec2>) =
    Map(rocks).let { map ->
        @Suppress("ControlFlowWithEmptyBody")
        while (map.dropSand()) {
        }
        map.sandAtRest
    }

internal fun sandAtRestFloor(rocks: HashSet<Vec2>) =
    Map(rocks).let { map ->
        map.addFloor()
        @Suppress("ControlFlowWithEmptyBody")
        while (map.dropSand()) {
        }
        map.sandAtRest
    }
