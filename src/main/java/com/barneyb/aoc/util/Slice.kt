package com.barneyb.aoc.util

import java.util.*

class Slice(
    private val arr: CharArray,
    private val start: Int,
    private val end: Int
) : CharSequence {

    constructor(arr: CharArray) : this(arr, 0, arr.size)

    constructor(str: String) : this(str.toCharArray())

    override val length
        get() = end - start

    override fun get(index: Int) =
        arr[start + index]

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
        var result = 1
        for (i in start until end)
            result = 31 * result + arr[i].code
        return result
    }

    override fun toString() =
        String(arr, start, length)

    fun trim(): Slice {
        var s = start
        while (arr[s].isWhitespace()) s++
        var e = end
        while (arr[e - 1].isWhitespace()) e--
        return Slice(arr, s, e)
    }

    fun lines() =
        buildList {
            var start = 0
            while (true) {
                val idx = indexOf('\n', start)
                if (idx < 0) break
                add(subSequence(start, idx))
                start = idx + 1
            }
            if (start < length) {
                add(subSequence(start, length))
            }
        }
}

fun String.toSlice() =
    Slice(this)
