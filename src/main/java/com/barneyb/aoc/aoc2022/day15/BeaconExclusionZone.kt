package com.barneyb.aoc.aoc2022.day15

import com.barneyb.aoc.util.*
import com.barneyb.util.HashSet
import com.barneyb.util.Rect
import com.barneyb.util.Vec2
import kotlin.math.abs
import kotlin.math.max
import kotlin.text.toInt

fun main() {
    Solver.execute(
        ::parse,
        ::countNonBeaconPositionsOnRow, // 5878678
        ::distressBeaconTuningFrequency, // 11796491041245
    )
}

private const val ROW_TO_SCAN = 2_000_000

private const val LOW = 0
private const val HIGH = 4_000_000

internal data class Sensor(val id: Int, val pos: Vec2, val range: Int) {
    val x get() = pos.x
    val y get() = pos.y

    fun inRange(p: Vec2) =
        pos.getManhattanDistance(p) <= range

    fun separation(other: Sensor) =
        pos.getManhattanDistance(other.pos)

    fun inRangeXsOnRow(row: Int): IntRange {
        val extra = range - abs(y - row)
        return if (extra < 0) IntRange.EMPTY
        else x - extra..x + extra
    }
}

typealias Beacon = Vec2

internal data class Model(
    val sensors: List<Sensor>,
    val beacons: HashSet<Beacon>
) {
    private val bounds = sensors.fold(
        beacons.fold(
            Rect.EMPTY,
            Rect::coerceToInclude
        )
    ) { r, s ->
        r.coerceToInclude(s.pos)
    }

    fun draw(bounds: Rect = this.bounds) =
        buildString {
            val rowLabelWidth = max(
                bounds.y1.toString().length,
                bounds.y2.toString().length,
            )
            append('\n')
            val skip = abs(bounds.x1 - bounds.x1 / 10 * 10)
            append(" ".repeat(rowLabelWidth + 1 + skip))
            for (x in bounds.x1 + skip..bounds.x2 step 10) {
                val lbl = x.toString()
                if (x + 1 + lbl.length > bounds.x2) break
                append('|')
                append(lbl.padEnd(9))
            }
            for (y in bounds.yRange) {
                append('\n')
                append(y.toString().padStart(rowLabelWidth))
                append(' ')
                for (x in bounds.xRange) {
                    val p = Vec2(x, y)
                    if (beacons.contains(p)) append('B')
                    else {
                        val idx = sensors.indexOfFirst { it.pos == p }
                        if (idx >= 0) append('S')
                        else append('.')
                    }
                }
            }
        }
}

internal fun parse(input: String) =
    input.toSlice()
        .trim()
        .lines()
        .withIndex()
        .map { (i, it) -> parseLine(i, it) }
        .unzip()
        .let { (ss, bs) -> Model(ss, HashSet(bs)) }

// Sensor at x=2, y=18: closest beacon is at x=-2, y=15
private val RE = Regex(
    "Sensor at x=(-?\\d+), y=(-?\\d+): closest beacon is at x=(-?\\d+), y=(-?\\d+)"
)

internal fun parseLine(idx: Int, input: CharSequence) =
    RE.matchEntire(input)!!.let { m ->
        val (sx, sy, bx, by) = m.groupValues
            .drop(1) // entire match
            .map(String::toInt)
        val beacon = Beacon(bx, by)
        val pos = Vec2(sx, sy)
        val sensor = Sensor(idx, pos, pos.getManhattanDistance(beacon))
        Pair(sensor, beacon)
    }

internal fun countNonBeaconPositionsOnRow(
    model: Model,
    row: Int = ROW_TO_SCAN
): Int {
    val ranges = arrayListOf<IntRange>()
    fun add(r: IntRange) {
        val idx = ranges.indexOfFirst { it.overlaps(r) }
        if (idx < 0) ranges.add(r)
        else add(ranges.removeAt(idx) + r)
    }
    model.sensors.forEach {
        add(it.inRangeXsOnRow(row))
    }
    return ranges.sumOf(IntRange::size) -
            model.beacons.count { it.y == row }
}

internal val Vec2.tuningFrequency
    get() = x.toLong() * HIGH + y

/*
        <dependency>
            <groupId>org.choco-solver</groupId>
            <artifactId>choco-solver</artifactId>
            <version>4.10.10</version>
        </dependency>

internal fun distressBeaconTuningFrequency(
    model: Model,
    lo: Int = LOW,
    hi: Int = HIGH
): Long {
    val m = org.chocosolver.solver.Model("Beacon Exclusion Zone")
    val xVar = m.intVar("x", lo, hi)
    val yVar = m.intVar("y", lo, hi)
    // not a beacon
    for (b in model.beacons) {
        m.or(
            xVar.ne(b.x).decompose(),
            yVar.ne(b.y).decompose(),
        )
    }
    // not in range of a sensor
    for (s in model.sensors) {
        xVar.sub(s.x).abs()
            .add(yVar.sub(s.y).abs())
            .gt(s.range)
            .post()
    }
    println(m.solver.findSolution())
    return Vec2(xVar.value, yVar.value).tuningFrequency
}
*/

internal fun distressBeaconTuningFrequency(
    model: Model,
    lo: Int = LOW,
    hi: Int = HIGH
): Long {
    val lines = mutableListOf<Line>()
    for ((i, u) in model.sensors.withIndex()) {
        for (v in model.sensors.drop(i + 1)) {
            if (u.separation(v) == u.range + v.range + 2) {
                lines.add(
                    Line.between(
                        u.pos.north(u.range + 1),
                        u.pos.west(u.range + 1),
                    )
                )
                lines.add(
                    Line.between(
                        u.pos.north(u.range + 1),
                        u.pos.east(u.range + 1),
                    )
                )
                lines.add(
                    Line.between(
                        u.pos.south(u.range + 1),
                        u.pos.west(u.range + 1),
                    )
                )
                lines.add(
                    Line.between(
                        u.pos.south(u.range + 1),
                        u.pos.east(u.range + 1),
                    )
                )
            }
        }
    }
    val bySlope = lines
        .distinct()
        .groupBy(Line::slope)
    val points = HashSet<Vec2>()
    bySlope[1]!!.forEach { a ->
        bySlope[-1]!!.forEach { b ->
            points.add(a.intersection(b))
        }
    }
    return points
        .filter { it.x >= lo && it.y >= lo }
        .filter { it.x <= hi && it.y <= hi }
        .filter { !model.beacons.contains(it) }
        .first { p -> model.sensors.none { it.inRange(p) } }
        .tuningFrequency
}

internal data class Line(val slope: Int, val intercept: Int) {

    companion object {
        fun between(p1: Vec2, p2: Vec2) =
            ((p2.y - p1.y) / (p2.x - p1.x)).let { slope ->
                Line(slope, p1.y - slope * p1.x)
            }
    }

    fun intersection(other: Line) =
        ((other.intercept + intercept) / 2).let { y ->
            Vec2(y - intercept, y)
        }

}
