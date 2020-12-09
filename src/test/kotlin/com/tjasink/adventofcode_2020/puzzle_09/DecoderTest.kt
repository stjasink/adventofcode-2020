package com.tjasink.adventofcode_2020.puzzle_09

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class DecoderTest {

    val input = """
            35
            20
            15
            25
            47
            40
            62
            55
            65
            95
            102
            117
            150
            182
            127
            219
            299
            277
            309
            576
        """.trimIndent().split('\n').map { it.toLong() }

    @Test
    fun `should find first number that is not a sum of two previous numbers`() {
        val answer = Decoder().part1(input, 5)

        assertEquals(127L, answer)
    }

    @Test
    fun `should find sum of lowest and highest of the sequence of numbers that add up to first answer`() {
        val answer = Decoder().part2(input, 127)

        assertEquals(62L, answer)
    }
}