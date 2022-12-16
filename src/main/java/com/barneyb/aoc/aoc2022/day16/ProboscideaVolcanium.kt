package com.barneyb.aoc.aoc2022.day16

import com.barneyb.aoc.util.Solver
import com.barneyb.aoc.util.toSlice
import com.barneyb.util.HashMap
import java.util.*

fun main() {
    Solver.execute(
        ::parse,
        ::maximumPressureRelease, // 1880
    )
}

internal data class Valve(
    val name: String,
    val rate: Int,
    val tunnels: List<String>,
)

// Valve AA has flow rate=0; tunnels lead to valves DD, II, BB
private val RE =
    Regex("Valve ([A-Z]+) has flow rate=(\\d+); tunnels? leads? to valves? ([A-Z]+(, [A-Z]+)*)")

internal fun parse(input: String) =
    input.toSlice()
        .trim()
        .lines()
        .map(RE::matchEntire)
        .map { m ->
            val (name, rate, tunnels) = m!!.groupValues.drop(1)
            Valve(name, rate.toInt(), tunnels.split(',').map(String::trim))
        }

private data class Step(
    val minute: Int,
    val valve: Valve,
    val projected: Int = 0,
    val open: Set<Valve> = emptySet(),
    val rate: Int = 0,
) {
    fun canOpen() =
        valve.rate > 0 && !open.contains(valve)

    fun open() =
        copy(
            minute = minute + 1,
            projected = projected + valve.rate * (30 - minute),
            open = open + valve,
            rate = rate + valve.rate
        )

    fun moveTo(v: Valve, dist: Int) =
        copy(
            valve = v,
            minute = minute + dist,
        )

}

private const val START_ROOM = "AA"

internal fun maximumPressureRelease(valves: List<Valve>): Int {
    val index = HashMap<String, Valve>()
    for (v in valves) {
        index[v.name] = v
    }
    val adjacent = HashMap<Valve, MutableMap<Valve, Int>>()
    for (v in valves) {
        adjacent[v] = v.tunnels
            .associateByTo(mutableMapOf(), index::get) { 1 }
    }
    for (v in valves) {
        if (v.rate > 0 || v.name == START_ROOM) continue
        val adj = adjacent[v]
        for ((a, ad) in adj) {
            for ((b, bd) in adj) {
                if (a == b) continue
                val curr = adjacent[a].getOrDefault(b, Int.MAX_VALUE)
                val new = ad + bd
                if (new < curr) {
                    adjacent[a][b] = new
                    adjacent[b][a] = new
                }
            }
            adjacent[a].remove(v)
        }
        adjacent.remove(v)
    }
    println(buildString {
        append("graph {\n")
        for ((v, adj) in adjacent) {
            append("  ${v.name} [label=\"${v.name} (${v.rate})\"]\n")
            for ((o, d) in adj) {
                if (v.name < o.name)
                    append("  ${v.name} -- ${o.name} [label=$d]\n")
            }
        }
        append("}\n")
    })
    val queue = PriorityQueue/*Queue*//*Stack*/(
        Comparator.comparingInt(Step::projected).reversed()
    )
    queue.add(Step(1, index[START_ROOM]))
    val maxRate = valves.sumOf(Valve::rate)
    var best = Int.MIN_VALUE
    while (queue.isNotEmpty()) {
        val step = queue.remove()
        if (step.minute > 30) continue
        if (step.projected > best) best = step.projected
        if (step.minute == 30) continue
        else if (step.projected + (30 - step.minute) * (maxRate - step.rate) < best) {
            continue
        }
        if (step.canOpen())
            queue.add(step.open())
        for ((v, d) in adjacent[step.valve])
            queue.add(step.moveTo(v, d))
    }
    return best
}
