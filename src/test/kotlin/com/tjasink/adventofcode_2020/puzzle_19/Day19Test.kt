package com.tjasink.adventofcode_2020.puzzle_19

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day19Test {

    @Test
    fun part1Test() {
        val input = """
            0: 4 1 5
            1: 2 3 | 3 2
            2: 4 4 | 5 5
            3: 4 5 | 5 4
            4: "a"
            5: "b"

            ababbb
            bababa
            abbbab
            aaabbb
            aaaabbb
        """.trimIndent().split('\n')

        val answer = Day19().part1(input)
        assertEquals(2, answer)
    }

    @Test
    fun part2Test() {

    }

}
