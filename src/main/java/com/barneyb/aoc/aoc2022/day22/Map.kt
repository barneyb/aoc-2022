package com.barneyb.aoc.aoc2022.day22

import com.barneyb.util.Dir
import com.barneyb.util.Dir.*
import com.barneyb.util.HashMap
import com.barneyb.util.Rect
import com.barneyb.util.Vec2

internal class Map(
    val tiles: HashMap<Vec2, Tile>,
    val bounds: Rect,
    val topLeft: Vec2,
    val steps: List<Step>,
) {

    val width = bounds.width
    val height = bounds.height
    val leg = (if (width % 3 == 0) width / 3 else width / 4).also {
        assert(height % it == 0) { "$it doesn't divide height $height evenly" }
        assert(topLeft.x % it == 1) { "$it leaves $topLeft not in a corner" }
    }

    private var pairs: HashMap<Edge, Edge>? = null

    inner class Edge(
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

    fun contains(pos: Vec2) =
        tiles.contains(pos)

    operator fun get(pos: Vec2) =
        tiles[pos]

    fun foldIntoTorus() {
        pairs = HashMap<Edge, Edge>().apply {
            when (leg) {
                4 -> listOf(
                    Edge(2, NORTH) to Edge(10, SOUTH),
                    Edge(4, NORTH) to Edge(4, SOUTH),
                    Edge(2, EAST) to Edge(2, WEST),
                    Edge(11, EAST) to Edge(10, WEST),
                    Edge(6, EAST) to Edge(4, WEST),
                    Edge(11, NORTH) to Edge(11, SOUTH),
                    Edge(5, NORTH) to Edge(5, SOUTH),
                )
                50 -> listOf(
                    Edge(1, NORTH) to Edge(9, SOUTH),
                    Edge(12, WEST) to Edge(12, EAST),
                    Edge(2, NORTH) to Edge(2, SOUTH),
                    Edge(12, SOUTH) to Edge(8, NORTH),
                    Edge(2, EAST) to Edge(1, WEST),
                    Edge(9, EAST) to Edge(8, WEST),
                    Edge(5, EAST) to Edge(5, WEST),
                )
                else ->
                    throw IllegalStateException("Unsupported $leg-length leg?!")
            }.forEach {
                put(it.first, it.second)
                put(it.second, it.first)
            }
        }
    }

    fun foldIntoCube() {
        pairs = HashMap<Edge, Edge>().apply {
            when (leg) {
                4 -> listOf(
                    Edge(2, NORTH) to Edge(4, NORTH),
                    Edge(2, EAST) to Edge(11, EAST),
                    Edge(6, EAST) to Edge(11, NORTH),
                    Edge(11, SOUTH) to Edge(4, WEST),
                    Edge(10, SOUTH) to Edge(4, SOUTH),
                    Edge(10, WEST) to Edge(5, SOUTH),
                    Edge(5, NORTH) to Edge(2, WEST),
                )
                50 -> listOf(
                    Edge(1, NORTH) to Edge(12, WEST),
                    Edge(2, NORTH) to Edge(12, SOUTH),
                    Edge(2, EAST) to Edge(9, EAST),
                    Edge(2, SOUTH) to Edge(5, EAST),
                    Edge(9, SOUTH) to Edge(12, EAST),
                    Edge(8, WEST) to Edge(1, WEST),
                    Edge(8, NORTH) to Edge(5, WEST),
                )
                else ->
                    throw IllegalStateException("Unsupported $leg-length leg?!")
            }.forEach {
                put(it.first, it.second)
                put(it.second, it.first)
            }
        }
    }

    fun crossEdge(state: State): State {
        val (pos, facing) = state
        val (edge, other) = pairs!!.first { (k, _) ->
            k.contains(pos) && k.dir == facing
        }
        return edge.crossTo(other, pos)
    }
}
