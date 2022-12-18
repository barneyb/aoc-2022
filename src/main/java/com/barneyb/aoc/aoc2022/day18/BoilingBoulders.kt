package com.barneyb.aoc.aoc2022.day18

import com.barneyb.aoc.util.*
import com.barneyb.util.HashSet
import com.barneyb.util.Queue
import com.barneyb.util.Vec3

fun main() {
    Solver.execute(
        ::parse,
        ::surfaceArea, // 3586
        ::externalSurfaceArea, // 2072
    )
}

internal fun parse(input: String) =
    HashSet(input.toSlice()
        .trim()
        .lines()
        .map {
            val (x, y, z) = it.split(",")
                .map(Slice::toInt)
            Vec3(x, y, z)
        })

internal fun surfaceArea(cubes: HashSet<Vec3>): Int {
    val v = cubes.size
    var e = 0
    for (c in cubes) {
        if (cubes.contains(c.mx(1))) e++
        if (cubes.contains(c.my(1))) e++
        if (cubes.contains(c.mz(1))) e++
    }
    return 6 * v - 2 * e
}

internal fun externalSurfaceArea(cubes: HashSet<Vec3>): Int {
    val visited = HashSet<Vec3>()
    var faces = 0
    val queue = Queue<Vec3>()
    // this assumes the droplet is "against" the origin and cubic-ish.
    val range = cubes.fold(1..1) { r, v ->
        r + v.x + v.y + v.z
    }.let { r ->
        r.first - 1..r.last + 1
    }

    fun check(v: Vec3) {
        if (v.x !in range) return
        if (v.y !in range) return
        if (v.z !in range) return
        if (visited.contains(v)) return
        if (cubes.contains(v)) {
            faces++
        } else {
            visited.add(v)
            queue.enqueue(v)
        }
    }

    check(Vec3(range.first, range.first, range.first))
    while (!queue.isEmpty()) {
        val curr = queue.dequeue()
        check(curr.mx(1))
        check(curr.mx(-1))
        check(curr.my(1))
        check(curr.my(-1))
        check(curr.mz(1))
        check(curr.mz(-1))
    }
    return faces
}
