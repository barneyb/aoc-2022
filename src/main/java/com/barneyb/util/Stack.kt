package com.barneyb.util

import java.util.*

/**
 * I am a simple LIFO stack using linked storage.
 */
class Stack<E>(vararg elements: E) : Iterable<E>, Cloneable {

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

    fun remove(element: E) {
        var prev: Node<E>? = null
        var curr = head
        while (curr != null) {
            if (curr.value == element) {
                if (prev == null) {
                    head = curr.next
                } else { // middle
                    prev.next = curr.next
                }
                curr.next = null
                return
            }
            prev = curr
            curr = curr.next
        }
    }

    fun size() =
        size

    fun isEmpty() =
        head == null

    fun isNotEmpty() =
        !isEmpty()

    public override fun clone(): Stack<E> {
        @Suppress("UNCHECKED_CAST")
        return (super.clone() as Stack<E>).apply {
            head = null
            var tail: Node<E>? = null
            for (v in this@Stack) {
                if (head == null) {
                    tail = Node(v, null)
                    head = tail
                } else {
                    tail?.next = Node(v, null)
                    tail = tail?.next
                }
            }
        }
    }

    override fun equals(other: Any?): Boolean {
        if (other !is Stack<*>) return false
        if (other.size != size) return false
        return iterableEquals(this, other)
    }

    override fun hashCode() =
        iterableHashCode(this)

    override fun iterator() =
        linkedIterator(head)

    override fun toString() =
        joinToString(prefix = "[", postfix = "]")
}
