package com.tjasink.adventofcode_2020.puzzle_01

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ExpensesTest {

    @Test
    fun `should find and multiply two numbers that add up to 2020`() {
        val input = listOf(
                1,2,3,2000,20,4,5,6
        )

        val output = Expenses().calculateForTwo(input)

        assertEquals(2000 * 20, output)

    }

    @Test
    fun `should find and multiply three numbers that add up to 2020`() {
        val input = listOf(
                1,2,3,2000,20,4,5,16
        )

        val output = Expenses().calculateForThree(input)

        assertEquals(2000 * 4 * 16, output)

    }
}