package com.barneyb.aoc.aoc2022.day22

import com.barneyb.aoc.aoc2022.day22.Tile.OPEN
import com.barneyb.aoc.aoc2022.day22.Tile.WALL
import com.barneyb.aoc.util.Solver
import com.barneyb.aoc.util.toSlice
import com.barneyb.util.Dir.EAST
import com.barneyb.util.HashMap
import com.barneyb.util.Rect
import com.barneyb.util.Vec2

fun main() {
    Solver.execute(
        ::parse,
        ::finalPasswordTorus, // 191,010
        ::finalPasswordCube, // 55,364
    )
}

internal enum class Tile { OPEN, WALL }

internal data class Step(
    val n: Int,
    val dir: Turn,
)

internal data class Parsed(
    val map: Map,
    val steps: List<Step>,
)

internal fun parse(input: String): Parsed {
    // one-indexed
    var y = 1
    var x = 1
    var startX = 0
    val tiles = HashMap<Vec2, Tile>()
    val steps = mutableListOf<Step>()
    var maxX = Int.MIN_VALUE
    var n = 0
    fun walk(turn: Turn) {
        steps.add(Step(n, turn))
        n = 0
    }
    for (c in input.toSlice()) {
        when (c) {
            ' ' -> x++
            '.' -> {
                if (startX == 0)
                    startX = x
                tiles[Vec2(x++, y)] = OPEN
            }
            '#' -> tiles[Vec2(x++, y)] = WALL
            '\n' -> {
                if (x > 1) { // still in the map portion
                    maxX = maxX.coerceAtLeast(x)
                    x = 1
                    y++
                }
            }
            'R' -> walk(Turn.RIGHT)
            'L' -> walk(Turn.LEFT)
            in '0'..'9' ->
                n = n * 10 + c.digitToInt()
        }
    }
    if (n > 0)
        walk(Turn.NONE)
    return Parsed(
        Map(
            tiles,
            Rect(1, 1, maxX - 1, y - 1), // newlines
            Vec2(startX, 1),
        ),
        steps,
    )
}

private fun walk(parsed: Parsed) =
    parsed.let { (map, steps) ->
        steps.fold(State(map.start, EAST)) { state, (n, turn) ->
            var curr = state
            for (i in 0 until n) {
                var next = curr.move()
                if (!map.contains(next.pos)) // stepped off the map
                    next = map.crossEdge(curr)
                if (map[next.pos] == WALL)
                    break
                curr = next
            }
            State(curr.pos, turn.execute(curr.facing))
        }
    }

internal fun finalPasswordTorus(parsed: Parsed): Int {
    parsed.map.foldIntoTorus()
    return walk(parsed).password
}

internal fun finalPasswordCube(parsed: Parsed): Int {
    parsed.map.foldIntoCube()
    return walk(parsed).password
}
