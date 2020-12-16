package com.tjasink.adventofcode_2020.puzzle_13

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day13Test {

    val input = """
        939
        7,13,x,x,59,x,31,19
    """.trimIndent().split('\n')

    @Test
    fun part1Test() {
        val answer = Day13().part1(input)
        Assertions.assertEquals(295, answer)
    }

    @Test
    fun part2Test() {
        val answer = Day13().part2(input)
        Assertions.assertEquals(1068781, answer)
    }

//    @Test
//    fun part2SplitTest() {
//        val inputs = listOf(
//            "7,13,x,x,x,x,x,x" to 77L,
//            "7,x,x,x,59,x,x,x" to 350L,
//            "7,x,x,x,x,x,31,x" to 0L,
//            "7,x,x,x,x,x,x,19" to 0L,
//            "x,13,x,x,59,x,x,x" to 0L,
//            "x,13,x,x,x,x,31,x" to 0L,
//            "x,13,x,x,x,x,x,19" to 0L,
//            "7,13,x,x,59,x,x,x" to 0L,
//        )
//
//        inputs.forEach {
//            val input = listOf("ignored") + it.first
//            val answer = Day13().part2(input)
//            println(it.first + ": $answer")
////            Assertions.assertEquals(it.second, answer)
//        }
//    }

    @Test
    fun part2TestDifferentExamples() {
        val inputs = listOf(
            "17,x,13,19" to 3417L,
            "67,7,59,61" to 754018L,
            "67,x,7,59,61" to 779210L,
            "67,7,x,59,61" to 1261476L,
            "1789,37,47,1889" to 1202161486L
        )

        inputs.forEach {
            val input = listOf("ignored") + it.first
            val answer = Day13().part2(input)
            Assertions.assertEquals(it.second, answer)
        }

    }

    @Test
    fun part2RealData() {
        val input = listOf("random",
            "13,x,x,x,x,x,x,37,x,x,x,x,x,449,x,29,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,19,x,x,x,23,x,x,x,x,x,x,x,773,x,x,x,x,x,x,x,x,x,41,x,x,x,x,x,x,17")
        val answer = Day13().part2(input)
        println(answer)

        // correct:  Buses 118998919/103805195 and 23/-36 align at 2736975137/-2395172104 (341803033)
        // next one is wrong: Buses 2736975137/-2395172104 and 773/-44 align at 2115681780901/-929836888210 (1185844892691)
    }

}
