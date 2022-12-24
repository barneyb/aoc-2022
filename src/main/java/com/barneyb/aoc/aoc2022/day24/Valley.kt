package com.barneyb.aoc.aoc2022.day24

import com.barneyb.util.*

private typealias BlizState = HashMap<Vec2, Stack<Dir>>

private fun Int.gcd(other: Int): Int =
    if (other == 0) this
    else other.gcd(this % other)

private fun Int.lcm(other: Int) =
    times(other / gcd(other))

internal class Valley(
    val bounds: Rect,
    val start: Vec2,
    val goal: Vec2,
    blizzards: HashSet<Blizzard>,
) {
    private val blizStates =
        arrayOfNulls<BlizState>(bounds.width.lcm(bounds.height))

    init {
        blizStates[0] = BlizState().apply {
            for ((pos, d) in blizzards) {
                put(pos, Stack(d))
            }
        }
    }

    val blizzards
        get() = HashSet<Blizzard>().apply {
            for ((p, ds) in blizStates[0]!!)
                add(Blizzard(p, ds.peek()))
        }

    private fun getBlizState(i: Int): BlizState =
        blizStates[i].let {
            if (it == null) {
                blizStates[i] = blow(getBlizState(i - 1))
                blizStates[i]!!
            } else it
        }

    private fun blow(bliz: BlizState) =
        BlizState().apply {
            for ((curr, dirs) in bliz) {
                for (d in dirs) {
                    var next = curr.move(d)
                    when {
                        d == Dir.NORTH && next.y < bounds.y1 ->
                            next = next.south(bounds.height)
                        d == Dir.SOUTH && next.y > bounds.y2 ->
                            next = next.north(bounds.height)
                        d == Dir.EAST && next.x > bounds.x2 ->
                            next = next.west(bounds.width)
                        d == Dir.WEST && next.x < bounds.x1 ->
                            next = next.east(bounds.width)
                    }
                    if (contains(next)) get(next).add(d)
                    else put(next, Stack(d))
                }
            }
        }

    private data class Step(
        val pos: Vec2,
        val n: Int,
    )

    fun stepsToGoal(): Int {
//        println(draw(getBlizState(0)))
        var best = Int.MAX_VALUE
        val visited = HashSet<Vec3>()
        val queue = Queue<Step>()
        queue.add(Step(start, 0))
        while (queue.isNotEmpty()) {
            val (pos, n) = queue.remove()
            if (pos == goal) {
                best = n
                break
            }
            val bi = (n + 1) % blizStates.size
            val bliz = getBlizState(bi)
            fun tryMovingTo(pos: Vec2) {
                if (bliz.contains(pos)) return
                if (pos != goal && !bounds.contains(pos)) return
                val key = Vec3(pos.x, pos.y, bi)
                if (visited.contains(key)) return
                visited.add(key)
                queue.add(Step(pos, n + 1))
            }
            tryMovingTo(pos) // wait
            tryMovingTo(pos.move(Dir.NORTH))
            tryMovingTo(pos.move(Dir.SOUTH))
            tryMovingTo(pos.move(Dir.EAST))
            tryMovingTo(pos.move(Dir.WEST))
        }
        return best
    }

    @Suppress("unused")
    private fun draw(bliz: BlizState) =
        buildString {
            append("$WALL".repeat(start.x))
            append(OPEN)
            append("$WALL".repeat(bounds.width - start.x))
            append(WALL)
            append('\n')
            for (y in bounds.yRange) {
                append(WALL)
                for (x in bounds.xRange) {
                    val pos = Vec2(x, y)
                    if (!bliz.contains(pos)) {
                        append(OPEN)
                        continue
                    }
                    val dirs = bliz[pos]
                    if (dirs.size == 1) when (dirs.peek()) {
                        Dir.NORTH -> append(NORTH)
                        Dir.SOUTH -> append(SOUTH)
                        Dir.EAST -> append(EAST)
                        Dir.WEST -> append(WEST)
                    } else append(dirs.size)
                }
                append(WALL)
                append('\n')
            }
            append("$WALL".repeat(goal.x))
            append(OPEN)
            append("$WALL".repeat(bounds.width - goal.x))
            append(WALL)
            append('\n')
        }

}
