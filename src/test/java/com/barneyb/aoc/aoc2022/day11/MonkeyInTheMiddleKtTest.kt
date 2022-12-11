package com.barneyb.aoc.aoc2022.day11

import com.barneyb.util.Queue
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

private const val EXAMPLE_ONE = """
Monkey 0:
  Starting items: 79, 98
  Operation: new = old * 19
  Test: divisible by 23
    If true: throw to monkey 2
    If false: throw to monkey 3

Monkey 1:
  Starting items: 54, 65, 75, 74
  Operation: new = old + 6
  Test: divisible by 19
    If true: throw to monkey 2
    If false: throw to monkey 0

Monkey 2:
  Starting items: 79, 60, 97
  Operation: new = old * old
  Test: divisible by 13
    If true: throw to monkey 1
    If false: throw to monkey 3

Monkey 3:
  Starting items: 74
  Operation: new = old + 3
  Test: divisible by 17
    If true: throw to monkey 0
    If false: throw to monkey 1
"""

class MonkeyInTheMiddleKtTest {

    @Test
    fun parsing() {
        assertEquals(Queue(79L, 98L), parseMonkey(parse(EXAMPLE_ONE)[0]).items)
    }

    @Test
    fun exampleOne() {
        assertEquals(10605, partOne(parse(EXAMPLE_ONE)))
    }

    @Test
    fun exampleTwo() {
        assertEquals(2713310158, partTwo(parse(EXAMPLE_ONE)))
    }
}
