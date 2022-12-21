package com.barneyb.aoc.aoc2022.day21

import com.barneyb.aoc.util.Solver
import com.barneyb.aoc.util.toLong
import com.barneyb.aoc.util.toSlice
import com.barneyb.util.HashSet

fun main() {
    Solver.execute(
        ::parse,
        ::rootYellsWhat, // 82,225,382,988,628
        ::whatToYell, // 3,429,411,069,028
    )
}

internal sealed interface Monkey

private typealias Op = Char

internal data class Yell(
    val n: Long,
) : Monkey

internal data class Operation(
    val a: CharSequence,
    val b: CharSequence,
    val op: Op,
) : Monkey

// dbpl: 5
// pppw: cczh / lfqf
internal fun parse(input: String) =
    input.toSlice()
        .trim()
        .lines()
        .associateBy({ l -> l.take(4) }) { l ->
            if (l.length < 17)
                Yell(l.drop(6).toLong())
            else
                Operation(
                    l.drop(6).take(4),
                    l.drop(13).take(4),
                    l[11],
                )
        }

private val ROOT = "root".toSlice()
private val HUMAN = "humn".toSlice()

private fun doOp(a: Long, op: Op, b: Long) =
    when (op) {
        '+' -> a + b
        '-' -> a - b
        '*' -> a * b
        '/' -> a / b
        else -> throw IllegalStateException("Unknown '$op' operator")
    }

private fun whoYellsWhat(
    who: CharSequence,
    byName: Map<CharSequence, Monkey>
): Long {
    val map = HashMap(byName)
    fun compute(name: CharSequence): Long =
        when (val it = map[name]!!) {
            is Yell -> it.n
            is Operation -> {
                val a = compute(it.a)
                val b = compute(it.b)
                val n = doOp(a, it.op, b)
                map[name] = Yell(n)
                n
            }
        }
    return compute(who)
}

internal fun rootYellsWhat(byName: Map<CharSequence, Monkey>) =
    whoYellsWhat(ROOT, byName)

// r = x + b, what is x?
private fun invOp(r: Long, op: Op, b: Long) =
    when (op) {
        '+' -> r - b
        '-' -> r + b
        '*' -> r / b
        '/' -> r * b
        else -> throw IllegalStateException("Unknown '$op' operator")
    }

// r = a + x, what is x?
private fun invOp(r: Long, a: Long, op: Op) =
    when (op) {
        '+' -> r - a
        '-' -> a - r
        '*' -> r / a
        '/' -> a / r
        else -> throw IllegalStateException("Unknown '$op' operator")
    }

internal fun whatToYell(byName: Map<CharSequence, Monkey>): Long {
    val humanBased = HashSet<CharSequence>(HUMAN)

    fun hasHuman(name: CharSequence): Boolean {
        return when (val it = byName[name]!!) {
            is Yell -> name == HUMAN
            is Operation ->
                if (hasHuman(it.a) || hasHuman(it.b)) {
                    humanBased.add(name)
                    true
                } else false
        }
    }

    fun toYell(name: CharSequence, answer: Long): Long {
        return when (val it = byName[name]!!) {
            is Yell ->
                if (name == HUMAN) answer else it.n
            is Operation -> {
                if (humanBased.contains(it.a)) {
                    val other = whoYellsWhat(it.b, byName)
                    val next = invOp(answer, it.op, other)
//                    println("$answer = x ${it.op} $other :: x=$next")
                    toYell(it.a, next)
                } else { // b has/is the human
                    val other = whoYellsWhat(it.a, byName)
                    val next = invOp(answer, other, it.op)
//                    println("$answer = $other ${it.op} x :: x=$next")
                    toYell(it.b, next)
                }
            }
        }
    }

    val root = byName[ROOT] as Operation
    val (base, other) = if (hasHuman(root.a))
        Pair(root.a, root.b)
    else
        Pair(root.b, root.a)
    val goal = whoYellsWhat(other, byName)
//    println("$base must yell $goal (like ${other})")
    return toYell(base, goal)
}
