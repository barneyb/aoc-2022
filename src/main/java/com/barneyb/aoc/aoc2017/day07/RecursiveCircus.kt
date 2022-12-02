package com.barneyb.aoc.aoc2017.day07

import com.barneyb.aoc.util.Solver
import java.util.*

fun main() {
    Solver.execute(
        ::parse,
        Prog::name,
    )
}

internal data class Prog(
    val name: String,
    val weight: Int,
    val tower: Collection<Prog> = emptyList(),
)

internal data class Record(
    val name: String,
    val weight: Int,
    val tower: Collection<String>,
) {
    companion object {
        // kpjxln (44) -> dzzbvkv, gzdxgvj, wsocb, jidxg
        val RE_PARSER = Regex("([a-z]+) \\((\\d+)\\)(:? -> ([a-z, ]+))?")

        fun parse(str: String): Record {
            val (name, weight, _, items) = RE_PARSER.find(str)!!.destructured
            return Record(
                name,
                weight.toInt(),
                if (items.isBlank()) emptyList() else items
                    .split(",")
                    .map(String::trim)
            )
        }
    }

    fun isLeaf() =
        tower.isEmpty()

}

internal fun parse(input: String): Prog {
    val q = LinkedList<Record>()
    val trees = mutableMapOf<String, Prog>()
    fun addNonLeaf(r: Record) {
        if (r.tower.all(trees::containsKey)) {
            trees[r.name] = Prog(
                r.name,
                r.weight,
                r.tower.mapNotNull(trees::remove)
            )
        } else {
            q.add(r)
        }
    }
    for (r in input.trim().lines().map(Record::parse)) {
        if (r.isLeaf()) {
            trees[r.name] = Prog(r.name, r.weight)
        } else {
            addNonLeaf(r)
        }
    }
    while (q.isNotEmpty()) {
        addNonLeaf(q.remove())
    }
    return trees.values.first()
}
