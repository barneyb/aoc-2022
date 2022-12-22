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
                tiles[Vec2(x, y)] = OPEN
            }
            '#' ->
                tiles[Vec2(x, y)] = WALL
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
    map.steps.fold(State(map.topLeft, EAST)) { state, (n, turn) ->
        var (curr, facing) = state
        for (i in 0 until n) {
            var next = curr.move(facing)
            if (!map.contains(next)) {
                crossEdge(State(curr, facing)).also {
                    if (map[it.pos] != WALL) {
                        println("$facing of $curr is ${it.pos} facing ${it.facing}")
                        next = it.pos
                        facing = it.facing
                    } else {
                        println("$facing of $curr is ${it.pos}, which is blocked")
                    }
                }
            }
            if (!map.contains(next) || map[next] == WALL)
                break
            curr = next
        }
        println("at $curr, turn $turn to face ${turn.execute(facing)}")
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

/*
              11  1
   1  45  89  23  6
  +----------------
 1|        1111
  |        1111
  |        1111
 4|        1111
 5|222233334444
  |222233334444
  |222233334444
 8|222233334444
 9|        55556666
  |        55556666
  |        55556666
12|        55556666
  +----------------
   1  45  89  11  1
              23  6

  1<  3v -  CCW  x ->
  1^  2v - flip
  1>  6< - flip
  2^  1v - flip
  2<  6^ -   CW
  2v  5^ - flip
  3^  1> -   CW
  3v  5> -  CCW
  4>  6v -   CW
  5<  3^ -   CW
  5v  2^ - flip
  6^  4< -  CCW
  6>  1< - flip
  6v  2> -  CCW

 */
internal fun finalPasswordCube(map: Map): Int {
    return walk(map) { (pos, facing) ->
        when {

            /* EXAMPLE */

            // 4 -> 6
            pos.x == 12 && pos.y in 5..8 && facing == EAST ->
                State(Vec2(map(pos.y, 5..8, 16 downTo 13), 9), SOUTH)
            // 5 -> 2
            pos.x in 9..12 && pos.y == 12 && facing == SOUTH ->
                State(Vec2(map(pos.x, 9..12, 4 downTo 1), 8), NORTH)
            // 3 -> 1
            pos.x in 5..8 && pos.y == 5 && facing == NORTH ->
                State(Vec2(9, map(pos.x, 5..8, 1..4)), EAST)

            /* MY INPUT */

            // 1 -> 4
            pos.x == 51 && pos.y in 1..50 && facing == WEST ->
                State(Vec2(1, map(pos.y, 1..50, 150 downTo 101)), EAST)
            // 4 -> 3
            pos.x in 1..50 && pos.y == 101 && facing == NORTH ->
                State(Vec2(51, map(pos.x, 1..50, 51..100)), EAST)
            // 3 -> 4
            pos.x == 51 && pos.y in 51..100 && facing == WEST ->
                State(Vec2(map(pos.y, 51..100, 1..50), 101), SOUTH)
            // 5 -> 6
            pos.x in 51..100 && pos.y == 150 && facing == SOUTH ->
                State(Vec2(50, map(pos.x, 51..100, 151..200)), WEST)
            // 6 -> 5
            pos.x == 50 && pos.y in 151..200 && facing == EAST ->
                State(Vec2(map(pos.y, 151..200, 51..100), 150), NORTH)
            // 4 -> 1
            pos.x == 1 && pos.y in 101..150 && facing == WEST ->
                State(Vec2(51, map(pos.y, 101..150, 50 downTo 1)), EAST)
            // 1 -> 6
            pos.x in 51..100 && pos.y == 1 && facing == NORTH ->
                State(Vec2(1, map(pos.x, 51..100, 151..200)), EAST)
            // 6 -> 2
            pos.x in 1..50 && pos.y == 200 && facing == SOUTH ->
                State(Vec2(map(pos.x, 1..50, 101..150), 1), SOUTH)
            // 2 -> 3
            pos.x in 101..150 && pos.y == 50 && facing == SOUTH ->
                State(Vec2(100, map(pos.x, 101..150, 51..100)), WEST)
            // 3 -> 2
            pos.x == 100 && pos.y in 50..100 && facing == EAST ->
                State(Vec2(map(pos.y, 50..100, 101..150), 50), NORTH)
            // 2 -> 6
            pos.x in 101..150 && pos.y == 1 && facing == NORTH ->
                State(Vec2(map(pos.x, 101..150, 1..50), 200), NORTH)
            // 2 -> 5
            pos.x == 150 && pos.y in 1..50 && facing == EAST ->
                State(Vec2(100, map(pos.y, 1..50, 150 downTo 101)), WEST)
            // 6 -> 1
            pos.x == 1 && pos.y in 151..200 && facing == WEST ->
                State(Vec2(map(pos.y, 151..200, 51..100), 1), SOUTH)
            // 5 -> 2
            pos.x == 100 && pos.y in 101..150 && facing == EAST ->
                State(Vec2(150, map(pos.y, 101..150, 50 downTo 1)), WEST)
//            //  ->
//            pos.x && pos.y && facing == ->
//                State(Vec2(), )
            else -> throw IllegalArgumentException("Unknown edge at $pos facing $facing")
        }
    }.password
}

internal fun map(n: Int, from: IntProgression, to: IntProgression) =
    to.first + ((n - from.first) / from.step) * to.step
