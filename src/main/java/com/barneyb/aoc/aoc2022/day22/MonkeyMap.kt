package com.barneyb.aoc.aoc2022.day22

import com.barneyb.aoc.util.Solver
import com.barneyb.aoc.util.toSlice
import com.barneyb.util.Dir
import com.barneyb.util.HashMap
import com.barneyb.util.Rect
import com.barneyb.util.Vec2

fun main() {
    Solver.execute(
        ::parse,
        ::finalPasswordTorus, // 191,010
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

internal data class Map(
    val tiles: HashMap<Vec2, Tile>,
    val bounds: Rect,
    val topLeft: Vec2,
    val steps: List<Step>,
) {

    fun contains(pos: Vec2) =
        tiles.contains(pos)

    operator fun get(pos: Vec2) =
        tiles[pos]

}

internal data class State(
    val pos: Vec2,
    val facing: Dir,
) {
    val password
        get() = pos.y * 1000 +
                pos.x * 4 +
                when (facing) {
                    Dir.EAST -> 0
                    Dir.SOUTH -> 1
                    Dir.WEST -> 2
                    Dir.NORTH -> 3
                }
}

internal fun parse(input: String): Map {
    var y = 1 // one-indexed
    var x = 0
    var start: Vec2 = Vec2.ORIGIN
    val tiles = HashMap<Vec2, Tile>()
    val steps = mutableListOf<Step>()
    var maxx = Int.MIN_VALUE
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
                if (start === Vec2.ORIGIN)
                    start = Vec2(x, y)
                tiles[Vec2(x, y)] = Tile.OPEN
            }
            '#' ->
                tiles[Vec2(x, y)] = Tile.WALL
            '\n' -> {
                if (n == 0) // still in the map portion
                    maxx = maxx.coerceAtLeast(x)
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
            maxx - 1, // the newline
            y - 3, // final newline, directions, blank
        ),
        start,
        steps
    )
}

private fun walk(map: Map, crossEdge: (State) -> State) =
    map.steps.fold(State(map.topLeft, Dir.EAST)) { state, (n, turn) ->
        var (curr, facing) = state
        for (i in 0 until n) {
            var next = curr.move(facing)
            if (!map.contains(next)) {
                crossEdge(State(next, facing)).also {
                    next = it.pos
                    facing = it.facing
                }
            }
            if (map[next] == Tile.WALL)
                break
            curr = next
        }
        State(curr, turn.execute(facing))
    }

internal fun finalPasswordTorus(map: Map) =
    walk(map) { (pos, facing) ->
        // wrap
        var next = when (facing) {
            Dir.NORTH -> Vec2(pos.x, map.bounds.y2)
            Dir.SOUTH -> Vec2(pos.x, map.bounds.y1)
            Dir.EAST -> Vec2(map.bounds.x1, pos.y)
            Dir.WEST -> Vec2(map.bounds.x2, pos.y)
        }

        // traverse any empty spaces
        while (!map.contains(next))
            next = next.move(facing)

        State(next, facing)
    }.password
