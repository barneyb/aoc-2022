package com.barneyb.util

class Queue<E>(vararg elements: E) : Iterable<E>, Cloneable {

    private var head: Node<E>? = null
    private var tail: Node<E>? = null
    var size = 0
        private set

    init {
        push(*elements)
    }

    fun push(element: E) {
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

    fun push(vararg elements: E) {
        elements.forEach(this::push)
    }

    fun peek(): E {
        val h = head ?: throw NoSuchElementException()
        return h.value
    }

    fun pop(): E {
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

    fun size() =
        size

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