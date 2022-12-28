package com.barneyb.util

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

class MinPQTest {

    @Test
    fun getSize() {
        val pq = MinPQ<Int>()
        assertEquals(0, pq.size)
        pq.add(2)
        assertEquals(1, pq.size)
        pq.remove()
        assertEquals(0, pq.size)
    }

    @Test
    fun peek() {
        val pq = MinPQ<Int>()
        assertThrows<NoSuchElementException> { pq.peek() }
        pq.add(2)
        assertEquals(2, pq.peek())
        pq.add(1)
        assertEquals(1, pq.peek())
    }

    @Test
    fun comparator() {
        assertNull(MinPQ<Int>().comparator)
        val pq = MinPQ(Comparator.naturalOrder<Int>().reversed())
        assertTrue(pq.comparator!!.compare(5, 3) < 0)
        val ns = listOf(100, 36, 25, 19, 17, 7, 3, 2, 1)
        ns.forEach(pq::add)
        ns.forEach {
            assertEquals(it, pq.remove())
        }
        assertTrue(pq.isEmpty())
        ns.shuffled().forEach(pq::add)
        ns.forEach {
            assertEquals(it, pq.remove())
        }
    }

    @Test
    fun growAndShrink() {
        val pq = MinPQ<Int>()
        (0 until 100).shuffled()
            .forEach(pq::add)
        assertEquals(100, pq.size)
        assertEquals(0, pq.peek())
        repeat(100) {
            assertEquals(it, pq.remove())
        }
        assertEquals(0, pq.size)
    }

    @Test
    fun iterator() {
        val pq = MinPQ<Int>()
        pq.add(2)
        pq.add(1)
        pq.add(3)
        val itr = pq.iterator()
        // head is first
        assertEquals(1, itr.next())
        // rest are arbitrarily ordered
        val theRest = mutableListOf<Int>()
        while (itr.hasNext()) theRest.add(itr.next())
        theRest.sort()
        assertEquals(listOf(2, 3), theRest)
    }

    @Test
    fun dupes() {
        val pq = MinPQ<Int>()
        pq.add(1)
        pq.add(2)
        pq.add(3)
        pq.add(2)
        pq.add(1)
        assertEquals(1, pq.remove())
        assertEquals(1, pq.remove())
        assertEquals(2, pq.remove())
        assertEquals(2, pq.remove())
        assertEquals(3, pq.remove())
    }
}
