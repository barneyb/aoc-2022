package com.barneyb.util

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals

class HashMapTest {

    @Test
    fun doesItSmoke() {
        val m = HashMap(
            1 to 'a',
            2 to 'b'
        )
        assertEquals('a', m[1])
        m[1] = 'q'
        assertEquals('q', m[1])
        m.remove(1)
        assertThrows<UnknownKeyException> {
            m[1]
        }
    }

    @Test
    fun sizeWorks() {
        val m = HashMap(
            1 to 'a',
            2 to 'b'
        )
        assertEquals(2, m.size)
        m[1] = 'q'
        assertEquals(2, m.size)
        m[26] = 'z'
        assertEquals(3, m.size)
        m.remove(1)
        assertEquals(2, m.size)
    }

    @Test
    fun iterators() {
        val m = HashMap(
            1 to 'a',
            2 to 'b',
        )
        assertEquals(setOf(1, 2), m.keys.toSet())
        assertEquals(setOf('a', 'b'), m.values.toSet())
        assertEquals(
            setOf(
                Entry(1, 'a'),
                Entry(2, 'b'),
            ), m.toSet()
        )
    }

}
