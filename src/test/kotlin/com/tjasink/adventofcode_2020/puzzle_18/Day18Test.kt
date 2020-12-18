package com.tjasink.adventofcode_2020.puzzle_18

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day18Test {

    @Test
    fun part1Test() {
        val inputsAndAnswers = listOf(
            listOf("1 + 2 * 3 + 4 * 5 + 6") to 71,
            listOf("1 + (2 * 3) + (4 * (5 + 6))") to 51,
            listOf("2 * 3 + (4 * 5)") to 26,
            listOf("5 + (8 * 3 + 9 + 3 * 4 * 3)") to 437,
            listOf("5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4))") to 12240,
            listOf("((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2") to 13632,
            listOf(
                "1 + 2 * 3 + 4 * 5 + 6",
                "1 + (2 * 3) + (4 * (5 + 6))",
                "2 * 3 + (4 * 5)",
                "5 + (8 * 3 + 9 + 3 * 4 * 3)",
                "5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4))",
                "((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2"
            ) to 71 + 51 + 26 + 437 + 12240 + 13632
        )

        inputsAndAnswers.forEach {
            val answer = Day18().part1(it.first)
            assertEquals(it.second, answer.toInt())
        }

    }

    @Test
    fun part2Test() {
        val inputsAndAnswers = listOf(
            listOf("1 + 2 * 3 + 4 * 5 + 6") to 231,
            listOf("1 + (2 * 3) + (4 * (5 + 6))") to 51,
            listOf("2 * 3 + (4 * 5)") to 46,
            listOf("5 + (8 * 3 + 9 + 3 * 4 * 3)") to 1445,
            listOf("5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4))") to 669060,
            listOf("((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2") to 23340,
            listOf(
                "1 + 2 * 3 + 4 * 5 + 6",
                "1 + (2 * 3) + (4 * (5 + 6))",
                "2 * 3 + (4 * 5)",
                "5 + (8 * 3 + 9 + 3 * 4 * 3)",
                "5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4))",
                "((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2"
            ) to 231 + 51 + 46 + 1445 + 669060 + 23340
        )

        inputsAndAnswers.forEach {
            val answer = Day18().part2(it.first)
            assertEquals(it.second, answer.toInt())
        }
    }

}
