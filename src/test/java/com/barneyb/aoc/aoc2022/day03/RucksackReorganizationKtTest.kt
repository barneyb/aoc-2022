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
        assertEquals(12, r.left.size)
        assertEquals(12, r.right.size)
        assertEquals(7/*g*/, r.left.first())
        assertEquals(49/*W*/, r.left.last())
        assertEquals(16/*p*/, r.intersection)
    }

    @Test
    fun exampleOne() {
        assertEquals(157, partOne(parse(EXAMPLE_ONE)))
    }

}
