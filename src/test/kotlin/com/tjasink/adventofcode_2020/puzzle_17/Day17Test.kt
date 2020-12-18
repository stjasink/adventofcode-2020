package com.tjasink.adventofcode_2020.puzzle_17

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import com.tjasink.adventofcode_2020.puzzle_17.Day17.Plan.Vector

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
            Vector(0) to setOf(Vector(-1), Vector(1)),
            Vector(5) to setOf(Vector(4), Vector(6)),
            Vector(0,0) to setOf(Vector(-1,-1), Vector(-1, 0), Vector(-1, 1), Vector(0, -1), Vector(0, 1), Vector(1, -1), Vector(1, 0), Vector(1, 1))
        )

        inputsAndExpectedOutputs.forEach {
            val output = Day17.Plan.findNeighboursFor(it.key)
            assertEquals(it.value, output)
        }

    }

}
