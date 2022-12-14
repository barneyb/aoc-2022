package com.barneyb.aoc.aoc2022.day08

import com.barneyb.aoc.util.Solver
import com.barneyb.aoc.util.toSlice
import com.barneyb.util.Vec2
import kotlin.math.max

fun main() {
    Solver.execute(
        ::parse,
        ::visibleTreeCount,
        ::bestScenicScore,
    )
}

private const val MAX_HEIGHT = 9

internal typealias Grid = List<List<Int>>

internal operator fun Grid.get(p: Vec2) =
    get(p.y)[p.x]

internal val Grid.height
    get() = size

internal val Grid.width
    get() = get(0).size

internal fun Grid.contains(p: Vec2) =
    p.y in 0 until height && p.x in 0 until width

internal fun parse(input: String) =
    input.toSlice()
        .trim()
        .lines()
        .map {
            it.map<Int>(Char::digitToInt)
        }

internal fun visibleTreeCount(grid: Grid): Int {
    val visible = HashSet<Vec2>()
    fun scan(start: Vec2, step: (Vec2) -> Vec2) {
        visible.add(start)
        var curr = start
        var tallest = grid[curr]
        while (true) {
            curr = step(curr)
            if (!grid.contains(curr)) break
            val h = grid[curr]
            if (h > tallest) {
                visible.add(curr)
                tallest = h
                if (tallest == MAX_HEIGHT) break
            }
        }
    }
    for (x in 0 until grid.width) {
        scan(Vec2(x, 0), Vec2::south)
        scan(Vec2(x, grid.height - 1), Vec2::north)
    }
    for (y in 0 until grid.height) {
        scan(Vec2(0, y), Vec2::east)
        scan(Vec2(grid.width - 1, y), Vec2::west)
    }
    return visible.size
}

internal fun scenicScore(grid: Grid, pos: Vec2): Int {
    fun scan(step: (Vec2) -> Vec2): Int {
        var curr = pos
        var count = 0
        val max = grid[curr]
        var tallest = Int.MIN_VALUE
        while (true) {
            curr = step(curr)
            if (!grid.contains(curr)) break
            count += 1
            val h = grid[curr]
            if (h > tallest) {
                tallest = h
                if (h >= max) break
            }
        }
        return count
    }
    return scan(Vec2::north) *
            scan(Vec2::south) *
            scan(Vec2::east) *
            scan(Vec2::west)
}

internal fun bestScenicScore(grid: Grid): Int {
    var best = Int.MIN_VALUE
    // no need to check the edges, since they're guaranteed zero
    for (x in 1 until grid.width - 1) {
        for (y in 1 until grid.height - 1) {
            best = max(best, scenicScore(grid, Vec2(x, y)))
        }
    }
    return best
}
