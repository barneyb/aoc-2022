package com.barneyb.aoc.aoc2022.day15

import com.barneyb.aoc.util.Solver
import com.barneyb.aoc.util.toSlice
import com.barneyb.util.HashSet
import com.barneyb.util.Vec2
import kotlin.math.abs

fun main() {
    Solver.execute(
        ::parse,
        ::partOne, // 5878678
    )
}

private const val ROW_TO_SCAN = 2_000_000

data class Sensor(val pos: Vec2, val range: Int) {
    val x get() = pos.x
    val y get() = pos.y
}

typealias Beacon = Vec2

data class Model(val sensors: List<Sensor>, val beacons: List<Beacon>)

internal fun parse(input: String) =
    input.toSlice()
        .trim()
        .lines()
        .map(::parseLine)
        .unzip()
        .let { (ss, bs) -> Model(ss, bs) }

// Sensor at x=2, y=18: closest beacon is at x=-2, y=15
private val RE = Regex(
    "Sensor at x=(-?\\d+), y=(-?\\d+): closest beacon is at x=(-?\\d+), y=(-?\\d+)"
)

internal fun parseLine(input: CharSequence) =
    RE.matchEntire(input)!!.let { m ->
        val (sx, sy, bx, by) = m.groupValues
            .drop(1) // entire match
            .map(String::toInt)
        val beacon = Beacon(bx, by)
        val pos = Vec2(sx, sy)
        val sensor = Sensor(pos, pos.getManhattanDistance(beacon))
        Pair(sensor, beacon)
    }

internal fun partOne(model: Model, row: Int = ROW_TO_SCAN): Int {
    val xs = HashSet<Int>()
    for (s in model.sensors) {
        xs.addAll(xOnRow(s, row))
    }
    for (b in model.beacons)
        if (b.y == row)
            xs.remove(b.x)
    return xs.size
}

internal fun xOnRow(s: Sensor, row: Int): IntRange {
    val extra = s.range - abs(s.y - row)
    return if (extra < 0) IntRange.EMPTY
    else s.x - extra..s.x + extra
}
