package com.barneyb.aoc.aoc2022.day17

import com.barneyb.aoc.util.Solver
import com.barneyb.aoc.util.toSlice
import com.barneyb.util.Dir
import com.barneyb.util.HashSet
import com.barneyb.util.Vec2
import kotlin.math.max
import kotlin.math.min

fun main() {
    Solver.execute(
        ::parse,
        ::heightOfTower, // 3161
    )
}

private const val WIDTH = 7

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

internal fun heightOfTower(jets: CharSequence, rockCount: Int = 2022): Int {
    var idxRock = 0
    var idxJet = 0
    val atRest = HashSet<Vec2>()
    var heightOfTower = 0

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

    repeat(rockCount) {
        var rock = rocks[idxRock++ % rocks.size]
        rock = rock.move(Dir.NORTH, heightOfTower + rock.height + 2)
        rock = rock.move(Dir.EAST, 2)
        while (true) {
            val jet = when (jets[idxJet++ % jets.length]) {
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
                break
            }
        }
    }

    return heightOfTower
}
