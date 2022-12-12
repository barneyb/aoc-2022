package com.barneyb.util

import kotlin.Pair

data class Entry<K, V>(val key: K, var value: V)

class UnknownKeyException(key: Any) :
    IllegalArgumentException("No '$key' is known")

class HashMap<K : Any, V>(initialCapacity: Int = 10) : Iterable<Entry<K, V>> {

    private var buckets = Array<Stack<Entry<K, V>>>(initialCapacity) { Stack() }
    var size = 0
        private set

    val keys: Iterable<K>
        get() = object : Iterable<K> {
            override fun iterator() =
                MappingIterator(this@HashMap.iterator(), Entry<K, *>::key)
        }

    val values: Iterable<V>
        get() = object : Iterable<V> {
            override fun iterator() =
                MappingIterator(this@HashMap.iterator(), Entry<*, V>::value)
        }

    constructor(vararg entries: Pair<K, V>) : this(entries.size * 2) {
        for (e in entries) {
            set(e.first, e.second)
        }
    }

    operator fun get(key: K): V {
        return entry(key)?.value
            ?: throw UnknownKeyException(key)
    }

    operator fun set(key: K, value: V) {
        val idx = index(key)
        val e = entryInBucket(buckets[idx], key)
        if (e != null) {
            e.value = value
            return
        }
        buckets[idx].push(Entry(key, value))
        if (++size > buckets.size / 4 * 3) {
            rehash(buckets.size * 2)
        }
    }

    fun remove(key: Any) {
        val idx = index(key)
        val e = entryInBucket(buckets[idx], key)
        if (e != null) {
            buckets[idx].remove(e)
        }
        if (--size < buckets.size / 4) {
            rehash(buckets.size / 2)
        }
    }

    private fun rehash(cap: Int) {
        val next = Array<Stack<Entry<K, V>>>(cap) { Stack() }
        for (e in iterator()) {
            next[index(e.key)].push(e)
        }
        buckets = next
    }

    fun contains(key: Any): Boolean {
        return entry(key) != null
    }

    private fun entry(key: Any): Entry<K, V>? {
        return entryInBucket(buckets[index(key)], key)
    }

    private fun entryInBucket(
        bucket: Stack<Entry<K, V>>,
        key: Any
    ): Entry<K, V>? {
        for (e in bucket) {
            if (key == e.key) return e
        }
        return null
    }

    private fun index(key: Any) =
        key.hashCode() % buckets.size

    override fun equals(other: Any?): Boolean {
        if (other !is HashMap<*, *>) return false
        if (other.size != size) return false
        for (a in other) {
            val b = entry(a.key)
            if (b == null || a.value != b.value) return false
        }
        return true
    }

    override fun hashCode(): Int {
        var hash = 17
        for (e in this) {
            hash = hash * 31 + e.key.hashCode()
            hash = hash * 31 + e.value.hashCode()
        }
        return hash
    }

    override fun toString() =
        buildString {
            var started = false
            append('{')
            for (e in this@HashMap) {
                if (started) {
                    append(", ")
                } else {
                    started = true
                }
                append(e.key)
                append(": ")
                append(e.value)
            }
            append('}')
        }

    override fun iterator(): Iterator<Entry<K, V>> =
        object : Iterator<Entry<K, V>> {
            private var idx = 0
            private var itr: Iterator<Entry<K, V>> = buckets[idx].iterator()

            override fun hasNext(): Boolean {
                while (!itr.hasNext() && ++idx < buckets.size) {
                    itr = buckets[idx].iterator()
                }
                return itr.hasNext()
            }

            override fun next(): Entry<K, V> {
                if (!hasNext()) {
                    throw NoSuchElementException()
                }
                return itr.next()
            }
        }

}
