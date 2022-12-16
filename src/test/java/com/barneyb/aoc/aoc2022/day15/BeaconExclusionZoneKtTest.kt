package com.barneyb.aoc.aoc2022.day15

import com.barneyb.aoc.util.Input
import com.barneyb.util.HashSet
import com.barneyb.util.Timing
import com.barneyb.util.Vec2
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

private const val EXAMPLE_ONE = """
Sensor at x=2, y=18: closest beacon is at x=-2, y=15
Sensor at x=9, y=16: closest beacon is at x=10, y=16
Sensor at x=13, y=2: closest beacon is at x=15, y=3
Sensor at x=12, y=14: closest beacon is at x=10, y=16
Sensor at x=10, y=20: closest beacon is at x=10, y=16
Sensor at x=14, y=17: closest beacon is at x=10, y=16
Sensor at x=8, y=7: closest beacon is at x=2, y=10
Sensor at x=2, y=0: closest beacon is at x=2, y=10
Sensor at x=0, y=11: closest beacon is at x=2, y=10
Sensor at x=20, y=14: closest beacon is at x=25, y=17
Sensor at x=17, y=20: closest beacon is at x=21, y=22
Sensor at x=16, y=7: closest beacon is at x=15, y=3
Sensor at x=14, y=3: closest beacon is at x=15, y=3
Sensor at x=20, y=1: closest beacon is at x=15, y=3
"""

class BeaconExclusionZoneKtTest {

    @Test
    fun parsing() {
        assertEquals(
            Model(
                listOf(
                    Sensor(0, Vec2(2, 18), 7)
                ),
                HashSet(
                    Vec2(-2, 15)
                )
            ),
            parse(EXAMPLE_ONE.lines().subList(1, 2).joinToString("\n"))
        )
    }

    @Test
    fun print() {
        val actual = parse(EXAMPLE_ONE).draw(/*Rect(0, 0, 20, 20)*/)
        println("=".repeat(80))
        println(actual)
        println("=".repeat(80))
        assertEquals(
            """
            
                 |0        |10       |20       
             0 ....S.......................
             1 ......................S.....
             2 ...............S............
             3 ................SB..........
             4 ............................
             5 ............................
             6 ............................
             7 ..........S.......S.........
             8 ............................
             9 ............................
            10 ....B.......................
            11 ..S.........................
            12 ............................
            13 ............................
            14 ..............S.......S.....
            15 B...........................
            16 ...........SB...............
            17 ................S..........B
            18 ....S.......................
            19 ............................
            20 ............S......S........
            21 ............................
            22 .......................B....
            """.trimIndent(), actual
        )
    }

    @Test
    fun exampleOne() {
        assertEquals(26, countNonBeaconPositionsOnRow(parse(EXAMPLE_ONE), 10))
    }

    @Test
    fun tuneFreq() {
        assertEquals(56000011, Vec2(14, 11).tuningFrequency)
    }

    @Test
    fun exampleTwo() {
        assertEquals(
            56000011,
            distressBeaconTuningFrequency(parse(EXAMPLE_ONE), 0, 20),
        )
    }

    @Test
    fun perf() {
        val model = parse(Input.forProblem(::parse))
        val r1 = Timing.benchmark(100) { countNonBeaconPositionsOnRow(model) }
        assertEquals(5_878_678, r1.result)
        val r2 = Timing.benchmark(100) { distressBeaconTuningFrequency(model) }
        assertEquals(11_796_491_041_245, r2.result)
    }

}
