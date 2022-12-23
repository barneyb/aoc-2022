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

    private val bounds =
        elves.fold(Rect.of(elves.first()), Rect::coerceToInclude)

    val emptySpaceCount =
        bounds.width * bounds.height - elves.size

//    init {
//        val msg = if (rounds == 0) "Initial State"
//        else "End of Round $rounds"
//        println("== $msg ==$this\n")
//    }

    fun tick(): Game {
//        println("\nDuring round ${rounds + 1}:")
        val proposal = HashMap<Vec2, Vec2>() // proposed -> current
        val conflicts = HashSet<Vec2>() // doubled proposals
        fun propose(curr: Vec2, prop: Vec2) {
            if (proposal.contains(prop)) {
                val old = proposal.remove(prop)
//                println("  $old's proposal rejected; stay put")
                proposal[old] = old
                conflicts.add(prop)
            }
            if (conflicts.contains(prop)) {
//                println("  rejected; stay at $curr")
                proposal[curr] = curr
            } else {
                proposal[prop] = curr
            }
        }
        for (e in elves) {
//            println(e)
            val positions = traversal.map { e + it }
            val scan = positions.map(elves::contains)
            if (scan.all { !it }) { // so alone!
//                println("  is all alone")
                proposal[e] = e
                continue
            }
            var found = false
            for (i in 0..9 step 3) {
//                println(" check ${positions[i]}")
                if (scan.subList(i, i + 3).all { !it }) {
//                    println("  -> ${positions[i]}")
                    propose(e, positions[i])
                    found = true
                    break
                }
            }
            if (!found)
                proposal[e] = e
        }
        val next = HashSet<Vec2>()
        next.addAll(proposal.keys)
        assert(elves.size == next.size) {
            "Went from ${elves.size} elves in round $rounds, to ${next.size} in ${rounds + 1}?!"
        }
        return Game(
            next,
            rounds + 1,
            traversal.drop(3) + traversal.take(3)
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
