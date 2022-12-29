package com.barneyb.aoc.aoc2022.day22

import com.barneyb.util.*
import com.barneyb.util.Dir.*
import kotlin.math.sqrt

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
    val leg = sqrt(tiles.size / 6.0).toInt().also {
        assert(height % it == 0) { "$it doesn't divide height $height evenly" }
        assert(topLeft.x % it == 1) { "$it leaves $topLeft not in a corner" }
    }

    var adjacentEdges: HashMap<Edge, Edge>? = null

    inner class Edge(
        val square: Int, // 0..15, on a 4x4 grid
        val dir: Dir,
    ) {

        /**
         * The corner where the edge starts, moving clockwise. E.g., a NORTH
         * edge will start at the top-left of the square, and a SOUTH edge will
         * start at the bottom-right.
         */
        val start: Vec2 by lazy {
            when (dir) {
                NORTH -> Vec2(1 + leg * (square % 4), 1 + leg * (square / 4))
                EAST -> Vec2(leg * (square % 4 + 1), 1 + leg * (square / 4))
                SOUTH -> Vec2(leg * (square % 4 + 1), leg * (square / 4 + 1))
                WEST -> Vec2(1 + leg * (square % 4), leg * (square / 4 + 1))
            }
        }

        /**
         * The range of the dynamic coordinate from [start] along the edge.
         */
        val range by lazy {
            when (dir) {
                NORTH -> start.x until start.x + leg
                SOUTH -> start.x downTo start.x - leg + 1
                EAST -> start.y until start.y + leg
                WEST -> start.y downTo start.y - leg + 1
            }
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
            return "Edge(sqr=$square, dir=$dir)"
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is Edge) return false

            if (square != other.square) return false
            if (dir != other.dir) return false

            return true
        }

        override fun hashCode(): Int {
            var result = square
            result = 31 * result + dir.hashCode()
            return result
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
            val squares = HashSet<Int>().apply {
                for (sqr in 0..15)
                    if (tiles.contains(
                            Vec2(
                                1 + sqr % 4 * leg,
                                1 + sqr / 4 * leg
                            )
                        )
                    )
                        add(sqr)
            }

            fun exists(sqr: Int) =
                squares.contains(sqr)

            fun known(e: Edge) =
                contains(e)

            fun join(a: Edge, z: Edge) {
                if (contains(a))
                    throw IllegalArgumentException(
                        "can't remap $a to $z (mapped to ${
                            get(
                                a
                            )
                        })"
                    )
                put(a, z)
                if (contains(z))
                    throw IllegalArgumentException(
                        "can't remap $z to $a (mapped to ${
                            get(
                                z
                            )
                        })"
                    )
                put(z, a)
            }

            // first, all the "internal" edges
            for (s in squares) {
                fun up(sqr: Int) =
                    if (sqr >= 4) sqr - 4 else -1

                fun left(sqr: Int) =
                    if (sqr % 4 > 0) sqr - 1 else -1
                if (exists(up(s))) join(Edge(s, NORTH), Edge(up(s), SOUTH))
                if (exists(left(s))) join(Edge(s, WEST), Edge(left(s), EAST))
            }

            // Now start wrapping, which always takes multiple passes. It'll
            // always take at least two, but depending on the traversal order
            // (which depends on the HashMap's undefined iteration order), I
            // believe it may take a third.
            val queue = Queue<Edge>()
            while (size < 24) {
                queue.addAll(keys)
                while (queue.isNotEmpty()) {
                    val a = queue.remove()
                    val b = get(a)
                    if (known(Edge(a.square, a.dir.turnRight()))) {
                        if (!known(Edge(b.square, b.dir.turnLeft()))) {
                            val e1 =
                                get(Edge(a.square, a.dir.turnRight())).let {
                                    Edge(it.square, it.dir.turnRight())
                                }
                            val e2 = Edge(b.square, b.dir.turnLeft())
                            join(e1, e2)
                            queue.add(e1, e2)
                        }
                    }
                }
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
