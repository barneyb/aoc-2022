package com.barneyb.aoc.aoc2022.day23

import com.barneyb.util.HashSet
import com.barneyb.util.Vec2
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

private const val EXAMPLE_ONE = """
....#..
..###.#
#...#.#
.#...##
#.###..
##.#.##
.#..#..
"""

private const val SMALLER_EXAMPLE = """
.....
..##.
..#..
.....
..##.
.....
"""

class UnstableDiffusionKtTest {

    @Test
    fun parsing() {
        assertEquals(
            HashSet(
                Vec2(2, 1),
                Vec2(3, 1),
                Vec2(2, 2),
                Vec2(2, 4),
                Vec2(3, 4),
            ),
            parse(SMALLER_EXAMPLE)
        )
    }

    @Test
    fun drawing() {
        assertEquals(
            """
            
            ##
            #.
            ..
            ##
            """.trimIndent(),
            Game(parse(SMALLER_EXAMPLE)).toString()
        )
    }

    @Test
    fun emptySpaces() {
        assertEquals(27, Game(parse(EXAMPLE_ONE)).emptySpaceCount)
        assertEquals(3, Game(parse(SMALLER_EXAMPLE)).emptySpaceCount)
    }

    @Test
    fun simpleExample() {
        var game = Game(parse(SMALLER_EXAMPLE))
        assertEquals(0, game.rounds)
        assertEquals(
            """
            
            ##
            #.
            ..
            ##
            """.trimIndent(),
            game.toString()
        )

        game = game.tick()
        assertEquals(1, game.rounds)
        assertEquals(
            """
            
            ##
            ..
            #.
            .#
            #.
            """.trimIndent(),
            game.toString()
        )

        game = game.tick()
        assertEquals(2, game.rounds)
        assertEquals(
            """
            
            .##.
            #...
            ...#
            ....
            .#..
            """.trimIndent(),
            game.toString()
        )

        game = game.tick()
        assertEquals(3, game.rounds)
        assertEquals(
            """
            
            ..#..
            ....#
            #....
            ....#
            .....
            ..#..
            """.trimIndent(),
            game.toString()
        )
    }

    @Test
    fun exampleOne() {
        var game = Game(parse(EXAMPLE_ONE))
        repeat(10) { game = game.tick() }
        assertEquals(10, game.rounds)
        assertEquals(
            """

            ......#.....
            ..........#.
            .#.#..#.....
            .....#......
            ..#.....#..#
            #......##...
            ....##......
            .#........#.
            ...#.#..#...
            ............
            ...#..#..#..
            """.trimIndent(), game.toString()
        )
    }

    @Test
    fun exampleTwo() {
        assertEquals(20, firstNoOpRound(parse(EXAMPLE_ONE)))
    }

    @Test
    fun smaller() {
        assertEquals(4, firstNoOpRound(parse(SMALLER_EXAMPLE)))
    }

}
