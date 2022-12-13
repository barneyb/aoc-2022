package com.barneyb.util

/**
 * I am a simple FIFO queue using linked storage.
 */
class Queue<E>(vararg elements: E) : Iterable<E>, Cloneable {

    private var head: Node<E>? = null
    private var tail: Node<E>? = null
    var size = 0
        private set

    init {
        enqueue(*elements)
    }

    fun enqueue(element: E) {
        val t = tail
        if (t == null) {
            // empty
            tail = Node(element)
            head = tail
        } else {
            t.next = Node(element)
            tail = t.next
        }
        size++
    }

    fun enqueue(vararg elements: E) {
        elements.forEach(this::enqueue)
    }

    fun peek(): E {
        val h = head ?: throw NoSuchElementException()
        return h.value
    }

    fun dequeue(): E {
        val h = head ?: throw NoSuchElementException()
        if (h.next == null) {
            // only item
            head = null
            tail = null
        } else {
            head = h.next
        }
        size--
        return h.value
    }

    fun isEmpty() =
        head == null

    fun isNotEmpty() =
        !isEmpty()

    public override fun clone(): Queue<E> {
        @Suppress("UNCHECKED_CAST")
        return (super.clone() as Queue<E>).apply {
            head = null
            tail = null
            for (v in this@Queue) {
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
        if (other !is Queue<*>) return false
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
