package com.barneyb.util

import java.util.*

@Suppress("UNCHECKED_CAST")
class MinPQ<E : Any>(
    val comparator: Comparator<in E>? = null
) : Iterable<E> {

    var size = 0
        private set

    fun isEmpty() =
        size == 0

    fun isNotEmpty() =
        !isEmpty()

    private var heap = arrayOfNulls<Any>(11) as Array<E?>

    fun add(element: E) {
        if (size == heap.size) { // full, so double it
            heap = heap.copyOf(heap.size shl 1)
            assert(isValid())
        }
        heap[size] = element
        swim(size, element)
        size++
        assert(isValid())
    }

    private tailrec fun swim(ei: Int, e: E) {
        if (ei == 0) return
        val pi = (ei - 1) shr 1
        val p = heap[pi]!!
        if (less(e, p)) {
            swap(ei, pi)
            swim(pi, e)
        }
    }

    private fun swap(m: Int, n: Int) {
        val v = heap[m]
        heap[m] = heap[n]
        heap[n] = v
    }

    fun remove(): E {
        val result = peek()
        swap(0, --size)
        heap[size] = null
        if (size > 0)
            sink(0, heap[0]!!)
        assert(isValid())
        if (size < heap.size shr 2) // quarter full, so halve it
            heap = heap.copyOf(heap.size shr 1)
        assert(isValid())
        return result
    }

    private tailrec fun sink(ei: Int, e: E) {
        var ci = (ei shl 1) + 1
        if (ci >= size) // already at leaf
            return
        var c = heap[ci]!!
        val ri = ci + 1
        if (ri < size) {
            val r = heap[ri]!!
            if (less(r, c)) {
                ci = ri
                c = r
            }
        }
        if (less(c, e)) {
            swap(ei, ci)
            sink(ci, e)
        }
    }

    fun peek(): E =
        if (size == 0) throw NoSuchElementException()
        else heap[0]!!

    fun clear() {
        for (i in 0 until size) heap[i] = null
        size = 0
    }

    private fun isValid(): Boolean {
        for (i in 0 until size) {
            assert(heap[i] != null) {
                "Index $i is null?!"
            }
            var ci = i * 2 + 1
            assert(ci >= size || !less(heap[ci]!!, heap[i]!!)) {
                "${heap[i]} at $i is not less than ${heap[ci]} at $ci?!"
            }
            ci++
            assert(ci >= size || !less(heap[ci]!!, heap[i]!!)) {
                "${heap[i]} at $i is not less than ${heap[ci]} at $ci?!"
            }
        }
        for (i in size until heap.size) {
            assert(heap[i] == null) {
                "Index $i (unused) is not null?!"
            }
        }
        return true
    }

    private fun less(a: E, b: E) =
        if (comparator == null) (a as Comparable<E>) < b
        else comparator.compare(a, b) < 0

    override fun iterator(): Iterator<E> =
        object : Iterator<E> {
            var idx = 0
            override fun hasNext() =
                idx < size

            override fun next() =
                if (hasNext()) heap[idx++]!!
                else throw NoSuchElementException()
        }

}
