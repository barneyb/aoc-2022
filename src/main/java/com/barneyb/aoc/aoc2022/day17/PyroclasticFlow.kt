package com.barneyb.aoc.aoc2022.day17

import com.barneyb.aoc.util.Solver
import com.barneyb.aoc.util.toSlice
import com.barneyb.util.Dir
import com.barneyb.util.HashMap
import com.barneyb.util.HashSet
import com.barneyb.util.Vec2
import kotlin.math.max
import kotlin.math.min

fun main() {
    Solver.execute(
        ::parse,
        ::heightOfTower, // 3161
        ::heightOfReallyTallTower // 1575931232076
    )
}

private const val WIDTH = 7

private const val ROCK_COUNT = 1_000_000_000_000

internal data class Rock(
    val parts: List<Vec2>,
    val width: Int = parts.maxOf(Vec2::x) - parts.minOf(Vec2::x) + 1,
    val height: Int = parts.maxOf(Vec2::y) - parts.minOf(Vec2::y) + 1,
) {
    companion object {
        fun parse(input: String) =
            Rock(buildList {
                input.toSlice()
                    .trim()
                    .lines()
                    .withIndex()
                    .forEach { (y, l) ->
                        for ((x, c) in l.withIndex()) {
                            if (c == '#') add(Vec2(x, y))
                        }
                    }
            })
    }

    val top get() = parts.maxOf(Vec2::y) - height

    fun move(dir: Dir, delta: Int = 1) =
        copy(parts = parts.map { it.move(dir, delta) })

    fun down(delta: Int = 1) =
        move(Dir.SOUTH, delta)

    fun right(delta: Int = 1) =
        move(Dir.EAST, delta)

    fun left(delta: Int = 1) =
        move(Dir.WEST, delta)
}

internal val rocks = listOf(
    Rock.parse("""####"""),
    Rock.parse(".#.\n###\n.#."),
    Rock.parse("..#\n..#\n###"),
    Rock.parse("#\n#\n#\n#"),
    Rock.parse("##\n##"),
)

internal fun parse(input: String) =
    input.toSlice().trim()

internal fun heightOfTower(jets: CharSequence, rockCount: Long = 2022): Long {
    var idxRock = 0
    var idxJet = 0
    val atRest = HashSet<Vec2>()
    var heightOfTower = 0
    val shapesOfTops = HashMap<List<Int>, Pair<Int, Int>>()

    @Suppress("unused")
    fun draw(rock: Rock? = null) =
        buildString {
            for (y in min(-heightOfTower, rock?.top ?: 0)..0) {
                for (x in -1..WIDTH) {
                    if (x < 0 || x == WIDTH) append('|')
                    else if (atRest.contains(Vec2(x, y))) append('#')
                    else if (rock == null) append('.')
                    else if (rock.parts.contains(Vec2(x, y))) append('@')
                    else append('.')
                }
                append('\n')
            }
            append('+')
            append("-".repeat(WIDTH))
            append('+')
            append('\n')
        }

    fun valid(r: Rock) =
        r.parts.all {
            it.x in 0..6 &&
                    it.y <= 0 &&
                    !atRest.contains(it)
        }

    while (idxRock < rockCount) {
        val ir = idxRock++ % rocks.size
        var rock = rocks[ir]
        rock = rock.move(Dir.NORTH, heightOfTower + rock.height + 2)
        rock = rock.move(Dir.EAST, 2)
        while (true) {
            val ij = idxJet++ % jets.length
            val jet = when (jets[ij]) {
                '<' -> rock.move(Dir.WEST)
                '>' -> rock.move(Dir.EAST)
                else -> throw IllegalArgumentException("bad jet")
            }
            if (valid(jet)) rock = jet
            val drop = rock.move(Dir.SOUTH)
            if (valid(drop)) rock = drop
            else {
                atRest.addAll(rock.parts)
                heightOfTower = max(heightOfTower, -rock.top)
                val shape = (0 until WIDTH).map { x ->
                    var v = Vec2(x, -heightOfTower)
                    while (!atRest.contains(v) && v.y <= 0) {
                        v = v.south()
                    }
                    heightOfTower + v.y - 1
                } + listOf(ir, ij)
                if (shapesOfTops.contains(shape)) {
                    val (preIdx, preHeight) = shapesOfTops[shape]
                    val cycleLen = idxRock - preIdx
                    val heightPerCycle = heightOfTower - preHeight
                    val remaining = rockCount - preIdx
                    return heightOfTower(
                        jets,
                        preIdx.toLong() + remaining % cycleLen
                    ) + (remaining / cycleLen) * heightPerCycle
                } else {
                    shapesOfTops[shape] = Pair(idxRock, heightOfTower)
                }
                break
            }
        }
    }
    return heightOfTower.toLong()
}

internal fun heightOfReallyTallTower(jets: CharSequence): Long =
    heightOfTower(jets, ROCK_COUNT)
