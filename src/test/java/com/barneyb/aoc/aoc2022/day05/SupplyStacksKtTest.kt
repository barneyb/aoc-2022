package com.barneyb.aoc.aoc2022.day05

import com.barneyb.util.Stack
import org.junit.jupiter.api.Assertions.assertArrayEquals
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

private const val EXAMPLE_ONE = """
    [D]    
[N] [C]    
[Z] [M] [P]
 1   2   3 

move 1 from 2 to 1
move 3 from 1 to 3
move 2 from 2 to 1
move 1 from 1 to 2
"""

class SupplyStacksKtTest {

    @Test
    fun parseLayout() {
        val stacks = parse(EXAMPLE_ONE).freshStacks()
        assertEquals('N', stacks[0].peek())
        assertArrayEquals(
            arrayOf(
                Stack('Z', 'N'),
                Stack('M', 'C', 'D'),
                Stack('P'),
            ), stacks
        )
    }

    @Test
    fun parseInstructions() {
        assertEquals(
            listOf(
                Ins(1, 1, 0),
                Ins(3, 0, 2),
                Ins(2, 1, 0),
                Ins(1, 0, 1),
            ), parse(EXAMPLE_ONE).instructions
        )
    }

    @Test
    fun exampleOne() {
        assertEquals(
            "CMZ",
            crateMover9000(parse(EXAMPLE_ONE))
        )
    }

    @Test
    fun exampleTwo() {
        assertEquals(
            "MCD",
            crateMover9001(parse(EXAMPLE_ONE))
        )
    }
}
