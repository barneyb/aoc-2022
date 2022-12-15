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
//        ::distressBeaconTuningFrequency,
    )
}

private const val ROW_TO_SCAN = 2_000_000

private const val LOW = 0
private const val HIGH = 4_000_000

data class Sensor(val pos: Vec2, val range: Int) {
    val x get() = pos.x
    val y get() = pos.y

    fun inRange(p: Vec2) =
        pos.getManhattanDistance(p) <= range
}

typealias Beacon = Vec2

data class Model(val sensors: List<Sensor>, val beacons: HashSet<Beacon>) {
    private val bounds = sensors.fold(
        beacons.fold(
            Rect.EMPTY,
            Rect::coerceToInclude
        )
    ) { r, s ->
        r.coerceToInclude(s.pos)
    }

    override fun toString() =
        toString(bounds)

    fun toString(bounds: Rect) =
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
        .map(::parseLine)
        .unzip()
        .let { (ss, bs) -> Model(ss, HashSet(bs)) }

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
        add(inRangeXsOnRow(it, row))
    }
    return ranges.sumOf(IntRange::size) -
            model.beacons.count { it.y == row }
}

internal fun inRangeXsOnRow(s: Sensor, row: Int): IntRange {
    val extra = s.range - abs(s.y - row)
    return if (extra < 0) IntRange.EMPTY
    else s.x - extra..s.x + extra
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
    return Vec2(14, 11).tuningFrequency
}
