package com.barneyb.aoc.aoc2022.day22

import com.barneyb.util.Dir
import com.barneyb.util.Dir.*
import com.barneyb.util.HashMap
import com.barneyb.util.Rect
import com.barneyb.util.Vec2

private fun IntProgression.indexOf(n: Int) =
    (n - first) / step

private operator fun IntProgression.get(i: Int) =
    first + i * step

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

    private var adjacentEdges: HashMap<Edge, Edge>? = null

    inner class Edge(
        val square: Int, // 0..15, on a 4x4 grid
        val dir: Dir,
    ) {

        /**
         * The corner where the edge starts, moving clockwise. E.g., a NORTH
         * edge will start at the top-left of the square, and a SOUTH edge will
         * start at the bottom-right.
         */
        val start: Vec2 = when (dir) {
            NORTH -> Vec2(1 + leg * (square % 4), 1 + leg * (square / 4))
            EAST -> Vec2(leg * (square % 4 + 1), 1 + leg * (square / 4))
            SOUTH -> Vec2(leg * (square % 4 + 1), leg * (square / 4 + 1))
            WEST -> Vec2(1 + leg * (square % 4), leg * (square / 4 + 1))
        }

        /**
         * The range of the dynamic coordinate from [start] along the edge.
         */
        val range = when (dir) {
            NORTH -> start.x until start.x + leg
            SOUTH -> start.x downTo start.x - leg + 1
            EAST -> start.y until start.y + leg
            WEST -> start.y downTo start.y - leg + 1
        }

        /** Whether the given point is on this edge. */
        fun contains(p: Vec2) =
            when (dir) {
                NORTH -> p.x in range && p.y == start.y
                SOUTH -> p.x in range && p.y == start.y
                EAST -> p.x == start.x && p.y in range
                WEST -> p.x == start.x && p.y in range
            }

        /**
         * Cross to the other (adjacent) edge, and return the resulting [State].
         */
        fun crossTo(other: Edge, pos: Vec2): State {
            fun map(c: Int) =
                other.range.reversed()[range.indexOf(c)]
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

        override fun toString(): String {
            return "Edge(dir=$dir, start=$start, range=$range)"
        }

    }

    fun contains(pos: Vec2) =
        tiles.contains(pos)

    operator fun get(pos: Vec2) =
        tiles[pos]

    fun foldIntoTorus() {
        adjacentEdges = HashMap<Edge, Edge>().apply {
            fun walk(
                dir: Dir,
                pt: (major: Int, minor: Int) -> Vec2,
                sqr: (major: Int, minor: Int) -> Int,
            ) {
                for (major in 0 until 4) {
                    var first: Int? = null
                    var last: Int? = null
                    for (minor in 0 until 4) {
                        val p = pt(
                            1 + major * leg,
                            1 + minor * leg
                        )
                        if (tiles.contains(p)) {
                            if (first == null)
                                first = minor
                            last = minor
                        }
                    }
                    if (first != null) { // square(s) in this row/column exist!
                        val a = Edge(sqr(major, first), dir.reversed())
                        val z = Edge(sqr(major, last!!), dir)
                        put(a, z)
                        put(z, a)
                    }
                }
            }
            walk(EAST, { y, x -> Vec2(x, y) }, { y, x -> y * 4 + x })
            walk(SOUTH, { x, y -> Vec2(x, y) }, { x, y -> y * 4 + x })
        }
    }

    fun foldIntoCube() {
        adjacentEdges = HashMap<Edge, Edge>().apply {
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
        val (edge, other) = adjacentEdges!!.firstOrNull { (k, _) ->
            k.contains(pos) && k.dir == facing
        }
            ?: throw IllegalArgumentException("Unknown edge facing $facing from $pos.")
        return edge.crossTo(other, pos)
    }
}
