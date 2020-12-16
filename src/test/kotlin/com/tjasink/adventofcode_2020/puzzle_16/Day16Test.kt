package com.tjasink.adventofcode_2020.puzzle_16

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day16Test {


    @Test
    fun part1Test() {
        val input = """
            class: 1-3 or 5-7
            row: 6-11 or 33-44
            seat: 13-40 or 45-50
    
            your ticket:
            7,1,14
    
            nearby tickets:
            7,3,47
            40,4,50
            55,2,20
            38,6,12
    """.trimIndent().split('\n')

        val answer = Day16().part1(input)
        Assertions.assertEquals(71, answer)
    }

    @Test
    fun part2Test() {
        val input = """
            departure class: 1-3 or 5-7
            departure row: 6-11 or 33-44
            seat: 13-40 or 45-50
    
            your ticket:
            7,1,7
    
            nearby tickets:
            7,45,11
            1,99,35
            2,99,36
            3,46,37
        """.trimIndent().split('\n')

        val answer = Day16().part2(input)
        Assertions.assertEquals(49, answer)

    }

}
