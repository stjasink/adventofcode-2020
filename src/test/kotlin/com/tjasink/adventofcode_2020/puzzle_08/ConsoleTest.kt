package com.tjasink.adventofcode_2020.puzzle_08

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ConsoleTest {

    val code = """
        nop +0
        acc +1
        jmp +4
        acc +3
        jmp -3
        acc -99
        acc +1
        jmp -4
        acc +6
        """.trimIndent().split('\n')

    @Test
    fun `output accumulator when executing same instruction again`() {
        val answer = Console().findAccumulatorAtLoopOrCompletion(code)

        assertEquals(Pair(5, Console.CompletionType.LOOP), answer)
    }

    @Test
    fun `output accumulator when detecting corrupted instruction and completing`() {
        val answer = Console().findAccumulatorAtSuccessfulCompletion(code)

        assertEquals(8, answer)
    }
}
