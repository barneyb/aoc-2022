package com.barneyb.util

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class StackTest {

    @Test
    fun doesItSmoke() {
        val s = Stack<Int>()
        assertEquals(0, s.size)
        assertEquals(0, s.size())
        assertTrue(s.isEmpty())
        assertFalse(s.isNotEmpty())
        assertThrows(NoSuchElementException::class.java) { s.peek() }
        assertThrows(NoSuchElementException::class.java) { s.pop() }
        s.push(123)
        assertEquals(1, s.size)
        assertEquals(1, s.size())
        assertFalse(s.isEmpty())
        assertTrue(s.isNotEmpty())
        assertEquals(123, s.peek())
        assertEquals(123, s.pop())
        assertTrue(s.isEmpty())
    }

    @Test
    fun equality() {
        val a = Stack<Int>()
        val b = Stack<Int>()
        assertEquals(a, b)
        assertEquals(a.hashCode(), b.hashCode())
        a.push(1, 2)
        b.push(1)
        b.push(2)
        assertEquals(2, a.peek())
        assertEquals(a, b)
        assertEquals(a.hashCode(), b.hashCode())
        b.push(99999)
        assertNotEquals(a, b)
        assertNotEquals(a.hashCode(), b.hashCode())
    }

    @Test
    fun testToString() {
        assertEquals("[]", Stack<Int>().toString())
        assertEquals("[3, 2, 1]", Stack(1, 2, 3).toString())
    }
}
