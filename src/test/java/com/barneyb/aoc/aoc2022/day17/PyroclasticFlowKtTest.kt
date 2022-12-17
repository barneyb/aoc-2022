package com.barneyb.aoc.aoc2022.day17

import com.barneyb.util.Vec2
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

private const val EXAMPLE_ONE = """
>>><<><>><<<>><>>><<<>>><<<><<<>><>><<>>
"""

class PyroclasticFlowKtTest {

    @Test
    fun elRock() {
        val el = rocks[2]
        assertEquals(
            listOf(
                Vec2(2, 0),
                Vec2(2, 1),
                Vec2(0, 2),
                Vec2(1, 2),
                Vec2(2, 2),
            ), el.parts
        )
        assertEquals(3, el.width)
        assertEquals(3, el.height)
    }

    @Test
    fun exampleOne() {
        assertEquals(3068, heightOfTower(parse(EXAMPLE_ONE)))
    }
}
