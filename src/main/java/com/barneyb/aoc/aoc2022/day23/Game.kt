package com.barneyb.aoc.aoc2022.day23

import com.barneyb.util.Dir.*
import com.barneyb.util.HashMap
import com.barneyb.util.HashSet
import com.barneyb.util.Rect
import com.barneyb.util.Vec2

private val searchDirs = arrayOf(NORTH, SOUTH, WEST, EAST)

class Game private constructor(
    val elves: HashSet<Vec2>,
    val rounds: Int,
    private val traversal: List<Vec2>
) {

    constructor(elves: HashSet<Vec2>) : this(elves, 0, buildList {
        for (d in searchDirs) {
            val proposed = Vec2.ORIGIN.move(d)
            add(proposed)
            val ortho = d.turnRight() // doesn't matter
            add(proposed.move(ortho, 1))
            add(proposed.move(ortho, -1))
        }
    })

    val bounds by lazy {
        elves.fold(Rect.EMPTY, Rect::coerceToInclude)
    }

    val emptySpaceCount by lazy {
        bounds.let { it.width * it.height - elves.size }
    }

//    init {
//        val msg = if (rounds == 0) "Initial State"
//        else "End of Round $rounds"
//        println("== $msg ==$this\n")
//    }

    fun tick(): Game =
        tickWithMotionCount().first

    fun tickWithMotionCount(): Pair<Game, Int> {
//        println("\nDuring round ${rounds + 1}:")
        val proposal = HashMap<Vec2, Vec2>() // proposed -> current
        val conflicts = HashSet<Vec2>() // doubled proposals
        var elvesInMotion = 0
        fun propose(curr: Vec2, prop: Vec2) {
            if (proposal.contains(prop)) {
                val old = proposal.remove(prop)
//                println("  $old's proposal rejected; stay put")
                proposal[old] = old
                elvesInMotion--
                conflicts.add(prop)
            }
            if (conflicts.contains(prop)) {
//                println("  rejected; stay at $curr")
                proposal[curr] = curr
            } else {
                proposal[prop] = curr
                elvesInMotion++
            }
        }
        perElf@ for (e in elves) {
//            println(e)
            val positions = traversal.map { e + it }
            val scan = positions.map(elves::contains)
            if (scan.all { !it }) { // so alone!
//                println("  is all alone")
                proposal[e] = e
                continue
            }
            for (i in 0..9 step 3) {
//                println(" check ${positions[i]}")
                if (scan.subList(i, i + 3).all { !it }) {
//                    println("  -> ${positions[i]}")
                    propose(e, positions[i])
                    continue@perElf
                }
            }
            proposal[e] = e
        }
        val newElves = HashSet<Vec2>()
        newElves.addAll(proposal.keys)
        assert(elves.size == newElves.size) {
            "Went from ${elves.size} elves in round $rounds, to ${newElves.size} in ${rounds + 1}?!"
        }
//        println(
//            "${if (rounds > 0) "," else ""}{   \"moves\": ${
//                proposal.filter { (a, b) -> a != b }
//                    .map { (b, a) -> "[${a.x},${a.y},${if (a.x == b.x) if (a.y > b.y) 0 else 2 else if (a.x > b.x) 3 else 1}]" }
//            },\n    \"conflicts\": ${
//                conflicts.map { "[${it.x},${it.y}]" }
//            },\n    \"stationary\": ${
//                proposal.filter { (a, b) -> a == b }
//                    .map { (it, _) -> "[${it.x},${it.y}]" }
//            }\n}"
//        )
        return Pair(
            Game(
                newElves,
                rounds + 1,
                traversal.drop(3) + traversal.take(3)
            ),
            elvesInMotion
        )
    }

    override fun toString() =
        toString(bounds)

    fun toString(bounds: Rect) =
        buildString {
            for (y in bounds.yRange) {
                append('\n')
                for (x in bounds.xRange) {
                    append(if (elves.contains(Vec2(x, y))) '#' else '.')
                }
            }
        }

}
