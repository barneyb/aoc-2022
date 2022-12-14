package com.barneyb.aoc.aoc2022.day14

import com.barneyb.aoc.util.Slice
import com.barneyb.aoc.util.Solver
import com.barneyb.aoc.util.toInt
import com.barneyb.aoc.util.toSlice
import com.barneyb.util.HashMap
import com.barneyb.util.HashSet
import com.barneyb.util.Stack
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
    ROCK('#'),
    EDGE('|')
}

internal class Map(rocks: HashSet<Vec2>) {
    private val sites = HashMap<Vec2, Fill>()
    private val xRange: IntRange
    private val height: Int

    init {
        var minx = sourceOfSand.x
        var maxx = minx
        var maxy = sourceOfSand.y
        for (r in rocks) {
            minx = min(minx, r.x)
            maxx = max(maxx, r.x)
            maxy = max(maxy, r.y)
            sites[r] = Fill.ROCK
        }
        xRange = minx - 2..maxx + 2
        height = maxy + 1
    }

    private var useFloor = false

    fun addFloor() {
        useFloor = true
        var left = Vec2(xRange.first, sourceOfSand.y)
        var right = Vec2(xRange.last, sourceOfSand.y)
        while (left.y <= height) {
            sites[left] = Fill.EDGE
            sites[right] = Fill.EDGE
            left = left.down()
            right = right.down()
        }
    }

    private fun areaOutsideCol(x: Int): Int {
        var curr = Vec2(x, 0)
        while (curr.y < height && !sites.contains(curr))
            curr = curr.down()
        return if (curr.y >= height) 0
        else (height - curr.y).let { it * (it + 1) / 2 }
    }

    private var _sandAtRest: Int = 0

    val sandAtRest: Int
        get() {
            if (!useFloor) return _sandAtRest
            val left = areaOutsideCol(xRange.first + 1)
            val right = areaOutsideCol(xRange.last - 1)
            return _sandAtRest + left + right
        }


    private fun rest(site: Vec2) {
        sites[site] = Fill.SAND
        _sandAtRest++
    }

    private val resumeFrom = Stack<Vec2>()

    /** Drop a unit of sand, and return whether it came to rest. */
    fun dropSand(): Boolean {
        if (sites.contains(sourceOfSand)) return false
        var curr = if (resumeFrom.isEmpty()) sourceOfSand
        else resumeFrom.pop()
        while (true) {
            val below = curr.down()
            curr = if (sites.contains(below)) {
                resumeFrom.push(curr)
                val toLeft = below.left()
                if (sites.contains(toLeft)) {
                    val toRight = below.right()
                    if (sites.contains(toRight)) {
                        resumeFrom.pop()
                        rest(curr)
                        return true
                    } else {
                        toRight
                    }
                } else {
                    toLeft
                }
            } else {
                below
            }
            if (curr.y >= height)
                return if (useFloor) {
                    rest(curr)
                    true
                } else false
        }
    }

    override fun toString() =
        buildString {
            val rowLabelWidth = height.toString().length
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
            for (y in 0..height) {
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
