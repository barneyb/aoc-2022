package com.barneyb.aoc.util

import java.util.*

class Slice(
    private val arr: CharArray,
    private val start: Int,
    private val end: Int
) : CharSequence, Iterable<Char> {

    private var hash: Int = 0

    constructor(arr: CharArray) : this(arr, 0, arr.size)

    constructor(str: String) : this(str.toCharArray())

    override val length
        get() = end - start

    @Suppress("MemberVisibilityCanBePrivate") // "override" Kotlin's extension method
    fun isEmpty() =
        start == end

    override fun get(index: Int) =
        arr[start + index]

    /**
     * Offer sub-slicing via indexing syntax: `s.subSequence(n, m) == s[n, m]`.
     */
    operator fun get(startIndex: Int, endIndex: Int) =
        subSequence(startIndex, endIndex)

    override fun subSequence(startIndex: Int, endIndex: Int) =
        Slice(arr, start + startIndex, start + endIndex)

    override fun equals(other: Any?) =
        other is Slice &&
                other.length == length &&
                Arrays.equals(
                    arr,
                    start,
                    end,
                    other.arr,
                    other.start,
                    other.end
                )

    override fun hashCode(): Int {
        if (hash == 0) {
            var result = 1
            for (i in start until end)
                result = 31 * result + arr[i].code
            hash = result
        }
        return hash
    }

    override fun iterator() =
        object : Iterator<Char> {
            var curr = start
            val end = this@Slice.end

            override fun hasNext() =
                curr < end

            override fun next() =
                if (hasNext())
                    arr[curr++]
                else
                    throw NoSuchElementException()

        }

    override fun toString() =
        String(arr, start, length)

    fun trim(): Slice {
        if (isEmpty()) return this
        var s = start
        var e = end - 1
        while (s < e && arr[s].isWhitespace()) s++
        while (e >= s && arr[e].isWhitespace()) e--
        return Slice(arr, s, e + 1)
    }

    fun lines() =
        split('\n')

    fun split(delim: Char) =
        split(delim.toString())

    fun split(delim: String) =
        buildList {
            var start = 0
            while (true) {
                val idx = indexOf(delim, start)
                if (idx < 0) break
                add(subSequence(start, idx))
                start = idx + delim.length
            }
            if (start < length) {
                add(subSequence(start, length))
            }
        }
}

fun String.toSlice() =
    Slice(this)
