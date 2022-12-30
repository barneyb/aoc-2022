package com.barneyb.aoc.aoc2022.day19

import com.barneyb.aoc.util.toInt
import com.barneyb.util.Queue
import com.barneyb.util.Stack

internal fun compress(parts: IntArray) =
    (parts[0].toLong() shl 0) +
            (parts[1].toLong() shl 16) +
            (parts[2].toLong() shl 32) +
            (parts[3].toLong() shl 48)

internal fun decompress(n: Long) =
    intArrayOf(
        ((n shr 0) and 0xFFFF).toInt(),
        ((n shr 16) and 0xFFFF).toInt(),
        ((n shr 32) and 0xFFFF).toInt(),
        ((n shr 48) and 0xFFFF).toInt(),
    )

// @formatter:off
internal val oneOre      = 1L shl 0
internal val oneClay     = 1L shl 16
internal val oneObsidian = 1L shl 32
internal val oneGeode    = 1L shl 48
// @formatter:on

data class Blueprint(
    val id: Int,
    val costOfOre: Long,
    val costOfClay: Long,
    val costOfObsidian: Long,
    val costOfGeode: Long,
) {

    constructor(
        id: Int,
        costOfOre: IntArray,
        costOfClay: IntArray,
        costOfObsidian: IntArray,
        costOfGeode: IntArray,
    ) : this(
        id,
        compress(costOfOre),
        compress(costOfClay),
        compress(costOfObsidian),
        compress(costOfGeode),
    )

    companion object {

        private val lineRegex =
            Regex("Blueprint (\\d+): Each ore robot costs (\\d+) ore. Each clay robot costs (\\d+) ore. Each obsidian robot costs (\\d+) ore and (\\d+) clay. Each geode robot costs (\\d+) ore and (\\d+) obsidian.")

        fun parse(line: CharSequence) =
            lineRegex.matchEntire(line)!!.let { m ->
                val ns = Queue<Int>()
                ns.addAll(
                    m.groupValues
                        .drop(1)
                        .map(String::toInt)
                )
                Blueprint(
                    ns.dequeue(),
                    intArrayOf(ns.dequeue(), 0, 0, 0),
                    intArrayOf(ns.dequeue(), 0, 0, 0),
                    intArrayOf(ns.dequeue(), ns.dequeue(), 0, 0),
                    intArrayOf(ns.dequeue(), 0, ns.dequeue(), 0),
                )
            }
    }

    fun maxGeodesIn(minutes: Int): Long {
        var best = Step()
        val queue = Stack<Step>()
        queue.add(best)
        while (queue.isNotEmpty()) {
            var curr = queue.remove()
            assert(curr.minute <= minutes)

            val timeLeft = minutes - curr.minute - 1
            if (timeLeft == 0) {
                // can't build anything useful the final minute, so short circuit
                curr = curr.tick()
            }

            if (curr.minute == minutes) {
                if (curr.pool > best.pool)
                    best = curr
                continue
            }

            if (curr.getPotentialGeodeCount(timeLeft + 1) < best.geodeCount) {
                // even a new geode bot per turn won't catch us up
                continue
            }

            val before = queue.size
            curr.build(costOfOre, oneOre, timeLeft)?.also(queue::add)
            curr.build(costOfClay, oneClay, timeLeft)?.also(queue::add)
            curr.build(costOfObsidian, oneObsidian, timeLeft)?.also(queue::add)
            curr.build(costOfGeode, oneGeode, timeLeft)?.also(queue::add)
            // don't have time to build anything; skip to the end
            if (queue.size == before)
                queue.add(curr.tick(timeLeft + 1))
        }

        return best
//            .also(::println)
            .geodeCount
    }

}
