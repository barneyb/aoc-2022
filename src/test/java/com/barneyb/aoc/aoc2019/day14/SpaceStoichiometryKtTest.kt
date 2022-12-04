package com.barneyb.aoc.aoc2019.day14

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

private const val EXAMPLE_ONE = "10 ORE => 10 A\n" +
        "1 ORE => 1 B\n" +
        "7 A, 1 B => 1 C\n" +
        "7 A, 1 C => 1 D\n" +
        "7 A, 1 D => 1 E\n" +
        "7 A, 1 E => 1 FUEL"

private const val EXAMPLE_TWO = "157 ORE => 5 NZVS\n" +
        "165 ORE => 6 DCFZ\n" +
        "44 XJWVT, 5 KHKGT, 1 QDVJ, 29 NZVS, 9 GPVTF, 48 HKGWZ => 1 FUEL\n" +
        "12 HKGWZ, 1 GPVTF, 8 PSHF => 9 QDVJ\n" +
        "179 ORE => 7 PSHF\n" +
        "177 ORE => 5 HKGWZ\n" +
        "7 DCFZ, 7 PSHF => 2 XJWVT\n" +
        "165 ORE => 2 GPVTF\n" +
        "3 DCFZ, 7 NZVS, 5 HKGWZ, 10 PSHF => 8 KHKGT"

class SpaceStoichiometryKtTest {

    @Test
    fun parse() {
        println(parse(EXAMPLE_ONE))
    }

    @Test
    fun exampleOne() {
        assertEquals(
            31,
            requiredOreForFuel(parse(EXAMPLE_ONE), 1)
        )
    }

    @Test
    fun exampleTwo() {
        assertEquals(
            13312,
            requiredOreForFuel(parse(EXAMPLE_TWO), 1)
        )
    }

    @Test
    fun partTwo() {
        assertEquals(
            82892753,
            fuelFromOre(parse(EXAMPLE_TWO), ONE_TRILLION)
        )
    }
}
