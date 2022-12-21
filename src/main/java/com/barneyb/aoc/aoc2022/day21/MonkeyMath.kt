package com.barneyb.aoc.aoc2022.day21

import com.barneyb.aoc.util.Solver
import com.barneyb.aoc.util.toLong
import com.barneyb.aoc.util.toSlice

fun main() {
    Solver.execute(
        ::parse,
        ::rootYellsWhat, // 82225382988628
    )
}

sealed interface Monkey

data class Yell(
    val n: Long,
) : Monkey

data class Operation(
    val a: CharSequence,
    val b: CharSequence,
    val op: Char,
) : Monkey

// dbpl: 5
// pppw: cczh / lfqf
internal fun parse(input: String) =
    input.toSlice()
        .trim()
        .lines()
        .associateByTo(mutableMapOf(), { l -> l.take(4) }) { l ->
            if (l.length < 17)
                Yell(l.drop(6).toLong())
            else
                Operation(
                    l.drop(6).take(4),
                    l.drop(13).take(4),
                    l[11],
                )
        }

internal fun rootYellsWhat(byName: MutableMap<CharSequence, Monkey>): Long {
    fun compute(name: CharSequence): Long =
        when (val it = byName[name]!!) {
            is Yell -> it.n
            is Operation -> {
                val a = compute(it.a)
                val b = compute(it.b)
                val n = when (it.op) {
                    '+' -> a + b
                    '-' -> a - b
                    '*' -> a * b
                    '/' -> a / b
                    else -> throw IllegalStateException("Unknown '$it.op' operator")
                }
                byName[name] = Yell(n)
                n
            }
        }
    return compute("root".toSlice())
}
