package com.barneyb.util

internal data class Node<E>(
    val value: E,
    var next: Node<E>? = null,
)

internal fun <E> linkedIterator(head: Node<E>?) =
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
