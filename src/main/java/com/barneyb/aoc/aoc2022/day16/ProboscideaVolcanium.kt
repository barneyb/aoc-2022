package com.barneyb.aoc.aoc2022.day16

import com.barneyb.aoc.util.Solver
import com.barneyb.aoc.util.toSlice
import com.barneyb.util.HashMap
import com.barneyb.util.Queue
import com.barneyb.util.Stack

fun main() {
    Solver.execute(
        ::parse,
        ::maximumPressureRelease, // 1880
        ::maximumPressureReleaseWithElephant, // 2520
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

internal fun parseValves(input: String) =
    input.toSlice()
        .trim()
        .lines()
        .map(lineMatcher::matchEntire)
        .map { m ->
            val (name, rate, tunnels) = m!!.groupValues.drop(1)
            Valve(name, rate.toInt(), tunnels.split(',').map(String::trim))
        }

internal fun parse(input: String) =
    buildGraph(parseValves(input))

private const val START_VALVE = "AA"

private typealias Graph = HashMap<Valve, HashMap<Valve, Int>>

private val Graph.startValve
    get() = keys.first { it.name == START_VALVE }

private fun buildGraph(valves: List<Valve>): Graph {
    val index = HashMap<String, Valve>().apply {
        for (v in valves)
            put(v.name, v)
    }
    val adjacent = Graph().apply {
        for (v in valves) {
            put(v, HashMap<Valve, Int>().apply {
                for (t in v.tunnels)
                    put(index[t], 1)
            })
        }
    }
//    printGraphviz(adjacent)
    for (v in valves) {
        if (v.rate > 0 || v.name == START_VALVE) continue
        val adj = adjacent[v]
        for ((a, ad) in adj) {
            for ((b, bd) in adj) {
                if (a == b) continue
                val curr = if (adjacent[a].contains(b)) adjacent[a][b]
                else Int.MAX_VALUE
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
//    printGraphviz(adjacent)
    val again = Graph()
    for (v in adjacent.keys) {
        val temp = HashMap<Valve, Int>()
        val queue = Queue(Pair(v, 0))
        while (queue.isNotEmpty()) {
            val (s, d) = queue.remove()
            if (!temp.contains(s) || temp[s] > d) {
                temp[s] = d
                s.tunnels.forEach {
                    queue.enqueue(Pair(index[it], d + 1))
                }
            }
        }
        again[v] = HashMap<Valve, Int>().apply {
            temp.filter { (s, _) ->
                s !== v && s.rate > 0
            }.forEach(this::put)
        }
    }
//    printGraphviz(again)
    return again
}

@Suppress("unused")
private fun printGraphviz(adjacent: Graph) {
    println(buildString {
        append("digraph {\n")
        for ((v, adj) in adjacent) {
            append("  ${v.name} [label=\"${v.name} (${v.rate})\"${if (v.name == START_VALVE) ",style=filled,fillcolor=lightgreen" else ""}];")
            for ((o, d) in adj) {
                append("${v.name} -> ${o.name} [label=$d];")
            }
            append('\n')
        }
        append("}\n")
    })
}

private fun walk(adjacent: Graph, start: Step): Int {
//    printGraphviz(adjacent)
    val queue = Stack(start)
    val maxRate = adjacent.keys.sumOf(Valve::rate)
    var best = Int.MIN_VALUE
    val visited = HashMap<Set<Valve>, Int>()
    while (queue.isNotEmpty()) {
        val step = queue.remove()
        val remaining = step.minutesLeft
        if (remaining < 0) continue
        if (step.projected > best)
            best = step.projected
        else if (visited.contains(step.open))
            if (visited[step.open] >= step.projected)
                continue
        visited[step.open] = step.projected
        if (step.rate == maxRate)
            continue
        else if (step.projected + remaining * (maxRate - step.rate) <= best)
            continue
        for ((v, d) in adjacent[step.valve])
            if (remaining - 1 > d && !step.isOpen(v))
                queue.add(step.moveAndOpen(v, d))
    }
    return best
}

internal fun maximumPressureRelease(graph: Graph) =
    walk(graph, Solo(29, graph.startValve))

internal fun maximumPressureReleaseWithElephant(graph: Graph) =
    walk(graph, Team(25, graph.startValve))
