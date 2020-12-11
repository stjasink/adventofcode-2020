package com.tjasink.adventofcode_2020.puzzle_11

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day11Test {

    val input = """
        L.LL.LL.LL
        LLLLLLL.LL
        L.L.L..L..
        LLLL.LL.LL
        L.LL.LL.LL
        L.LLLLL.LL
        ..L.L.....
        LLLLLLLLLL
        L.LLLLLL.L
        L.LLLLL.LL
    """.trimIndent().split('\n')

    @Test
    fun part1Test() {
        val answer = Day11().part1(input)
        Assertions.assertEquals(37, answer)
    }

    @Test
    fun part2Test() {
        val answer = Day11().part2(input)
        Assertions.assertEquals(26, answer)
    }
}
