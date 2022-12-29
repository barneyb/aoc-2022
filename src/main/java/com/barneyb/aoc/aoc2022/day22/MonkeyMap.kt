package com.barneyb.aoc.aoc2022.day22

import com.barneyb.aoc.aoc2022.day22.Tile.OPEN
import com.barneyb.aoc.aoc2022.day22.Tile.WALL
import com.barneyb.aoc.util.Solver
import com.barneyb.aoc.util.toSlice
import com.barneyb.util.Dir
import com.barneyb.util.Dir.*
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

internal enum class Turn {
    LEFT,
    RIGHT,
    NONE; // sort of a kludge, but neatly sidesteps the fencepost problem

    fun execute(d: Dir): Dir =
        when (this) {
            LEFT -> d.turnLeft()
            RIGHT -> d.turnRight()
            NONE -> d
        }
}

internal data class Step(
    val n: Int,
    val dir: Turn,
)

internal data class State(
    val pos: Vec2,
    val facing: Dir,
) {
    val password
        get() = pos.y * 1000 +
                pos.x * 4 +
                when (facing) {
                    EAST -> 0
                    SOUTH -> 1
                    WEST -> 2
                    NORTH -> 3
                }

    fun move() =
        State(pos.move(facing), facing)
}

internal fun parse(input: String): Map {
    var y = 1 // one-indexed
    var x = 0
    var startX = 0
    val tiles = HashMap<Vec2, Tile>()
    val steps = mutableListOf<Step>()
    var maxX = Int.MIN_VALUE
    var n = 0
    fun walk(turn: Turn) {
        if (n > 0) {
            steps.add(Step(n, turn))
            n = 0
        }
    }
    for (c in input.toSlice()) {
        x++
        when (c) {
            '.' -> {
                if (startX == 0)
                    startX = x
                tiles[Vec2(x, y)] = OPEN
            }
            '#' ->
                tiles[Vec2(x, y)] = WALL
            '\n' -> {
                if (n == 0) // still in the map portion
                    maxX = maxX.coerceAtLeast(x)
                walk(Turn.NONE)
                x = 0
                y++
            }
            'R' ->
                walk(Turn.RIGHT)
            'L' ->
                walk(Turn.LEFT)
            in '0'..'9' ->
                n = n * 10 + c.digitToInt()
        }
    }
    return Map(
        tiles,
        Rect(
            1,
            1,
            maxX - 1, // the newline
            y - 3, // final newline, directions, blank
        ),
        Vec2(startX, 1),
        steps
    )
}

private fun walk(map: Map) =
    map.steps.fold(State(map.topLeft, EAST)) { state, (n, turn) ->
        var curr = state
        for (i in 0 until n) {
            var next = curr.move()
            if (!map.contains(next.pos))
                next = map.crossEdge(curr)
            if (map[next.pos] == WALL)
                break
            curr = next
        }
        State(curr.pos, turn.execute(curr.facing))
    }

internal fun finalPasswordTorus(map: Map): Int {
    map.foldIntoTorus()
    return walk(map).password
}

internal fun finalPasswordCube(map: Map): Int {
    map.foldIntoCube()
    return walk(map).password
}
