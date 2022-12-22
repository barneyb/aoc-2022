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

internal data class Map(
    val tiles: HashMap<Vec2, Tile>,
    val bounds: Rect,
    val topLeft: Vec2,
    val steps: List<Step>,
) {

    val width = bounds.width
    val height = bounds.height

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
                    EAST -> 0
                    SOUTH -> 1
                    WEST -> 2
                    NORTH -> 3
                }
}

internal fun parse(input: String): Map {
    var y = 1 // one-indexed
    var x = 0
    var start: Vec2 = Vec2.ORIGIN
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
                if (start === Vec2.ORIGIN)
                    start = Vec2(x, y)
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
        start,
        steps
    )
}

private fun walk(map: Map, crossEdge: (State) -> State) =
    map.steps.fold(State(map.topLeft, EAST)) { state, (n, turn) ->
        var (curr, facing) = state
        for (i in 0 until n) {
            var next = curr.move(facing)
            if (!map.contains(next)) {
                crossEdge(State(curr, facing)).also {
                    if (map[it.pos] != WALL) {
//                        println("$facing of $curr is ${it.pos} facing ${it.facing}")
                        next = it.pos
                        facing = it.facing
                    } else {
//                        println("$facing of $curr is ${it.pos}, which is blocked")
                    }
                }
            }
            if (!map.contains(next) || map[next] == WALL)
                break
            curr = next
        }
//        println("at $curr, turn $turn to face ${turn.execute(facing)}")
        State(curr, turn.execute(facing))
    }

internal fun finalPasswordTorus(map: Map) =
    walk(map) { (pos, facing) ->
        // wrap
        var next = when (facing) {
            NORTH -> Vec2(pos.x, map.bounds.y2)
            SOUTH -> Vec2(pos.x, map.bounds.y1)
            EAST -> Vec2(map.bounds.x1, pos.y)
            WEST -> Vec2(map.bounds.x2, pos.y)
        }

        // traverse any empty spaces
        while (!map.contains(next))
            next = next.move(facing)

        State(next, facing)
    }.password

internal data class Edge(
    val leg: Int,
    val square: Int, // 0..15, on a 4x4 grid
    val dir: Dir,
) {
    val start: Vec2 = when (dir) {
        NORTH -> Vec2(1 + leg * (square % 4), 1 + leg * (square / 4))
        EAST -> Vec2(leg * (square % 4 + 1), 1 + leg * (square / 4))
        SOUTH -> Vec2(leg * (square % 4 + 1), leg * (square / 4 + 1))
        WEST -> Vec2(1 + leg * (square % 4), leg * (square / 4 + 1))
    }

    val range = when (dir) {
        NORTH -> start.x until start.x + leg
        SOUTH -> start.x downTo start.x - leg + 1
        EAST -> start.y until start.y + leg
        WEST -> start.y downTo start.y - leg + 1
    }

    fun contains(p: Vec2) =
        when (dir) {
            NORTH -> p.x in range && p.y == start.y
            SOUTH -> p.x in range && p.y == start.y
            EAST -> p.x == start.x && p.y in range
            WEST -> p.x == start.x && p.y in range
        }

    fun crossTo(other: Edge, pos: Vec2): State {
        fun map(c: Int) =
            map(c, range, other.range.reversed())
        return State(
            when (dir) {
                NORTH, SOUTH -> when (other.dir) {
                    NORTH, SOUTH -> Vec2(map(pos.x), other.start.y)
                    EAST, WEST -> Vec2(other.start.x, map(pos.x))
                }
                EAST, WEST -> when (other.dir) {
                    NORTH, SOUTH -> Vec2(map(pos.y), other.start.y)
                    EAST, WEST -> Vec2(other.start.x, map(pos.y))
                }
            },
            other.dir.reversed()
        )
    }
}

internal fun finalPasswordCube(map: Map): Int {
    val leg = if (map.width % 3 == 0) map.width / 3 else map.width / 4
    assert(map.height % leg == 0)
    assert(map.topLeft.x % leg == 1)

    val pairs = buildMap {
        val pairs = when (leg) {
            50 -> {
                val oneNorth = Edge(leg, 1, NORTH)
                val twoNorth = Edge(leg, 2, NORTH)
                val twoEast = Edge(leg, 2, EAST)
                val twoSouth = Edge(leg, 2, SOUTH)
                val threeEast = Edge(leg, 5, EAST)
                val fiveEast = Edge(leg, 9, EAST)
                val fiveSouth = Edge(leg, 9, SOUTH)
                val sixEast = Edge(leg, 12, EAST)
                val sixSouth = Edge(leg, 12, SOUTH)
                val sixWest = Edge(leg, 12, WEST)
                val fourWest = Edge(leg, 8, WEST)
                val fourNorth = Edge(leg, 8, NORTH)
                val threeWest = Edge(leg, 5, WEST)
                val oneWest = Edge(leg, 1, WEST)

                listOf(
                    oneNorth to sixWest,
                    twoNorth to sixSouth,
                    twoEast to fiveEast,
                    twoSouth to threeEast,
                    fiveSouth to sixEast,
                    fourWest to oneWest,
                    fourNorth to threeWest,
                )
            }
            4 -> {
                val oneNorth = Edge(leg, 2, NORTH)
                val oneEast = Edge(leg, 2, EAST)
                val fourEast = Edge(leg, 6, EAST)
                val sixNorth = Edge(leg, 11, NORTH)
                val sixEast = Edge(leg, 11, EAST)
                val sixSouth = Edge(leg, 11, SOUTH)
                val fiveSouth = Edge(leg, 10, SOUTH)
                val fiveWest = Edge(leg, 10, WEST)
                val threeSouth = Edge(leg, 5, SOUTH)
                val twoSouth = Edge(leg, 4, SOUTH)
                val twoWest = Edge(leg, 4, WEST)
                val twoNorth = Edge(leg, 4, NORTH)
                val threeNorth = Edge(leg, 5, NORTH)
                val oneWest = Edge(leg, 2, WEST)
                listOf(
                    oneNorth to twoNorth,
                    oneEast to sixEast,
                    fourEast to sixNorth,
                    sixSouth to twoWest,
                    fiveSouth to twoSouth,
                    fiveWest to threeSouth,
                    threeNorth to oneWest,
                )
            }
            else -> {
                throw IllegalStateException("Unsupported $leg-length leg?!")
            }
        }
        pairs.forEach {
            put(it.first, it.second)
            put(it.second, it.first)
        }
    }

    return walk(map) { (pos, facing) ->
        val (edge, other) = pairs.entries.first { (k, _) ->
            k.contains(pos) && k.dir == facing
        }
        edge.crossTo(other, pos)
    }.password
}

internal fun map(n: Int, from: IntProgression, to: IntProgression) =
    to.first + ((n - from.first) / from.step) * to.step
