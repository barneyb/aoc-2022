package com.barneyb.aoc.aoc2022.day22

import com.barneyb.util.Dir

internal enum class Turn {
    LEFT,
    RIGHT,
    NONE; // sort of a kludge, but neatly sidesteps the fencepost problem

    fun execute(d: Dir): Dir =
        when (this) {
            LEFT -> d.turnLeft()
            RIGHT -> d.turnRight()
            NONE -> d
        }
}
