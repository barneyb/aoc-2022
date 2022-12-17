package com.barneyb.aoc.aoc2022.day16

internal data class Team(
    val minutesLefts: IntArray,
    val valves: Array<Valve>,
    override val projected: Int = 0,
    val open: Set<Valve> = emptySet(),
    override val rate: Int = 0,
    val idx: Int = 0,
) : Step<Team> {
    override val minutesLeft
        get() = minutesLefts[idx]

    override val valve
        get() = valves[idx]

    override fun canOpen() =
        minutesLeft > 1 && valves[idx].rate > 0 && !isOpen(valves[idx])

    override fun open(): Team {
        val mls = minutesLefts.copyOf()
        mls[idx] -= 1
        return copy(
            minutesLefts = mls,
            projected = projected + valves[idx].rate * minutesLefts[idx],
            open = open + valves[idx],
            rate = rate + valves[idx].rate,
            idx = nextIdx(mls),
        )
    }

    override fun isOpen(v: Valve) =
        open.contains(v)

    private fun nextIdx(mls: IntArray) =
        if (mls[0] >= mls[1]) 0 else 1

    override fun moveTo(v: Valve, dist: Int): Team {
        val mls = minutesLefts.copyOf()
        mls[idx] -= dist
        return copy(
            minutesLefts = mls,
            valves = valves.copyOf().apply {
                this[idx] = v
            },
            idx = nextIdx(mls),
        )
    }

    override fun moveAndOpen(v: Valve, dist: Int): Team {
        val idx = idx
        val n = moveTo(v, dist)
        val mls = n.minutesLefts.copyOf()
        mls[idx] -= 1
        return n.copy(
            minutesLefts = mls,
            projected = n.projected + n.valves[idx].rate * n.minutesLefts[idx],
            open = n.open + n.valves[idx],
            rate = n.rate + n.valves[idx].rate,
            idx = nextIdx(mls),
        )
    }
}
