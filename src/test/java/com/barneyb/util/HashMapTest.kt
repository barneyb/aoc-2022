package com.barneyb.util

import com.barneyb.aoc.util.Input
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows


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
        assertThrows<HashMap.UnknownKeyException> {
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
                1 to 'a',
                2 to 'b',
            ), m.toSet()
        )
    }

    @Test
    fun frequencies() {
        abstract class ST<K : Any, V> {
            abstract operator fun get(key: K): V
            abstract operator fun set(key: K, value: V)
            abstract operator fun contains(key: K): Boolean
            abstract val size: Int
            abstract val keys: Iterable<K>

            fun isEmpty() =
                size == 0
        }

        class JavaST<K : Any, V>() : ST<K, V>() {
            private val map = java.util.HashMap<K, V>()

            override val size
                get() = map.size

            override val keys
                get() = map.keys

            override fun get(key: K) =
                map[key]!!

            override fun set(key: K, value: V) {
                map[key] = value
            }

            override fun contains(key: K) =
                map.contains(key)
        }

        class BarneyST<K : Any, V>() : ST<K, V>() {
            private val map = HashMap<K, V>()

            override val size
                get() = map.size

            override val keys
                get() = map.keys

            override fun get(key: K) =
                map[key]

            override fun set(key: K, value: V) {
                map[key] = value
            }

            override fun contains(key: K) =
                map.contains(key)
        }

        data class Res(
            val max: String,
            val count: Int,
            val size: Int,
            val total: Int
        )

        fun bench(
            st: ST<String, Int>,
            allWords: Collection<String>
        ): Res {
            if (!st.isEmpty()) throw IllegalArgumentException("Must pass an empty ST")
            for (word in allWords) {
                if (word.length < 5) continue
                if (!st.contains(word)) st[word] = 1
                else st[word] = st[word] + 1
            }
            var max = ""
            st[max] = 0
            var total = 0
            for (word in st.keys) {
                val n = st.get(word)
                total += n
                if (n > st.get(max)) {
                    max = word
                }
            }
            return Res(max, st.get(max), st.size, total)
        }

        val listOfWords = Input.asString("ulysses.txt")
            .lowercase()
            .split(Regex("[^a-z]+"))
        val java = Timing.benchmark(75) {
            bench(JavaST(), listOfWords)
        }
        val barney = Timing.benchmark(75) {
            bench(BarneyST(), listOfWords)
        }
        assertEquals(java.result.count, barney.result.count)
        assertEquals(java.result.total, barney.result.total)
        assertEquals(java.result.size, barney.result.size)
        assertTrue(barney.elapsed < java.elapsed * 2)
    }

}
