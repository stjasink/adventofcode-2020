package com.tjasink.adventofcode_2020.puzzle_21

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day21Test {
    val input = """
        mxmxvkd kfcds sqjhc nhms (contains dairy, fish)
        trh fvjkl sbzzf mxmxvkd (contains dairy)
        sqjhc fvjkl (contains soy)
        sqjhc mxmxvkd sbzzf (contains fish)
    """.trimIndent().split('\n')

    @Test
    fun part1Test() {
        val answer = Day21().part1(input)
        assertEquals(5, answer)
    }

    @Test
    fun part2Test() {
        val answer = Day21().part2(input)
        assertEquals(273, answer)

    }

}
