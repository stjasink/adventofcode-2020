package com.tjasink.adventofcode_2020.puzzle_14

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day14Test {

    @Test
    fun part1Test() {
        val input = """
        mask = XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X
        mem[8] = 11
        mem[7] = 101
        mem[8] = 0
    """.trimIndent().split('\n')

        val answer = Day14().part1(input)
        Assertions.assertEquals(165, answer)
    }

    @Test
    fun part2Test() {
        val input = """
            mask = 000000000000000000000000000000X1001X
            mem[42] = 100
            mask = 00000000000000000000000000000000X0XX
            mem[26] = 1
    """.trimIndent().split('\n')

        val answer = Day14().part2(input)
        Assertions.assertEquals(208, answer)
    }

}
