package com.barneyb.aoc.aoc2016.day17

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class TwoStepsForwardKtTest {

    @Test
    fun exampleOne() {
        assertEquals("DDRRRD", shortestPath("ihgpwlah"))
    }

    @Test
    fun exampleTwo() {
        assertEquals("DDUDRLRRUDRD", shortestPath("kglvqrro"))
    }

    @Test
    fun exampleThree() {
        assertEquals("DRURDRUDDLLDLUURRDULRLDUUDDDRR", shortestPath("ulqzkmiv"))
    }

    @Test
    fun hashing() {
        assertAll(
            { assertEquals("ced9", hash("hijkl").slice(0..3)) },
            { assertEquals("5745", hash("hijklDR").slice(0..3)) },
            { assertEquals("5745", hash("hijkl", "DR").slice(0..3)) })
    }

    @Test
    fun adjacent() {
        val start = State.start("hijkl")
        val adj = adjacent(start)
        assertEquals(1, adj.size)
        assertEquals(start.down(), adj.first())
    }

    @Test
    fun adjacentExampleThree() {
        val curr = State(x = 1, y = 2, "ulqzkmiv", "DRURDRUDDLLDLUU")
        val adj = adjacent(curr)
        assertTrue(adj.contains(curr.right()))
    }

}
