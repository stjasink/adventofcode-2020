package com.tjasink.adventofcode_2020.puzzle_15

import com.tjasink.adventofcode_2020.common.Solver
import com.tjasink.adventofcode_2020.common.loadInput
import com.tjasink.adventofcode_2020.common.runAndTime

fun main() {
    val input = loadInput("puzzle_15/data.txt")
    val solver = Day15()
    runAndTime(solver, input)
}

class Day15 : Solver {

    override fun part1(input: List<String>): Long {
        val startNumbers = input.first().split(',').map { it.toInt() }
        return playGame(startNumbers, 2020).toLong()
    }

    override fun part2(input: List<String>): Long {
        val startNumbers = input.first().split(',').map { it.toInt() }
        return playGame(startNumbers, 30000000).toLong()
    }

    private fun playGame(withStartNumbers: List<Int>, untilTurn: Int): Int {
        val lastSeenAt = withStartNumbers
            .dropLast(1)
            .mapIndexed { index, i -> i to index }
            .toMap()
            .toMutableMap()

        val start = withStartNumbers.size

        var newNumber = withStartNumbers.last()
        for (turnNumber in start until untilTurn) {
            val foundAt = lastSeenAt[newNumber]
            lastSeenAt[newNumber] = turnNumber - 1
            newNumber = if (foundAt == null) 0 else turnNumber - foundAt - 1
        }

        return newNumber
    }

}
