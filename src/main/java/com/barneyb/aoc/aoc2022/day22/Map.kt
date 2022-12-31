package com.barneyb.aoc.aoc2022.day22

import com.barneyb.aoc.aoc2022.day22.Turn.LEFT
import com.barneyb.aoc.aoc2022.day22.Turn.RIGHT
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
        assert(it * it * 6 == tiles.size) { "$it doesn't multiply out to tile count" }
        assert(height % it == 0) { "$it doesn't divide height $height evenly" }
        assert(topLeft.x % it == 1) { "$it leaves $topLeft not in a corner" }
    }

    /**
     * Pairs of edges which are "the same", but from each of the viewpoints of
     * the two squares being joined. Pairs are recorded both ways, so every
     * Edge is present twice: once as a key and once as a value.
     */
    var edgePairs: HashMap<Edge, Edge>? = null

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
         * Cross to the edge's dual, and return the resulting [State].
         */
        fun crossTo(dual: Edge, pos: Vec2): State {
            fun map(c: Int) =
                dual.range.reversed()[range.indexOf(c)]
            return State(
                when (dir) {
                    NORTH, SOUTH -> when (dual.dir) {
                        NORTH, SOUTH -> Vec2(map(pos.x), dual.start.y)
                        EAST, WEST -> Vec2(dual.start.x, map(pos.x))
                    }
                    EAST, WEST -> when (dual.dir) {
                        NORTH, SOUTH -> Vec2(map(pos.y), dual.start.y)
                        EAST, WEST -> Vec2(dual.start.x, map(pos.y))
                    }
                },
                dual.dir.reversed()
            )
        }

        fun toThe(turn: Turn) =
            Edge(square, turn.execute(dir))

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
        edgePairs = HashMap<Edge, Edge>().apply {
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
        edgePairs = HashMap<Edge, Edge>().apply {
            fun halfJoin(a: Edge, b: Edge) {
                if (contains(a)) {
                    val it = get(a)
                    throw IllegalArgumentException("can't remap $a to $b (mapped to $it)")
                }
                put(a, b)
            }

            fun join(a: Edge, z: Edge) {
                halfJoin(a, z)
                halfJoin(z, a)
            }

            // first, all the "internal" edges
            with(HashSet<Int>()) {
                (1 until 15).filter {
                    tiles.contains(Vec2(1 + it % 4 * leg, 1 + it / 4 * leg))
                }.forEach(this::add)
                for (s in this) {
                    val up = if (s >= 4) s - 4 else -1
                    if (contains(up))
                        join(Edge(s, NORTH), Edge(up, SOUTH))
                    val left = if (s % 4 > 0) s - 1 else -1
                    if (contains(left))
                        join(Edge(s, WEST), Edge(left, EAST))
                }
            }

            // Now start wrapping. Depending on the traversal order (which
            // depends on the HashMap's undefined iteration order), it may take
            // multiple passes. Note that this wouldn't work w/ Java's HashMap,
            // as it protects against concurrent modification, by mine doesn't.
            while (size < 24) {
                for (edge in keys) {
                    // if the edge "to the right" is paired...
                    val right = edge.toThe(RIGHT)
                    if (contains(right)) {
                        // but the edge "to the left" its dual doesn't...
                        val dual = get(edge)
                        val left = dual.toThe(LEFT)
                        if (!contains(left)) {
                            // ...we have an internal corner between left and
                            // the edge "to the right" of right's dual.
                            val aPrimePrime = get(right).toThe(RIGHT)
                            // join them!
                            join(aPrimePrime, left)
                        }
                    }
                }
            }

        }
    }

    fun crossEdge(state: State): State {
        val (pos, facing) = state
        val (edge, dual) = edgePairs!!.firstOrNull { (k, _) ->
            k.contains(pos) && k.dir == facing
        }
            ?: throw IllegalArgumentException("Unknown edge facing $facing from $pos.")
        return edge.crossTo(dual, pos)
    }
}
