package com.barneyb.aoc.aoc2022.day10

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

private const val EXAMPLE_ONE = """
noop
addx 3
addx -5
"""

private const val EXAMPLE_TWO = """
addx 15
addx -11
addx 6
addx -3
addx 5
addx -1
addx -8
addx 13
addx 4
noop
addx -1
addx 5
addx -1
addx 5
addx -1
addx 5
addx -1
addx 5
addx -1
addx -35
addx 1
addx 24
addx -19
addx 1
addx 16
addx -11
noop
noop
addx 21
addx -15
noop
noop
addx -3
addx 9
addx 1
addx -3
addx 8
addx 1
addx 5
noop
noop
noop
noop
noop
addx -36
noop
addx 1
addx 7
noop
noop
noop
addx 2
addx 6
noop
noop
noop
noop
noop
addx 1
noop
noop
addx 7
addx 1
noop
addx -13
addx 13
addx 7
noop
addx 1
addx -33
noop
noop
noop
addx 2
noop
noop
noop
addx 8
noop
addx -1
addx 2
addx 1
noop
addx 17
addx -9
addx 1
addx 1
addx -3
addx 11
noop
noop
addx 1
noop
addx 1
noop
noop
addx -13
addx -19
addx 1
addx 3
addx 26
addx -30
addx 12
addx -1
addx 3
addx 1
noop
noop
noop
addx -9
addx 18
addx 1
addx 2
noop
noop
addx 9
noop
noop
noop
addx -1
addx 2
addx -37
addx 1
addx 3
noop
addx 15
addx -21
addx 22
addx -6
addx 1
noop
addx 2
addx 1
noop
addx -10
noop
noop
addx 20
addx 1
addx 2
addx 2
addx -6
addx -11
noop
noop
noop
"""

class CathodeRayTubeKtTest {

    @Test
    fun parse() {
        assertEquals(
            listOf(
                State(1, 1),
                State(2, 1),
                State(3, 1),
                State(4, 4),
                State(5, 4),
            ), states(parse(EXAMPLE_ONE)).toList()
        )
    }

    @Test
    fun exampleOne() {
        assertEquals(
            0, signalStrengths(parse(EXAMPLE_ONE))
        )
    }

    @Test
    fun exampleTwo() {
        assertEquals(
            13140, signalStrengths(parse(EXAMPLE_TWO))
        )
    }

    @Test
    fun exampleThree() {
        val r = render(parse(EXAMPLE_TWO))
        println("-".repeat(40))
        println(r)
        println("-".repeat(40))
        assertEquals(
            """
                
                ##..##..##..##..##..##..##..##..##..##..
                ###...###...###...###...###...###...###.
                ####....####....####....####....####....
                #####.....#####.....#####.....#####.....
                ######......######......######......####
                #######.......#######.......#######.....
            """.trimIndent(), r
        )
    }

}
