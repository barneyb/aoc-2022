package com.barneyb.aoc.aoc2022.day18

import com.barneyb.aoc.util.Slice
import com.barneyb.aoc.util.Solver
import com.barneyb.aoc.util.toInt
import com.barneyb.aoc.util.toSlice
import com.barneyb.util.Vec3

fun main() {
    Solver.execute(
        ::parse,
        ::surfaceArea, // 3586
    )
}

internal fun parse(input: String) =
    input.toSlice()
        .trim()
        .lines()
        .map {
            val (x, y, z) = it.split(",")
                .map(Slice::toInt)
            Vec3(x, y, z)
        }
        .sorted()

internal fun surfaceArea(cubes: List<Vec3>): Int {
    val v = cubes.size
    var e = 0
    for (c in cubes) {
        if (cubes.binarySearch(Vec3(c.x + 1, c.y, c.z)) >= 0) e++
        if (cubes.binarySearch(Vec3(c.x, c.y + 1, c.z)) >= 0) e++
        if (cubes.binarySearch(Vec3(c.x, c.y, c.z + 1)) >= 0) e++
    }
    return 6 * v - 2 * e
}
