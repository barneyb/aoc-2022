package com.barneyb.util

import java.util.*

class HashMap<K : Any, V>(initialCapacity: Int = 10) : Iterable<Pair<K, V>> {

    class UnknownKeyException(key: Any) :
        IllegalArgumentException("No '$key' is known")

    private data class Node<K, V>(
        val hash: Int,
        val key: K,
        var value: V,
        var next: Node<K, V>? = null,
    )

    private var bins = Array<Node<K, V>?>(initialCapacity) { null }
    var size = 0
        private set

    fun isEmpty() =
        size == 0

    fun isNotEmpty() =
        !isEmpty()

    val keys: Iterable<K>
        get() = object : Iterable<K> {
            override fun iterator() =
                MappingIterator(this@HashMap.iterator(), Pair<K, *>::first)
        }

    val values: Iterable<V>
        get() = object : Iterable<V> {
            override fun iterator() =
                MappingIterator(this@HashMap.iterator(), Pair<*, V>::second)
        }

    private val nodes: Iterable<Node<K, V>>
        get() = object : Iterable<Node<K, V>> {
            override fun iterator() =
                this@HashMap.nodeIterator()
        }

    constructor(vararg entries: Pair<K, V>) : this(entries.size * 2) {
        for (e in entries) {
            put(e.first, e.second)
        }
    }

    operator fun get(key: K): V {
        return node(key)?.value
            ?: throw UnknownKeyException(key)
    }

    fun put(key: K, value: V) {
        if (size >= bins.size / 4 * 3) {
            resize(bins.size * 2)
        }
        val hash = Objects.hashCode(key)
        val idx = index(hash)
        val n = nodeInBin(bins[idx], key)
        if (n != null) {
            n.value = value
            return
        }
        bins[idx] = Node(hash, key, value, bins[idx])
        size++
    }

    operator fun set(key: K, value: V) =
        put(key, value)

    fun remove(key: Any) {
        val hash = Objects.hashCode(key)
        val idx = index(hash)
        var prev: Node<K, V>? = null
        var curr = bins[idx]
        while (curr != null) {
            if (curr.key == key) {
                if (prev == null) {
                    bins[idx] = curr.next
                } else {
                    prev.next = curr.next
                    curr.next = null
                }
                size--
                if (size > 0 && size <= bins.size / 4) {
                    resize(bins.size / 2)
                }
                break
            }
            prev = curr
            curr = curr.next
        }
    }

    private fun resize(cap: Int) {
        val next = Array<Node<K, V>?>(cap) { null }
        for (n in nodeIterator()) {
            val idx = index(n.hash, next.size)
            next[idx] = n.copy(next = next[idx])
        }
        bins = next
    }

    fun contains(key: Any): Boolean {
        return node(key) != null
    }

    private fun node(key: Any): Node<K, V>? =
        node(Objects.hashCode(key), key)

    private fun node(hash: Int, key: Any): Node<K, V>? =
        nodeInBin(bins[index(hash)], key)

    private fun index(hash: Int, binCount: Int = bins.size) =
        (hash and 0x7fffffff) % binCount

    private fun nodeInBin(
        bin: Node<K, V>?,
        key: Any
    ): Node<K, V>? {
        var curr = bin
        while (curr != null) {
            if (key == curr.key) return curr
            curr = curr.next
        }
        return null
    }

    override fun equals(other: Any?): Boolean {
        if (other !is HashMap<*, *>) return false
        if (other.size != size) return false
        for (a in other.nodes) {
            val b = node(a.hash, a.key)
            if (b == null || a.value != b.value) return false
        }
        return true
    }

    override fun hashCode(): Int {
        var hash = 0
        for (n in nodes) {
            hash += n.hash xor Objects.hashCode(n.value)
        }
        return hash
    }

    override fun toString() =
        buildString {
            var started = false
            append('{')
            for (n in nodes) {
                if (started) {
                    append(", ")
                } else {
                    started = true
                }
                append(n.key)
                append(": ")
                append(n.value)
            }
            append('}')
        }

    private fun nodeIterator(): Iterator<Node<K, V>> =
        object : Iterator<Node<K, V>> {
            private var idx = -1
            private var curr: Node<K, V>? = null

            override fun hasNext(): Boolean {
                if (curr != null) return true
                while (curr == null) {
                    if (++idx >= bins.size) break
                    curr = bins[idx]
                }
                return curr != null
            }

            override fun next(): Node<K, V> {
                if (!hasNext()) throw NoSuchElementException()
                val c = curr!!
                curr = c.next
                return c
            }
        }

    override fun iterator(): Iterator<Pair<K, V>> =
        MappingIterator(this@HashMap.nodeIterator()) {
            Pair(it.key, it.value)
        }

}
