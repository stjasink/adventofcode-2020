package com.tjasink.adventofcode_2020.puzzle_09

import java.util.*

fun main() {
    val input = ClassLoader.getSystemResourceAsStream("puzzle_09/numbers.txt")
        .bufferedReader()
        .lineSequence()
        .toList()

    for (i in 1..5) {
        val start1 = Date()
        val part1Answer = Decoder().part1(input.map { it.toLong() }, 25)
        println("Part 1 time: ${Date().time - start1.time}")
        println("Part 1 answer: ${part1Answer}")

        val start2 = Date()
        val part2Answer = Decoder().part2(input.map { it.toLong() }, part1Answer)
        println("Part 2 time: ${Date().time - start2.time}")
        println("Part 2 answer: $part2Answer")
    }


}

class Decoder {

    fun part2(numbers: List<Long>, lookFor: Long): Long {
        for (startIndex in numbers.indices) {
            val numbersToAdd = mutableListOf<Long>()
            var currentTotal = 0L
            var i = 0
            while (currentTotal < lookFor) {
                numbersToAdd.add(numbers[startIndex + i])
                currentTotal += numbers[startIndex + i]
                i += 1
            }
            if (currentTotal == lookFor) {
                val min = numbersToAdd.minOrNull()!!
                val max = numbersToAdd.maxOrNull()!!
                return min + max
            }
        }
        return 0
    }

    fun part1(numbers: List<Long>, preambleSize: Int): Long {
        numbers.forEachIndexed { index, number ->
            if (index >= preambleSize) {
                val previousNumbers = numbers.subList(index - preambleSize, index)
                val sumsOfPreviousNumbers = sumsOfPairsOf(previousNumbers)
                if (!sumsOfPreviousNumbers.contains(number)) {
                    return number
                }
            }
        }
        return 0
    }

    private fun sumsOfPairsOf(previousNumbers: List<Long>): Set<Long> {
        val sums = mutableSetOf<Long>()
        previousNumbers.forEach { num1 ->
            previousNumbers.forEach { num2 ->
                if (num1 != num2) {
                    sums.add(num1 + num2)
                }
            }
        }
        return sums
    }


}
