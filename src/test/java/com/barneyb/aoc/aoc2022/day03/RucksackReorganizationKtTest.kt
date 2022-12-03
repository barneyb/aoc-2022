package com.barneyb.aoc.aoc2022.day03

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

private const val EXAMPLE_ONE = "vJrwpWtwJgWrhcsFMMfFFhFp\n" +
        "jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL\n" +
        "PmmdzqPrVvPwwTWBwg\n" +
        "wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn\n" +
        "ttgJtRGJQctTZtZT\n" +
        "CrZsJsPPZsGzwwsLwLmpwMDw\n"

class RucksackReorganizationKtTest {

    @Test
    fun parse() {
        val r = parse("vJrwpWtwJgWrhcsFMMfFFhFp").first()
        assertEquals(16/*p*/, r.intersection)
    }

    @Test
    fun exampleOne() {
        assertEquals(157, partOne(parse(EXAMPLE_ONE)))
    }

    @Test
    fun partTwoExampleOne() {
        assertEquals(70, partTwo(parse(EXAMPLE_ONE)))
    }

}
