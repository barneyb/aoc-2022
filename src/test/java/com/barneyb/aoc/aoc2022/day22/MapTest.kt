package com.barneyb.aoc.aoc2022.day22

import com.barneyb.util.Dir.*
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

private const val SHAPE_EXAMPLE = """      222
      222
      222
444555666
444555666
444555666
      aaabbb
      aaabbb
      aaabbb
"""

private const val SHAPE_INPUT = """   111222
   111222
   111222
   555
   555
   555
888999
888999
888999
ccc
ccc
ccc
"""

private const val SHAPE_LONG = """   111222333
   111222333
   111222333
      666
      666
      666
      aaa
      aaa
      aaa
      eee
      eee
      eee
"""

class MapTest {

    private fun makeMap(raw: String): Map {
        return parse(
            (raw.replace(Regex("[0-9a-f]"), ".")
                .lines()
                .filter(String::isNotBlank) +
                    listOf("", "movement", ""))
                .joinToString("\n")
        )
    }

    @Suppress("unused")
    private fun printAdjacent(map: Map) {
        map.adjacentEdges!!.let { edges ->
            println("${edges.size} known edges")
            edges
                .filter { (a, b) -> a.square < b.square }
                .forEach(::println)
        }
    }

    private fun assertEdge(map: Map, a: Map.Edge, b: Map.Edge) {
        val edges = map.adjacentEdges!!
        assertTrue(edges.contains(a), "$a is not known")
        assertTrue(edges.contains(b), "$b is not known")
        assertEquals(b, edges[a])
        assertEquals(a, edges[b])
    }

    @Test
    fun exampleTorus() {
        val map = makeMap(SHAPE_EXAMPLE)
        map.foldIntoTorus()
        assertEdge(map, map.Edge(2, NORTH), map.Edge(10, SOUTH))
        assertEdge(map, map.Edge(2, NORTH), map.Edge(10, SOUTH))
        assertEdge(map, map.Edge(2, WEST), map.Edge(2, EAST))
        assertEdge(map, map.Edge(4, NORTH), map.Edge(4, SOUTH))
        assertEdge(map, map.Edge(4, WEST), map.Edge(6, EAST))
        assertEdge(map, map.Edge(5, NORTH), map.Edge(5, SOUTH))
        assertEdge(map, map.Edge(10, WEST), map.Edge(11, EAST))
        assertEdge(map, map.Edge(11, NORTH), map.Edge(11, SOUTH))
    }

    @Test
    fun exampleCube() {
        val map = makeMap(SHAPE_EXAMPLE)
        map.foldIntoCube()
        assertEdge(map, map.Edge(2, WEST), map.Edge(5, NORTH))
        assertEdge(map, map.Edge(2, NORTH), map.Edge(4, NORTH))
        assertEdge(map, map.Edge(5, SOUTH), map.Edge(10, WEST))
        assertEdge(map, map.Edge(11, NORTH), map.Edge(6, EAST))
        assertEdge(map, map.Edge(2, EAST), map.Edge(11, EAST))
        assertEdge(map, map.Edge(10, SOUTH), map.Edge(4, SOUTH))
        assertEdge(map, map.Edge(4, WEST), map.Edge(11, SOUTH))
    }

    @Test
    fun inputTorus() {
        val map = makeMap(SHAPE_INPUT)
        map.foldIntoTorus()
        assertEdge(map, map.Edge(1, NORTH), map.Edge(9, SOUTH))
        assertEdge(map, map.Edge(1, WEST), map.Edge(2, EAST))
        assertEdge(map, map.Edge(2, NORTH), map.Edge(2, SOUTH))
        assertEdge(map, map.Edge(5, WEST), map.Edge(5, EAST))
        assertEdge(map, map.Edge(8, NORTH), map.Edge(12, SOUTH))
        assertEdge(map, map.Edge(8, WEST), map.Edge(9, EAST))
        assertEdge(map, map.Edge(12, WEST), map.Edge(12, EAST))
    }

    @Test
    fun inputCube() {
        val map = makeMap(SHAPE_INPUT)
        map.foldIntoCube()
        assertEdge(map, map.Edge(1, NORTH), map.Edge(12, WEST))
        assertEdge(map, map.Edge(1, WEST), map.Edge(8, WEST))
        assertEdge(map, map.Edge(2, NORTH), map.Edge(12, SOUTH))
        assertEdge(map, map.Edge(5, WEST), map.Edge(8, NORTH))
        assertEdge(map, map.Edge(5, EAST), map.Edge(2, SOUTH))
        assertEdge(map, map.Edge(9, SOUTH), map.Edge(12, EAST))
        assertEdge(map, map.Edge(2, EAST), map.Edge(9, EAST))
    }

    @Test
    fun longTorus() {
        val map = makeMap(SHAPE_LONG)
        map.foldIntoTorus()
        assertEdge(map, map.Edge(1, NORTH), map.Edge(1, SOUTH))
        assertEdge(map, map.Edge(2, NORTH), map.Edge(14, SOUTH))
        assertEdge(map, map.Edge(3, NORTH), map.Edge(3, SOUTH))
        assertEdge(map, map.Edge(1, WEST), map.Edge(3, EAST))
        assertEdge(map, map.Edge(6, WEST), map.Edge(6, EAST))
        assertEdge(map, map.Edge(10, WEST), map.Edge(10, EAST))
        assertEdge(map, map.Edge(14, WEST), map.Edge(14, EAST))
    }

    @Test
    fun longCube() {
        val map = makeMap(SHAPE_LONG)
        map.foldIntoCube()
        assertEdge(map, map.Edge(1, SOUTH), map.Edge(6, WEST))
        assertEdge(map, map.Edge(1, WEST), map.Edge(10, WEST))
        assertEdge(map, map.Edge(1, NORTH), map.Edge(14, WEST))
        assertEdge(map, map.Edge(2, NORTH), map.Edge(14, SOUTH))
        assertEdge(map, map.Edge(3, SOUTH), map.Edge(6, EAST))
        assertEdge(map, map.Edge(3, EAST), map.Edge(10, EAST))
        assertEdge(map, map.Edge(3, NORTH), map.Edge(14, EAST))
    }

}
