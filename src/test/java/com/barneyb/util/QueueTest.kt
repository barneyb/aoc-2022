package com.barneyb.util

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class QueueTest {

    @Test
    fun doesItSmoke() {
        val s = Queue<Int>()
        assertEquals(0, s.size)
        assertTrue(s.isEmpty())
        assertFalse(s.isNotEmpty())
        assertThrows(NoSuchElementException::class.java) { s.peek() }
        assertThrows(NoSuchElementException::class.java) { s.dequeue() }
        s.enqueue(123)
        assertEquals(1, s.size)
        assertFalse(s.isEmpty())
        assertTrue(s.isNotEmpty())
        assertEquals(123, s.peek())
        assertEquals(123, s.dequeue())
        assertTrue(s.isEmpty())
    }

    @Test
    fun equality() {
        val a = Queue<Int>()
        val b = Queue<Int>()
        assertEquals(a, b)
        assertEquals(a.hashCode(), b.hashCode())
        a.enqueue(1, 2)
        b.enqueue(1)
        b.enqueue(2)
        assertEquals(1, a.peek())
        assertEquals(a, b)
        assertEquals(a.hashCode(), b.hashCode())
        b.enqueue(99999)
        assertNotEquals(a, b)
        assertNotEquals(a.hashCode(), b.hashCode())
    }

    @Test
    fun testToString() {
        assertEquals("[]", Queue<Int>().toString())
        assertEquals("[1, 2, 3]", Queue(1, 2, 3).toString())
    }

    @Test
    fun clone() {
        val a = Queue(1, 2, 3)
        val b = a.clone()
        assertEquals(a, b)
        assertEquals(a::class, b::class)
        assertEquals(a::class.java, b::class.java)
        assertNotSame(a, b)

        b.enqueue(99999)
        assertNotEquals(a, b)
        a.enqueue(99999)
        assertEquals(a, b)
    }

}
