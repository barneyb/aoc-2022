package com.barneyb.util

import java.util.*

class Stack<E>(vararg elements: E) : Iterable<E> {
    private data class Node<E>(val value: E, var next: Node<E>?)

    private var head: Node<E>? = null
    var size = 0
        private set

    init {
        push(*elements)
    }

    fun push(element: E) {
        head = Node(element, head)
        size++
    }

    fun push(vararg elements: E) {
        elements.forEach(this::push)
    }

    fun peek(): E {
        val h = head ?: throw NoSuchElementException()
        return h.value
    }

    fun pop(): E {
        val h = head ?: throw NoSuchElementException()
        head = h.next
        size--
        return h.value
    }

    fun size() =
        size

    fun isEmpty() =
        head == null

    fun isNotEmpty() =
        !isEmpty()

    override fun equals(other: Any?): Boolean {
        if (other !is Stack<*>) return false
        if (other.size != size) return false
        val a = iterator()
        val b = other.iterator()
        while (a.hasNext()) {
            if (a.next() != b.next()) {
                return false
            }
        }
        return true
    }

    override fun hashCode() =
        iterator().asSequence().fold(0) { hc, it ->
            hc * 31 + Objects.hashCode(it)
        }

    override fun iterator() =
        object : Iterator<E> {
            private var curr = head
            override fun hasNext(): Boolean =
                curr != null

            override fun next(): E {
                val c = curr ?: throw NoSuchElementException()
                curr = c.next
                return c.value
            }

        }

    override fun toString() =
        buildString {
            append('[')
            val itr = this@Stack.iterator()
            if (itr.hasNext()) {
                append(itr.next())
            }
            while (itr.hasNext()) {
                append(", ")
                append(itr.next())
            }
            append(']')
        }
}
