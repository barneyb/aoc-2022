package com.barneyb.aoc.aoc2022.day18

import com.barneyb.util.HashSet
import com.barneyb.util.Vec3
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

private const val EXAMPLE_ZERO = """
1,1,1
2,1,1
"""
private const val EXAMPLE_ONE = """
2,2,2
1,2,2
3,2,2
2,1,2
2,3,2
2,2,1
2,2,3
2,2,4
2,2,6
1,2,5
3,2,5
2,1,5
2,3,5
"""

class BoilingBouldersKtTest {

    @Test
    fun parsing() {
        assertEquals(
            HashSet(
                Vec3(1, 1, 1),
                Vec3(2, 1, 1),
            ), parse(EXAMPLE_ZERO)
        )
    }

    @Test
    fun exampleZero() {
        assertEquals(10, surfaceArea(parse(EXAMPLE_ZERO)))
        assertEquals(10, externalSurfaceArea(parse(EXAMPLE_ZERO)))
    }

    @Test
    fun exampleOne() {
        assertEquals(64, surfaceArea(parse(EXAMPLE_ONE)))
    }

    @Test
    fun exampleTwo() {
        assertEquals(58, externalSurfaceArea(parse(EXAMPLE_ONE)))
    }

}
