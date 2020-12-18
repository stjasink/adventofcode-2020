package com.tjasink.adventofcode_2020.puzzle_17

import org.junit.jupiter.api.Assertions.assertEquals
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
        assertEquals(112, answer)
    }

    @Test
    fun part2Test() {
        val input = """
            .#.
            ..#
            ###
        """.trimIndent().split('\n')

        val answer = Day17().part2(input)
        assertEquals(848, answer)
    }

    @Test
    fun testFor5D() {
        val input = """
            .#.
            ..#
            ###
        """.trimIndent().split('\n')

        val answer = Day17().calculateForStartPlaneAndDimensions(input, 5)
        assertEquals(5760, answer)
    }

    @Test
    fun `should find neighbours for point`() {
        val inputsAndExpectedOutputs = mapOf(
            listOf(0) to setOf(listOf(-1), listOf(1)),
            listOf(5) to setOf(listOf(4), listOf(6)),
            listOf(0,0) to setOf(listOf(-1,-1), listOf(-1, 0), listOf(-1, 1), listOf(0, -1), listOf(0, 1), listOf(1, -1), listOf(1, 0), listOf(1, 1))
        )

        inputsAndExpectedOutputs.forEach {
            val output = Day17.Plan.findNeighboursFor(it.key)
            assertEquals(it.value, output)
        }

    }

}
