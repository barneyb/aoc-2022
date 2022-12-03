package com.barneyb.aoc.aoc2019.day14

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

private const val EXAMPLE_ONE = "10 ORE => 10 A\n" +
        "1 ORE => 1 B\n" +
        "7 A, 1 B => 1 C\n" +
        "7 A, 1 C => 1 D\n" +
        "7 A, 1 D => 1 E\n" +
        "7 A, 1 E => 1 FUEL"

class SpaceStoichiometryKtTest {

    @Test
    fun parse() {
        println(parse(EXAMPLE_ONE))
    }

    @Test
    fun exampleOne() {
        assertEquals(
            31,
            requiredOre(parse(EXAMPLE_ONE), Reactant(1, ELEMENT_FUEL))
        )
    }

}
