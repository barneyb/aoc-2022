package com.barneyb.aoc.aoc2022.day11

import com.barneyb.aoc.util.*
import com.barneyb.util.IntPair
import com.barneyb.util.Queue
import kotlin.text.toLong

fun main() {
    Solver.execute(
        ::parse,
        ::partOne,
        ::partTwo, // 15310845153
    )
}

data class Monkey(
    val items: Queue<Long>,
    val op: (Long) -> Long,
    val modulus: Long,
    val targets: IntPair,
) {

    var inspectionCount = 0L
        private set

    fun hasItems() =
        items.isNotEmpty()

    fun inspect(): Long {
        inspectionCount++
        return items.dequeue()
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

    val modulus = lines[3].split(' ').last().toLong()

    val targets = IntPair(
        lines[4].trim().split(' ').last().toInt(),
        lines[5].trim().split(' ').last().toInt(),
    )

    return Monkey(items, op, modulus, targets)
}

internal fun partOne(blocks: List<Slice>) =
    part(blocks.map(::parseMonkey), 20) { it / 3 }

internal fun partTwo(blocks: List<Slice>) =
    blocks.map(::parseMonkey).let { monkeys ->
        monkeys.map(Monkey::modulus)
            .fold(1, Long::times)
            .let { modulus ->
                part(monkeys, 10000) { it % modulus }
            }
    }

private fun part(
    monkeys: List<Monkey>,
    rounds: Int,
    transform: (Long) -> Long
): Long {
    repeat(rounds) { round ->
        monkeys.forEach { m ->
            while (m.hasItems()) {
                var level = m.inspect()
                level = m.op(level)
                level = transform(level)
                monkeys[if (level % m.modulus == 0L)
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
        .fold(1, Long::times)
}
