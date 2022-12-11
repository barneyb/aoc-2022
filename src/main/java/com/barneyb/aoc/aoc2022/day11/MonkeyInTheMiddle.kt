package com.barneyb.aoc.aoc2022.day11

import com.barneyb.aoc.util.*
import com.barneyb.util.IntPair
import com.barneyb.util.Queue
import kotlin.text.toLong

fun main() {
    Solver.execute(
        ::parse,
        ::partOne,
    )
}

data class Monkey(
    val items: Queue<Long>,
    val op: (Long) -> Long,
    val test: (Long) -> Boolean,
    val targets: IntPair,
) {

    var inspectionCount = 0
        private set

    fun hasItems() =
        items.isNotEmpty()

    fun inspect(): Long {
        inspectionCount++
        return op(items.dequeue())
    }

    fun addItem(item: Long) {
        items.enqueue(item)
    }
}

/*
Monkey 0:
  Starting items: 79, 98
  Operation: new = old * 19
  Test: divisible by 23
    If true: throw to monkey 2
    If false: throw to monkey 3
 */
internal fun parse(input: String) =
    input.toSlice()
        .trim()
        .split("\n\n")
        .map(::parseMonkey)

internal fun parseMonkey(str: Slice): Monkey {
    val lines = str.trim()
        .lines()

    val items = Queue<Long>().apply {
        val l = lines[1]
        l.drop(l.indexOf(':') + 1)
            .split(',')
            .map { it.trim().toLong() }
            .forEach(this::enqueue)
    }

    val op = lines[2].trim().split(' ').drop(4).let { parts ->
        parts[1].let { str ->
            if (parts[0][0] == '+') {
                fun(n: Long) =
                    n + if (str[0] == 'o') n else str.toLong()
            } else if (parts[0][0] == '*') {
                fun(n: Long) =
                    n * if (str[0] == 'o') n else str.toLong()
            } else {
                throw IllegalArgumentException("Unknown '${parts[0]}' operation")
            }
        }
    }

    val test = lines[3].split(' ').last().toLong().let { divisor ->
        fun(n: Long) =
            n % divisor == 0L
    }

    val targets = IntPair(
        lines[4].trim().split(' ').last().toInt(),
        lines[5].trim().split(' ').last().toInt(),
    )

    return Monkey(items, op, test, targets)
}

internal fun partOne(monkeys: List<Monkey>): Int {
    repeat(20) { round ->
        monkeys.forEach { m ->
            while (m.hasItems()) {
                val level = m.inspect() / 3
                monkeys[if (m.test(level))
                    m.targets.first
                else
                    m.targets.second].addItem(level)
            }
        }
    }
    return monkeys
        .map(Monkey::inspectionCount)
        .sortedDescending()
        .take(2)
        .fold(1, Int::times)
}
