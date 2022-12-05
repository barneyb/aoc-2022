package com.barneyb.aoc.util

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

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

    @Test
    fun iterator() {
        assertFalse(Slice("").iterator().hasNext())
        assertThrows<NoSuchElementException> {
            Slice("").iterator().next()
        }
        assertTrue(Slice(" ").iterator().hasNext())
        val itr = Slice("cow").iterator()
        assertTrue(itr.hasNext())
        assertEquals('c', itr.next())
        assertEquals('o', itr.next())
        assertTrue(itr.hasNext())
        assertEquals('w', itr.next())
        assertFalse(itr.hasNext())
    }

    @Test
    fun splitting() {
        assertEquals(
            listOf(
                Slice("a"),
                Slice("b"),
                Slice(" 123"),
                Slice(" d"),
            ),
            Slice("a,b, 123, d")
                .split(',')
        )
    }
}
