package com.barneyb.aoc.aoc2022.day16

internal interface Step<T : Step<T>> {
    fun moveTo(v: Valve, dist: Int): T
    fun moveAndOpen(v: Valve, dist: Int): T
    fun canOpen(): Boolean
    fun isOpen(v: Valve): Boolean
    fun open(): T

    val valve: Valve
    val rate: Int
    val projected: Int
    val minutesLeft: Int
}
