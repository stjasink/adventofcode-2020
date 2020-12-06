package com.tjasink.adventofcode_2020.puzzle_05

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class BoardingTest {

    @Test
    fun `should decode boarding pass directly to id`() {
        val boarding = Boarding()

//        BFFFBBFRRR: row 70, column 7, seat ID 567.
//        FFFBBBFRRR: row 14, column 7, seat ID 119.
//        BBFFBBFRLL: row 102, column 4, seat ID 820.

        assertEquals(567, boarding.seatId("BFFFBBFRRR"))
        assertEquals(119, boarding.seatId("FFFBBBFRRR"))
        assertEquals(820, boarding.seatId("BBFFBBFRLL"))
    }

    @Test
    fun `should find highest id in boarding pass list`() {
        val input = listOf(
            "BFFFBBFRRR",
            "FFFBBBFRRR",
            "BBFFBBFRLL"
        )

        assertEquals(820, Boarding().highestSeatNumber(input))
    }
}