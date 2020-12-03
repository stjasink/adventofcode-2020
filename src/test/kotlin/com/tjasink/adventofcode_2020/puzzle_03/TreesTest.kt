package com.tjasink.adventofcode_2020.puzzle_03

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class TreesTest {

    @Test
    fun `count trees encountered when going across 3 and down 1`() {
        val mapStart = """
            ..##.......
            #...#...#..
            .#....#..#.
            ..#.#...#.#
            .#...##..#.
            ..#.##.....
            .#.#.#....#
            .#........#
            #.##...#...
            #...##....#
            .#..#...#.#""".trimIndent().split('\n')

        val treeCounter = Trees()

        val slopesAndAnswers = mapOf(
                Pair(1, 1) to 2,
                Pair(3, 1) to 7,
                Pair(5, 1) to 3,
                Pair(7, 1) to 4,
                Pair(1, 2) to 2
        )

        slopesAndAnswers.forEach{
            val treeCount = treeCounter.countTrees(mapStart, it.key.first, it.key.second)
            assertEquals(it.value, treeCount)
        }

    }

    @Test
    fun `should multiply answers`() {
        val mapStart = """
            ..##.......
            #...#...#..
            .#....#..#.
            ..#.#...#.#
            .#...##..#.
            ..#.##.....
            .#.#.#....#
            .#........#
            #.##...#...
            #...##....#
            .#..#...#.#""".trimIndent().split('\n')

        val allSlopes = listOf(
                Pair(1, 1),
                Pair(3, 1),
                Pair(5, 1),
                Pair(7, 1),
                Pair(1, 2)
        )

        val product = Trees().countAndMultiplyTrees(mapStart, allSlopes)

        assertEquals(336, product)
    }
}
