package com.barneyb.aoc.aoc2022.day22

import com.barneyb.aoc.util.Solver
import com.barneyb.aoc.util.toSlice
import com.barneyb.util.Dir
import com.barneyb.util.Vec2

fun main() {
    Solver.execute(
        ::parse,
        ::finalPassword, // 191,010
    )
}

private enum class Tile { OPEN, WALL }

internal fun parse(input: String) =
    input.toSlice().let { slice ->
        var y = 1 // one-indexed
        var x = 0
        var curr: Vec2 = Vec2.ORIGIN
        val tiles = HashMap<Vec2, Tile>()
        var maxx = Int.MIN_VALUE
        var heading = Dir.EAST
        var n = 0
        fun walk() {
            if (n == 0) return
            for (i in 0 until n) {
                var next = curr.move(heading)
                if (!tiles.containsKey(next)) {
                    // wrap
                    next = when (heading) {
                        Dir.NORTH -> Vec2(next.x, y - 2)
                        Dir.SOUTH -> Vec2(next.x, 1)
                        Dir.EAST -> Vec2(1, next.y)
                        Dir.WEST -> Vec2(maxx, next.y)
                    }
                    // traverse any empty space
                    while (!tiles.containsKey(next))
                        next = next.move(heading)
                }
                if (tiles[next] == Tile.WALL)
                    break
                curr = next
            }
            n = 0
        }
        for (c in slice) {
            x++
            when (c) {
                '.' -> {
                    if (curr === Vec2.ORIGIN)
                        curr = Vec2(x, y)
                    tiles[Vec2(x, y)] = Tile.OPEN
                }
                '#' ->
                    tiles[Vec2(x, y)] = Tile.WALL
                '\n' -> {
                    if (n == 0) // still in the map
                        maxx = maxx.coerceAtLeast(x)
                    walk()
                    x = 0
                    y++
                }
                'R' -> {
                    walk()
                    heading = heading.turnRight()
                }
                'L' -> {
                    walk()
                    heading = heading.turnLeft()
                }
                in '0'..'9' ->
                    n = n * 10 + c.digitToInt()
            }
        }
        Pair(curr, heading)
    }

fun finalPassword(state: Pair<Vec2, Dir>) =
    state.first.y * 1000 +
            state.first.x * 4 +
            when (state.second) {
                Dir.NORTH -> 3
                Dir.SOUTH -> 1
                Dir.EAST -> 0
                Dir.WEST -> 2
            }
