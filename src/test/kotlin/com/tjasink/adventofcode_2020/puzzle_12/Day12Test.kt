package com.tjasink.adventofcode_2020.puzzle_12

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day12Test {

    val input = """
        F10
        N3
        F7
        R90
        F11
    """.trimIndent().split('\n')

    @Test
    fun part1Test() {
        val answer = Day12().part1(input)
        Assertions.assertEquals(25, answer)
    }

    @Test
    fun part2Test() {
        val answer = Day12().part2(input)
        Assertions.assertEquals(286, answer)
    }
}
