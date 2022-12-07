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

    @Test
    fun exampleTwo() {
        assertEquals(24933642, partTwo(parse(EXAMPLE_ONE)))
    }

    @Test
    fun render() {
        val str = draw(parse(EXAMPLE_ONE))
        println(str)
        assertEquals(
            """
                - / (dir)
                  - a (dir)
                    - e (dir)
                      - i (file, size=584)
                    - f (file, size=29116)
                    - g (file, size=2557)
                    - h.lst (file, size=62596)
                  - b.txt (file, size=14848514)
                  - c.dat (file, size=8504156)
                  - d (dir)
                    - j (file, size=4060174)
                    - d.log (file, size=8033020)
                    - d.ext (file, size=5626152)
                    - k (file, size=7214296)
            """.trimIndent(), str
        )
    }

}