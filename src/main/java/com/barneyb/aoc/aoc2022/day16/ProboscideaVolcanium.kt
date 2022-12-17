package com.barneyb.aoc.aoc2022.day16

import com.barneyb.aoc.util.Solver
import com.barneyb.aoc.util.toSlice
import com.barneyb.util.HashMap

fun main() {
    Solver.execute(
        ::parse,
        ::maximumPressureRelease, // 1880
    ) { maximumPressureReleaseWithElephant(it, 1880) } // 2520
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

private const val START_VALVE = "AA"

internal typealias Graph = HashMap<Valve, MutableMap<Valve, Int>>

private fun buildGraph(valves: List<Valve>): Graph {
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

private fun walk(
    adjacent: Graph,
    start: Step<*>,
    minimum: Int = Int.MIN_VALUE
): Int {
    val queue =
        com.barneyb.util.Stack<Step<*>>()
//        java.util.PriorityQueue(
//            Comparator.comparingInt(Step<*>::rate).reversed()
//        )
//        java.util.PriorityQueue(
//            Comparator.comparingInt(Step<*>::projected).reversed()
//        )
    queue.add(start)
    val maxRate = adjacent.keys.sumOf(Valve::rate)
    var best = minimum
    var itr = 0L
    while (queue.isNotEmpty()) {
        val step = queue.remove()
        val remaining = step.minutesLeft
//        if (itr > 1000_000_000) break
        if (remaining < 0) continue
        if (step.projected > best) {
            best = step.projected
            println("  $best ($itr)")
        }
        if (remaining == 0 || step.rate == maxRate) continue
        else if (step.projected + remaining * (maxRate - step.rate) < best) {
            continue
        }
        if (++itr % 10_000_000 == 0L) println("${itr / 1000_000}M:${queue.size}) ${step.projected}:${step.projected + remaining * (maxRate - step.rate)} $step")
        for ((v, d) in adjacent[step.valve])
            if (remaining - d >= 1)
                queue.add(step.moveTo(v, d))
        if (remaining > 1 && step.canOpen()) {
            queue.add(step.open())
        }
    }
    return best
}

internal fun maximumPressureRelease(valves: List<Valve>) =
    walk(
        buildGraph(valves),
        Solo(29, valves.first { it.name == START_VALVE })
    )

internal fun maximumPressureReleaseWithElephant(
    valves: List<Valve>,
    minimum: Int = Int.MIN_VALUE
): Int {
    val min = 25
    val start = valves.first { it.name == START_VALVE }
    val graph = buildGraph(valves)
    return walk(
        graph,
        Team(intArrayOf(min, min), arrayOf(start, start)),
        minimum,
    )
}
