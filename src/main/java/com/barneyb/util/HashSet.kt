package com.barneyb.util

class HashSet<E : Any>(vararg elements: E) : Iterable<E> {

    private val map = HashMap<E, Unit>()

    init {
        add(*elements)
    }

    val size
        get() = map.size

    fun add(element: E) =
        map.put(element, Unit)

    fun add(vararg elements: E) =
        elements.forEach(::add)

    fun addAll(other: Iterable<E>) =
        other.forEach(::add)

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
