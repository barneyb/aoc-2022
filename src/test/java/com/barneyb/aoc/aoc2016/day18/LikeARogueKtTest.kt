package com.barneyb.aoc.aoc2016.day18

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class LikeARogueKtTest {

    @Test
    fun exampleOne() {
        assertEquals(6, safeTileCount("..^^.", 3))
    }

    @Test
    fun exampleTwo() {
        assertEquals(38, safeTileCount(".^^.^.^^^^", 10))
    }

}
