package com.barneyb.aoc.aoc2022.day22

import com.barneyb.util.Dir
import com.barneyb.util.Vec2

internal data class State(
    val pos: Vec2,
    val facing: Dir,
) {
    val password
        get() = pos.y * 1000 +
                pos.x * 4 +
                when (facing) {
                    Dir.EAST -> 0
                    Dir.SOUTH -> 1
                    Dir.WEST -> 2
                    Dir.NORTH -> 3
                }

    fun move() =
        State(pos.move(facing), facing)
}
