package com.barneyb.aoc.aoc2022.day19

import com.barneyb.aoc.util.toInt
import com.barneyb.util.Queue
import com.barneyb.util.Stack

// @formatter:off
private val oneOre      = intArrayOf(1, 0, 0, 0)
private val oneClay     = intArrayOf(0, 1, 0, 0)
private val oneObsidian = intArrayOf(0, 0, 1, 0)
private val oneGeode    = intArrayOf(0, 0, 0, 1)
// @formatter:on

data class Blueprint(
    val id: Int,
    val costOfOre: IntArray,
    val costOfClay: IntArray,
    val costOfObsidian: IntArray,
    val costOfGeode: IntArray
) {

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

    val maxGeodes by lazy {
        var best = Step()
        val queue = Stack<Step>()
        queue.add(best)
        var itr = 0L
        while (queue.isNotEmpty()) {
            var curr = queue.remove()
            itr++

            if (curr.remaining == 1) {
                // can't build anything useful the final minute, so short circuit
                curr = curr.tick()
                itr++
            }

            if (curr.done) {
                if (curr.geodeCount > best.geodeCount)
                    best = curr
                continue
            }

            val before = queue.size
            curr.build(costOfOre, oneOre)?.also(queue::add)
            curr.build(costOfClay, oneClay)?.also(queue::add)
            curr.build(costOfObsidian, oneObsidian)?.also(queue::add)
            curr.build(costOfGeode, oneGeode)?.also(queue::add)
            if (queue.size == before)
                queue.add(curr.tick())
        }

        best.geodeCount
    }

    val qualityLevel
        get() = id * maxGeodes

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Blueprint

        if (id != other.id) return false
        if (!costOfOre.contentEquals(other.costOfOre)) return false
        if (!costOfClay.contentEquals(other.costOfClay)) return false
        if (!costOfObsidian.contentEquals(other.costOfObsidian)) return false
        if (!costOfGeode.contentEquals(other.costOfGeode)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + costOfOre.contentHashCode()
        result = 31 * result + costOfClay.contentHashCode()
        result = 31 * result + costOfObsidian.contentHashCode()
        result = 31 * result + costOfGeode.contentHashCode()
        return result
    }

}
