package com.barneyb.aoc.aoc2022.day21

import com.barneyb.aoc.util.toSlice
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

private const val EXAMPLE_ONE = """
root: pppw + sjmn
dbpl: 5
pppw: cczh / lfqf
cczh: sllz + lgvd
zczc: 2
ptdq: humn - dvpt
dvpt: 3
lfqf: 4
humn: 5
ljgn: 2
sjmn: drzm * dbpl
sllz: 4
lgvd: ljgn * ptdq
drzm: hmdt - zczc
hmdt: 32
"""

class MonkeyMathKtTest {

    @Test
    fun parsing() {
        assertEquals(
            mapOf(
                "root".toSlice() to Operation(
                    "pppw".toSlice(),
                    "sjmn".toSlice(),
                    '+'
                ),
                "dbpl".toSlice() to Yell(5),
                "pppw".toSlice() to Operation(
                    "cczh".toSlice(),
                    "lfqf".toSlice(),
                    '/'
                ),
            ),
            parse(EXAMPLE_ONE.trim().lines().take(3).joinToString("\n"))
        )
    }

    @Test
    fun exampleOne() {
        assertEquals(152, rootYellsWhat(parse(EXAMPLE_ONE)))
    }
}
