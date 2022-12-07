package com.barneyb.aoc.aoc2022.day07

import com.barneyb.aoc.util.Slice
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

private const val EXAMPLE_ONE = """
${'$'} cd /
${'$'} ls
dir a
14848514 b.txt
8504156 c.dat
dir d
${'$'} cd a
${'$'} ls
dir e
29116 f
2557 g
62596 h.lst
${'$'} cd e
${'$'} ls
584 i
${'$'} cd ..
${'$'} cd ..
${'$'} cd d
${'$'} ls
4060174 j
8033020 d.log
5626152 d.ext
7214296 k
"""

class NoSpaceLeftOnDeviceKtTest {

    @Test
    fun parse() {
        assertEquals(Slice("/"), parse(EXAMPLE_ONE).name)
    }

    @Test
    fun exampleOne() {
        assertEquals(95437, partOne(parse(EXAMPLE_ONE)))
    }
}
