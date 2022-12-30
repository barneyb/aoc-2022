package com.barneyb.aoc.aoc2022.day16

internal interface Step {
    fun moveAndOpen(v: Valve, dist: Int): Step
    fun isOpen(v: Valve): Boolean

    val minutesLeft: Int
    val valve: Valve
    val projected: Int
    val open: Set<Valve>
    val rate: Int
}
