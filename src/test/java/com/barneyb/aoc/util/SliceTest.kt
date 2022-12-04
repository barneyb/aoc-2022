package com.barneyb.aoc.util

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class SliceTest {

    @Test
    fun trim() {
        val aCow = Slice("a cow yo!")
        listOf(
            Slice("cow"),
            Slice("   cow   "),
            Slice("   cow"),
            Slice("cow   "),
            aCow.subSequence(2, 5),
            aCow.subSequence(1, 6),
            aCow.subSequence(1, 5),
            aCow.subSequence(2, 6),
        ).forEach {
            assertEquals("cow", it.trim().toString(), "single-trim '$it'")
            assertEquals(
                "cow",
                it.trim().trim().toString(),
                "double-trim '$it'"
            )
        }
    }

    @Test
    fun trimDegenerates() {
        assertEquals("", Slice("").trim().toString())
        assertEquals("", Slice("      ").trim().toString())
        assertEquals("", Slice(" \n\t\r ").trim().toString())
        assertEquals("", Slice("   hai ").subSequence(1, 2).trim().toString())
    }

    @Test
    fun hash() {
        listOf(
            "",
            " ",
            "cow",
            "123",
        ).forEach {
            assertEquals(
                it.toCharArray().contentHashCode(),
                Slice(it).hashCode(),
                "wrong hash for '$it'"
            )
        }
        assertEquals(
            "cow".toCharArray().contentHashCode(),
            Slice("a cow, yo!").subSequence(2, 5).hashCode()
        )
    }

    @Test
    fun equality() {
        assertEquals(
            Slice("cow"),
            Slice("a cow, yo!").subSequence(2, 5)
        )
    }

}
