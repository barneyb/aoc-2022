package com.barneyb.aoc.aoc2022.day16

internal data class Team(
    val minutesLefts: IntArray,
    val valves: Array<Valve>,
    override val projected: Int = 0,
    override val open: Set<Valve> = emptySet(),
    override val rate: Int = 0,
) : Step {

    constructor(minLeft: Int, valve: Valve) : this(
        intArrayOf(minLeft, minLeft),
        arrayOf(valve, valve)
    )

    val idx: Int =
        if (minutesLefts[0] >= minutesLefts[1]) 0 else 1

    override val minutesLeft
        get() = minutesLefts[idx]

    override val valve
        get() = valves[idx]

    override fun isOpen(v: Valve) =
        open.contains(v)

    override fun moveAndOpen(v: Valve, dist: Int) =
        copy(
            minutesLefts = minutesLefts.copyOf().apply {
                this[idx] -= dist + 1
            },
            valves = valves.copyOf().apply {
                this[idx] = v
            },
            projected = projected + v.rate * (minutesLefts[idx] - dist),
            open = open + v,
            rate = rate + v.rate,
        )
}
