package com.barneyb.aoc.aoc2022.day16

internal interface Step<T : Step<T>> {
    fun moveAndOpen(v: Valve, dist: Int): T
    fun isOpen(v: Valve): Boolean

    val valve: Valve
    val rate: Int
    val projected: Int
    val minutesLeft: Int
}
