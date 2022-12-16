package com.barneyb.aoc.aoc2022.day16

import com.barneyb.aoc.util.Solver
import com.barneyb.aoc.util.toSlice
import com.barneyb.util.HashMap
import com.barneyb.util.Stack

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
private val lineMatcher =
    Regex("Valve ([A-Z]+) has flow rate=(\\d+); tunnels? leads? to valves? ([A-Z]+(, [A-Z]+)*)")

internal fun parse(input: String) =
    input.toSlice()
        .trim()
        .lines()
        .map(lineMatcher::matchEntire)
        .map { m ->
            val (name, rate, tunnels) = m!!.groupValues.drop(1)
            Valve(name, rate.toInt(), tunnels.split(',').map(String::trim))
        }

private data class Step(
    val minutesLeft: Int,
    val valve: Valve,
    val projected: Int = 0,
    val open: Set<Valve> = emptySet(),
    val rate: Int = 0,
) {
    fun canOpen() =
        valve.rate > 0 && !open.contains(valve)

    fun open() =
        copy(
            minutesLeft = minutesLeft - 1,
            projected = projected + valve.rate * minutesLeft,
            open = open + valve,
            rate = rate + valve.rate
        )

    fun moveTo(v: Valve, dist: Int) =
        copy(
            valve = v,
            minutesLeft = minutesLeft - dist,
        )

}

private const val START_VALVE = "AA"

private fun buildGraph(valves: List<Valve>): HashMap<Valve, MutableMap<Valve, Int>> {
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
        if (v.rate > 0 || v.name == START_VALVE) continue
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
//    println(buildString {
//        append("graph {\n")
//        for ((v, adj) in adjacent) {
//            append("  ${v.name} [label=\"${v.name} (${v.rate})\"]\n")
//            for ((o, d) in adj) {
//                if (v.name < o.name)
//                    append("  ${v.name} -- ${o.name} [label=$d]\n")
//            }
//        }
//        append("}\n")
//    })
    return adjacent
}

internal fun maximumPressureRelease(valves: List<Valve>): Int {
    val adjacent = buildGraph(valves)
    val queue = Stack(Step(29, valves.first { it.name == START_VALVE }))
    val maxRate = valves.sumOf(Valve::rate)
    var best = Int.MIN_VALUE
    while (queue.isNotEmpty()) {
        val step = queue.remove()
        val remaining = step.minutesLeft
        if (remaining < 0) continue
        if (step.projected > best) best = step.projected
        if (remaining == 0) continue
        else if (step.projected + remaining * (maxRate - step.rate) < best) {
            continue
        }
        if (remaining > 1 && step.canOpen())
            queue.add(step.open())
        for ((v, d) in adjacent[step.valve])
            if (remaining - d >= 1)
                queue.add(step.moveTo(v, d))
    }
    return best
}
