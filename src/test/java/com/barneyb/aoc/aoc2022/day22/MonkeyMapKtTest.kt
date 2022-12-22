package com.barneyb.aoc.aoc2022.day22

import com.barneyb.util.Rect
import com.barneyb.util.Vec2
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

private const val EXAMPLE_ONE = """        ...#
        .#..
        #...
        ....
...#.......#
........#...
..#....#....
..........#.
        ...#....
        .....#..
        .#......
        ......#.

10R5L5R10L4R5L5
"""

class MonkeyMapKtTest {

    @Test
    fun parsing() {
        val map = parse(EXAMPLE_ONE)
        assertEquals(Vec2(9, 1), map.topLeft)
        assertEquals(
            Rect(
                1..16,
                1..12,
            ),
            map.bounds
        )
        assertEquals(96, map.tiles.size)
        assertEquals(7, map.steps.size)
    }

    @Test
    fun exampleOne() {
        assertEquals(6032, finalPasswordTorus(parse(EXAMPLE_ONE)))
    }

}