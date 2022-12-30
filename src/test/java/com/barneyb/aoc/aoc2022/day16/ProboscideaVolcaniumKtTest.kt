package com.barneyb.aoc.aoc2022.day16

import com.barneyb.aoc.util.Input
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

private const val EXAMPLE_ONE = """
Valve AA has flow rate=0; tunnels lead to valves DD, II, BB
Valve BB has flow rate=13; tunnels lead to valves CC, AA
Valve CC has flow rate=2; tunnels lead to valves DD, BB
Valve DD has flow rate=20; tunnels lead to valves CC, AA, EE
Valve EE has flow rate=3; tunnels lead to valves FF, DD
Valve FF has flow rate=0; tunnels lead to valves EE, GG
Valve GG has flow rate=0; tunnels lead to valves FF, HH
Valve HH has flow rate=22; tunnel leads to valve GG
Valve II has flow rate=0; tunnels lead to valves AA, JJ
Valve JJ has flow rate=21; tunnel leads to valve II
"""

class ProboscideaVolcaniumKtTest {

    @Test
    fun parsing() {
        assertEquals(
            listOf(
                Valve("AA", 0, listOf("DD", "II", "BB")),
                Valve("BB", 13, listOf("CC", "AA")),
                Valve("CC", 2, listOf("DD", "BB")),
                Valve("DD", 20, listOf("CC", "AA", "EE")),
                Valve("EE", 3, listOf("FF", "DD")),
                Valve("FF", 0, listOf("EE", "GG")),
                Valve("GG", 0, listOf("FF", "HH")),
                Valve("HH", 22, listOf("GG")),
                Valve("II", 0, listOf("AA", "JJ")),
                Valve("JJ", 21, listOf("II")),
            ),
            parseValves(EXAMPLE_ONE)
        )
    }

    @Test
    fun exampleOne() {
        assertEquals(1651, maximumPressureRelease(parse(EXAMPLE_ONE)))
    }

    @Test
    fun exampleTwo() {
        assertEquals(
            1707,
            maximumPressureReleaseWithElephant(parse(EXAMPLE_ONE))
        )
    }

    @Test
    fun theRealDeal() {
        val parsed = parse(Input.forProblem(::parse))
        assertEquals(1880, maximumPressureRelease(parsed))
        assertEquals(2520, maximumPressureReleaseWithElephant(parsed))
    }

}
