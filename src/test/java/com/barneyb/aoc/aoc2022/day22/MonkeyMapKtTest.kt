package com.barneyb.aoc.aoc2022.day22

import com.barneyb.util.Dir
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
        assertEquals(Pair(Vec2(8, 6), Dir.EAST), parse(EXAMPLE_ONE))
    }

    @Test
    fun exampleOne() {
        assertEquals(6032, finalPassword(parse(EXAMPLE_ONE)))
    }

}
