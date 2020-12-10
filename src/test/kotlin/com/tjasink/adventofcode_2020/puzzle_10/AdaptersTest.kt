package com.tjasink.adventofcode_2020.puzzle_10

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class AdaptersTest {

    val input1 = """
        16
        10
        15
        5
        1
        11
        7
        19
        6
        12
        4
    """.trimIndent().split('\n').map { it.toInt() }

    val input2 = """
        28
        33
        18
        42
        31
        14
        46
        20
        48
        47
        24
        23
        49
        45
        19
        38
        39
        11
        1
        32
        25
        35
        8
        17
        7
        9
        4
        2
        34
        10
        3
    """.trimIndent().split('\n').map { it.toInt() }


    @Test
    fun `should find number of 1 jolt differences multiplied by number of 3 jolt differences`() {
        val answer = Adapters().part1(input2)
        assertEquals(220, answer)
    }

    @Test
    fun `should find number of valid combinations`() {
        val answer = Adapters().part2(input2)
        assertEquals(19208, answer)
    }

    @Test
    fun `should find number of valid combinations short input`() {
        val answer = Adapters().part2(input1)
        assertEquals(8, answer)
    }
}
