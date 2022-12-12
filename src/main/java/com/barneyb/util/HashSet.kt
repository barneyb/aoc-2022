package com.barneyb.util

class HashSet<E : Any> : Iterable<E> {

    private val map = HashMap<E, Unit>()

    fun add(element: E) =
        map.set(element, Unit)

    fun remove(element: E) =
        map.remove(element)

    fun contains(element: E) =
        map.contains(element)

    override fun equals(other: Any?): Boolean {
        return other is HashSet<*> &&
                other.map == map
    }

    override fun hashCode(): Int {
        return map.hashCode()
    }

    override fun toString() =
        joinToString(prefix = "[", postfix = "]")

    override fun iterator(): Iterator<E> {
        return map.keys.iterator()
    }
}
