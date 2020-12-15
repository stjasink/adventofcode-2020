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
        val startNumbers = input.first().split(',').map { it.toLong() }
        return playGame(startNumbers, 2020)
    }

    override fun part2(input: List<String>): Long {
        val startNumbers = input.first().split(',').map { it.toLong() }
        return playGame(startNumbers, 30000000)
    }

    private fun playGame(withStartNumbers: List<Long>, untilTurn: Long): Long {

        val lastSeenAt = mutableMapOf<Long, Long>()

        for (i in 0 until withStartNumbers.size - 1) {
            lastSeenAt[withStartNumbers[i]] = i.toLong()
        }
        var turnNumber = withStartNumbers.size
        var newNumber = withStartNumbers.last()

        while (true) {
            if (turnNumber % 1000000 == 0) {
                println(turnNumber)
            }
            if (turnNumber.toLong() == untilTurn) {
                return newNumber
            }

            val foundAt = lastSeenAt[newNumber]
            lastSeenAt[newNumber] = turnNumber.toLong() - 1

            if (foundAt == null) {
                newNumber = 0
            } else {
                val age = turnNumber - foundAt - 1
                newNumber = age
            }

            turnNumber += 1
        }
    }

}
