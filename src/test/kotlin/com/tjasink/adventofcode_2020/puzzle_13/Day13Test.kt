package com.tjasink.adventofcode_2020.puzzle_13

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day13Test {

    val input = """""".trimIndent().split('\n')

    @Test
    fun part1Test() {
        val answer = Day13().part1(input)
        Assertions.assertEquals(25, answer)
    }

    @Test
    fun part2Test() {
        val answer = Day13().part2(input)
        Assertions.assertEquals(286, answer)
    }
}
