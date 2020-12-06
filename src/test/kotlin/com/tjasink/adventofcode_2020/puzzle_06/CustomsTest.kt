package com.tjasink.adventofcode_2020.puzzle_06

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class CustomsTest {

    @Test
    fun `should count and add group answers part 1`() {
        val input = """
            abc

            a
            b
            c

            ab
            ac

            a
            a
            a
            a

            b
        """.trimIndent().split('\n')

        val actual = Customs().collateAndCountAndAddGrouupAnswersForAnyonesAnswers(input)
        assertEquals(11L, actual)
    }

    @Test
    fun `should count and add group answers part 2`() {
        val input = """
            abc

            a
            b
            c

            ab
            ac

            a
            a
            a
            a

            b
        """.trimIndent().split('\n')

        val actual = Customs().collateAndCountAndAddGrouupAnswersForEveryonesAnswers(input)
        assertEquals(6L, actual)
    }
}