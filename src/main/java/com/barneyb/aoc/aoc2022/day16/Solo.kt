package com.barneyb.aoc.aoc2022.day16

internal data class Solo(
    override val minutesLeft: Int,
    override val valve: Valve,
    override val projected: Int = 0,
    override val open: Set<Valve> = emptySet(),
    override val rate: Int = 0,
) : Step {

    override fun moveAndOpen(v: Valve, dist: Int) =
        copy(
            minutesLeft = minutesLeft - dist - 1,
            valve = v,
            projected = projected + v.rate * (minutesLeft - dist),
            open = open + v,
            rate = rate + v.rate
        )
}
