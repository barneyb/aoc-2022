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

    private val bestsByMinute = Array<Step?>(MINUTES_PART_TWO + 1) { null }

    fun maxGeodesIn(minutes: Int): Int {
        if (bestsByMinute[minutes] != null)
            return bestsByMinute[minutes]!!.geodeCount
        bestsByMinute[minutes] = Step()
        val queue = Stack<Step>()
        queue.add(bestsByMinute[minutes]!!)
        var itr = 0L
        while (queue.isNotEmpty()) {
            var curr = queue.remove()
            itr++

            val timeLeft = minutes - curr.minute - 1
            if (timeLeft == 0) {
                // can't build anything useful the final minute, so short circuit
                curr = curr.tick()
                itr++
            }

            if (curr.minute == minutes) {
                if (curr.geodeCount > bestsByMinute[curr.minute]!!.geodeCount)
                    bestsByMinute[curr.minute] = curr
                continue
            }

            val before = queue.size
            curr.build(costOfOre, oneOre, timeLeft)?.also(queue::add)
            curr.build(costOfClay, oneClay, timeLeft)?.also(queue::add)
            curr.build(costOfObsidian, oneObsidian, timeLeft)?.also(queue::add)
            curr.build(costOfGeode, oneGeode, timeLeft)?.also(queue::add)
            if (queue.size == before)
                queue.add(curr.tick())
        }

        return bestsByMinute[minutes]!!.geodeCount
    }

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
