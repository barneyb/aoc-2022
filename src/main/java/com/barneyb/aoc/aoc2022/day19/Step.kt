package com.barneyb.aoc.aoc2022.day19

data class Step(
    val minute: Int,
    val rate: IntArray,
    val pool: IntArray,
    val parent: Step? = null, // NOT PART OF EQUALITY!
) {
    constructor() : this(
        0, // start with
        intArrayOf(1, 0, 0, 0), // one ore bot
        intArrayOf(0, 0, 0, 0), // an empty pool
    )

    val geodeCount get() = pool[3]

    fun tick() =
        copy(
            minute = minute + 1,
            pool = pool + rate,
            parent = this,
        )

    fun build(
        cost: IntArray,
        robot: IntArray,
        withinMinutes: Int = 99999
    ): Step? {
        // building takes a turn after all resources are available
        val readyIn = 1 + cost.indices.maxOf { i ->
            val needed = cost[i] - pool[i]
            if (needed <= 0) return@maxOf 0
            val rate = rate[i]
            if (rate == 0) return@maxOf 99999
            var turns = needed / rate
            if (turns == 0 || needed % rate > 0) turns++
            turns.coerceAtLeast(0)
        }
        return if (readyIn <= withinMinutes)
            copy(
                minute = minute + readyIn,
                rate = rate + robot,
                pool = pool + rate * readyIn - cost,
                parent = this,
            )
        else
            null
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Step

        if (minute != other.minute) return false
        if (!rate.contentEquals(other.rate)) return false
        if (!pool.contentEquals(other.pool)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = minute
        result = 31 * result + rate.contentHashCode()
        result = 31 * result + pool.contentHashCode()
        return result
    }
}

internal operator fun IntArray.plus(other: IntArray) =
    copyOf().apply { indices.forEach { set(it, get(it) + other[it]) } }

internal operator fun IntArray.minus(other: IntArray) =
    copyOf().apply { indices.forEach { set(it, get(it) - other[it]) } }

internal operator fun IntArray.times(scalar: Int) =
    copyOf().apply { indices.forEach { set(it, get(it) * scalar) } }
