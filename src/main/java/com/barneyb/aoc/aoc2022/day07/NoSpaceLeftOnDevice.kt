package com.barneyb.aoc.aoc2022.day07

import com.barneyb.aoc.util.Slice
import com.barneyb.aoc.util.Solver
import com.barneyb.aoc.util.toInt
import com.barneyb.aoc.util.toSlice
import com.barneyb.util.Queue

fun main() {
    Solver.benchmark(
        ::parse,
        ::partOne,
        ::partTwo,
    )
}

internal open class File(val name: CharSequence, val size: Int) {
    override fun toString() =
        "$name (file, size=$size)"
}

internal class Dir(name: CharSequence, val parent: Dir?) : File(name, 0) {
    private val byName = LinkedHashMap<CharSequence, File>()

    val totalSize: Int
        get() = children.sumOf {
            when (it) {
                is Dir -> it.totalSize
                else -> it.size
            }
        }

    val children
        get() = byName.values

    val subdirs
        get() = children.filterIsInstance<Dir>()

    fun subdir(name: CharSequence) =
        byName.getOrPut(name) { Dir(name, this) } as Dir

    fun file(name: CharSequence, size: Int) {
        byName[name] = File(name, size)
    }

    override fun toString() =
        "$name (dir)"

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

internal fun partTwo(root: Dir) =
    (70_000_000 - root.totalSize).let { available ->
        (30_000_000 - available).let { needed ->
            allDirs(root)
                .map(Dir::totalSize)
                .filter { it >= needed }
                .min()
        }
    }

internal fun draw(root: Dir) =
    buildString {
        fun walk(f: File, l: Int) {
            if (isNotEmpty()) append('\n')
            repeat(l) { append("  ") }
            append("- ")
            append(f)
            if (f is Dir)
                f.children.forEach {
                    walk(it, l + 1)
                }
        }
        walk(root, 0)
    }
