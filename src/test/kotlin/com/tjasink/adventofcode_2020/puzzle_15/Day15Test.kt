package com.tjasink.adventofcode_2020.puzzle_15

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day15Test {

    @Test
    fun part1Test() {
        val inputsAndAnswers = listOf(
            listOf("0,3,6") to 436L,
            listOf("1,3,2") to 1L,
            listOf("2,1,3") to 10L,
            listOf("1,2,3") to 27L,
            listOf("2,3,1") to 78L,
            listOf("3,2,1") to 438L,
            listOf("3,1,2") to 1836L
        )

        inputsAndAnswers.forEach {
            val answer = Day15().part1(it.first)
            Assertions.assertEquals(it.second, answer)
        }

    }

    @Test
    fun part2Test() {
        val inputsAndAnswers = listOf(
            listOf("0,3,6") to 175594L,
            listOf("1,3,2") to 2578L,
            listOf("2,1,3") to 3544142L,
            listOf("1,2,3") to 261214L,
            listOf("2,3,1") to 6895259L,
            listOf("3,2,1") to 18L,
            listOf("3,1,2") to 362L
        )

        inputsAndAnswers.forEach {
            val answer = Day15().part2(it.first)
            Assertions.assertEquals(it.second, answer)
        }

    }

}
