package com.barneyb.aoc.aoc2022.day19

data class Step(
    val minute: Int,
    val rate: Long,
    val pool: Long,
//    val parent: Step? = null, // NOT PART OF EQUALITY!
) {
    // start with one ore bot and an empty pool
    constructor() : this(0, oneOre, 0)

    constructor(
        minute: Int,
        rate: IntArray,
        pool: IntArray,
    ) : this(minute, compress(rate), compress(pool))

    val geodeCount get() = (pool shr 48) and 0xFFFF

    fun getPotentialGeodeCount(minutes: Int): Long {
        // assume we'll get a new geode bot every minute
        var result = geodeCount
        var rate = rate shr 48
        repeat(minutes) {
            result += rate++
        }
        return result
    }

    fun tick(n: Int = 1) =
        copy(
            minute = minute + n,
            pool = pool + rate * n,
//            parent = this,
        )

    fun build(
        cost: IntArray,
        robot: IntArray,
        withinMinutes: Int = 99999,
    ) =
        build(compress(cost), compress(robot), withinMinutes)

    private fun turnsFor(cost: Long, shift: Int): Int {
        val needed = ((cost shr shift) and 0xFFFF) -
                ((pool shr shift) and 0xFFFF)
        if (needed <= 0) return 0
        val rate = (rate shr shift) and 0xFFFF
        if (rate == 0L) return 99999
        var turns = needed / rate
        if (turns == 0L || needed % rate > 0) turns++
        return turns.toInt().coerceAtLeast(0)
    }

    fun build(
        cost: Long,
        robot: Long,
        withinMinutes: Int = 99999,
    ): Step? {
        var readyIn = turnsFor(cost, 0)
        readyIn = readyIn.coerceAtLeast(turnsFor(cost, 16))
        readyIn = readyIn.coerceAtLeast(turnsFor(cost, 32))
        // building takes a turn after all resources are available
        return if (++readyIn <= withinMinutes)
            copy(
                minute = minute + readyIn,
                rate = rate + robot,
                pool = pool + rate * readyIn - cost,
//                parent = this,
            )
        else
            null
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Step

        if (minute != other.minute) return false
        if (rate != other.rate) return false
        if (pool != other.pool) return false

        return true
    }

    override fun hashCode(): Int {
        var result = minute
        result = 31 * result + rate.hashCode()
        result = 31 * result + pool.hashCode()
        return result
    }

    override fun toString() =
        buildString {
            append("Step(")
            append(minute)
            append(" rate:")
            append(decompress(rate).contentToString())
            append(" pool:")
            append(decompress(pool).contentToString())
            append(')')
        }

}
