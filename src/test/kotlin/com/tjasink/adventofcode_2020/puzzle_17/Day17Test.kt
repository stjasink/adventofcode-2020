package com.tjasink.adventofcode_2020.puzzle_17

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day17Test {
    
    @Test
    fun part1Test() {
        val input = """
            .#.
            ..#
            ###
        """.trimIndent().split('\n')

        val answer = Day17().part1(input)
        Assertions.assertEquals(112, answer)
    }

    @Test
    fun part2Test() {
        val input = """
            .#.
            ..#
            ###
        """.trimIndent().split('\n')

        val answer = Day17().part2(input)
        Assertions.assertEquals(848, answer)

    }

}
