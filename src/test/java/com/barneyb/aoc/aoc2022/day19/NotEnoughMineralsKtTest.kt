package com.barneyb.aoc.aoc2022.day19

import com.barneyb.util.HashMap
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.test.assertContains

private const val EXAMPLE_ONE = """
Blueprint 1: Each ore robot costs 4 ore. Each clay robot costs 2 ore. Each obsidian robot costs 3 ore and 14 clay. Each geode robot costs 2 ore and 7 obsidian.
Blueprint 2: Each ore robot costs 2 ore. Each clay robot costs 3 ore. Each obsidian robot costs 3 ore and 8 clay. Each geode robot costs 3 ore and 12 obsidian.
"""

class NotEnoughMineralsKtTest {

    @Test
    fun parsing() {
        assertEquals(
            listOf(
                Blueprint(
                    1,
                    intArrayOf(4, 0, 0, 0),
                    intArrayOf(2, 0, 0, 0),
                    intArrayOf(3, 14, 0, 0),
                    intArrayOf(2, 0, 7, 0),
                ),
                Blueprint(
                    2,
                    intArrayOf(2, 0, 0, 0),
                    intArrayOf(3, 0, 0, 0),
                    intArrayOf(3, 8, 0, 0),
                    intArrayOf(3, 0, 12, 0),
                ),
            ), parse(EXAMPLE_ONE)
        )
    }

    @Test
    fun blueprintOne() {
        val line = EXAMPLE_ONE.trim().lines()[0]
        val bp = Blueprint.parse(line)
        assertEquals(9, bp.maxGeodesIn(MINUTES_PART_ONE))
//        assertEquals(56, bp.maxGeodesIn(MINUTES_PART_TWO))
    }

    @Test
    fun blueprintTwo() {
        val line = EXAMPLE_ONE.trim().lines()[1]
        val bp = Blueprint.parse(line)
        assertEquals(12, bp.maxGeodesIn(MINUTES_PART_ONE))
//        assertEquals(62, bp.maxGeodesIn(MINUTES_PART_TWO))
    }

    @Test
    fun lineTwo() {
        val line =
            "Blueprint 2: Each ore robot costs 4 ore. Each clay robot costs 4 ore. Each obsidian robot costs 2 ore and 16 clay. Each geode robot costs 4 ore and 16 obsidian."
        val bp = Blueprint.parse(line)
        assertEquals(0, bp.maxGeodesIn(MINUTES_PART_ONE)) // a legit zero
    }

    @Test
    fun fullExample() {
        val line = EXAMPLE_ONE.trim().lines()[0]
        val bp = Blueprint.parse(line)

        val ore = bp.costOfOre
        val clay = bp.costOfClay
        val obsidian = bp.costOfObsidian
        val geode = bp.costOfGeode

        // @formatter:off
        // cost to harvest
        val costs = HashMap(
            ore       to intArrayOf(1, 0, 0, 0),
            clay      to intArrayOf(0, 1, 0, 0),
            obsidian  to intArrayOf(0, 0, 1, 0),
            geode     to intArrayOf(0, 0, 0, 1),
        )
        // @formatter:on

        fun Step.build(type: IntArray) =
            build(type, costs[type], 1)!!

        var s = Step().also(::println) // 0
        s = s.tick().also(::println)
        s = s.tick().also(::println)

        val expected = costs.map { s.build(it.first, it.second, 1) }
        s = s.build(clay).also(::println) // 3
        assertContains(expected, s)

        s = s.tick().also(::println)
        s = s.build(clay).also(::println)
        s = s.tick().also(::println)
        s = s.build(clay).also(::println)
        s = s.tick().also(::println)
        s = s.tick().also(::println)
        s = s.tick().also(::println)
        s = s.build(obsidian).also(::println) // 11
        s = s.build(clay).also(::println)
        s = s.tick().also(::println)
        s = s.tick().also(::println)
        s = s.build(obsidian).also(::println)
        s = s.tick().also(::println)
        s = s.tick().also(::println)
        s = s.build(geode).also(::println)
        s = s.tick().also(::println)
        s = s.tick().also(::println)
        s = s.build(geode).also(::println) // 21
        s = s.tick().also(::println)
        s = s.tick().also(::println)
        s = s.tick().also(::println)
        assertEquals(9, s.geodeCount)
    }

}

