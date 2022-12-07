package com.barneyb.aoc.aoc2022.day07

import com.barneyb.aoc.util.Slice
import com.barneyb.aoc.util.Solver
import com.barneyb.aoc.util.toInt
import com.barneyb.aoc.util.toSlice
import com.barneyb.util.Queue

fun main() {
    Solver.execute(
        ::parse,
        ::partOne,
    )
}

internal data class Dir(val name: CharSequence, val parent: Dir?) {
    private val dirsByName = HashMap<CharSequence, Dir>()
    private val filesByName = HashMap<CharSequence, Int>()

    var localSize = -1
        private set

    var totalSize = -1
        private set

    val subdirs
        get() = dirsByName.values

    fun subdir(name: CharSequence) =
        dirsByName.getOrPut(name) { Dir(name, this) }

    fun file(name: CharSequence, size: Int) {
        filesByName[name] = size
    }

    fun computeSizes() {
        localSize = filesByName.values.sum()
        dirsByName.values.forEach(Dir::computeSizes)
        totalSize = localSize + subdirs.sumOf(Dir::totalSize)
    }
}

internal fun parse(input: String) =
    input.toSlice().trim().lines().let { lines ->
        assert(
            lines.first().toString() == "\$ cd /"
        ) { "Must start by changing to the '/' directory" }
        val root = Dir(Slice("/"), null)
        var curr = root
        for (l in lines) {
            if (l.startsWith("$ cd")) {
                curr = if (l.endsWith("/")) {
                    root
                } else if (l.endsWith("..")) {
                    curr.parent!!
                } else {
                    curr.subdir(l.drop(5))
                }
            } else if (l.startsWith("$ ls")) {
                continue
            } else if (l.startsWith("dir")) {
                curr.subdir(l.drop(4))
            } else {
                val idx = l.indexOf(' ')
                curr.file(l.drop(idx + 1), l[0, idx].toInt())
            }
        }
        root.computeSizes()
        root
    }

private fun allDirs(root: Dir) =
    buildList {
        val queue = Queue<Dir>()
        queue.enqueue(root)
        while (queue.isNotEmpty()) {
            val d = queue.dequeue()
            add(d)
            d.subdirs.forEach(queue::enqueue)
        }
    }

internal fun partOne(root: Dir) =
    allDirs(root)
        .map(Dir::totalSize)
        .filter { it <= 100_000 }
        .sum()
