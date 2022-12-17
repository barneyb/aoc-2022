package com.barneyb.aoc.aoc2022.day16

internal data class Solo(
    override val minutesLeft: Int,
    override val valve: Valve,
    override val projected: Int = 0,
    val open: Set<Valve> = emptySet(),
    override val rate: Int = 0,
) : Step<Solo> {

    fun open() =
        copy(
            minutesLeft = minutesLeft - 1,
            projected = projected + valve.rate * minutesLeft,
            open = open + valve,
            rate = rate + valve.rate
        )

    override fun isOpen(v: Valve) =
        open.contains(v)

    private fun moveTo(v: Valve, dist: Int) =
        copy(
            minutesLeft = minutesLeft - dist,
            valve = v,
        )

    override fun moveAndOpen(v: Valve, dist: Int) =
        moveTo(v, dist).open()
}
