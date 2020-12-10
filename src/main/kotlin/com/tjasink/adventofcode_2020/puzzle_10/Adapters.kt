package com.tjasink.adventofcode_2020.puzzle_10

import java.lang.IllegalStateException
import java.util.*

fun main() {
    val input = ClassLoader.getSystemResourceAsStream("puzzle_10/adapters.txt")
        .bufferedReader()
        .lineSequence()
        .toList()

    for (i in 1..5) {
        val start1 = Date()
        val part1Answer = Adapters().part1(input.map { it.toInt() })
        println("Part 1 time: ${Date().time - start1.time}")
        println("Part 1 answer: ${part1Answer}")

        val start2 = Date()
        val part2Answer = Adapters().part2(input.map { it.toInt() })
        println("Part 2 time: ${Date().time - start2.time}")
        println("Part 2 answer: $part2Answer")
    }


}

class Adapters {

    fun part1(adapters: List<Int>): Int {
        val numGaps = arrayOf(0, 0, 0)
        val sortedAdapters = adapters.sorted()
        for (i in adapters.indices) {
            val diff = sortedAdapters[i] - (if (i == 0) 0 else sortedAdapters[i - 1])
            numGaps[diff - 1] += 1
        }
        numGaps[2] += 1
        return numGaps[0] * numGaps[2]
    }

    fun part2(adapters: List<Int>): Long {
        val sortedAdapters = sortAndAddStartAndEnd(adapters)
        val gapSizes = findGapSizes(sortedAdapters)
        val runsOfOneJoltGaps = findRunsOfOneJoltGaps(gapSizes)
        val combinationsForEachRunOfOneJoltGaps = runsOfOneJoltGaps.map {
            combinationsForOneJoltGaps(it)
        }
        return combinationsForEachRunOfOneJoltGaps.reduce { acc, i -> acc * i }
    }

    private fun sortAndAddStartAndEnd(adapters: List<Int>): MutableList<Int> {
        val sortedAdapters = adapters.sorted().toMutableList()
        sortedAdapters.add(0, 0)
        sortedAdapters.add(sortedAdapters.last() + 3)
        return sortedAdapters
    }

    private fun findGapSizes(sortedAdapters: MutableList<Int>): MutableList<Int> {
        val gapSizes = mutableListOf<Int>()
        for (i in sortedAdapters.indices) {
            val diff = sortedAdapters[i] - (if (i == 0) 0 else sortedAdapters[i - 1])
            gapSizes.add(diff)
        }
        return gapSizes
    }

    private fun findRunsOfOneJoltGaps(gapSizes: MutableList<Int>): MutableList<Int> {
        val runsOfOneJoltGaps = mutableListOf<Int>()
        var numOnes = 0
        for (i in gapSizes) {
            if (i == 1) {
                numOnes += 1
            } else {
                if (numOnes > 0) {
                    runsOfOneJoltGaps.add(numOnes)
                }
                numOnes = 0
            }
        }
        return runsOfOneJoltGaps
    }
    
    private fun combinationsForOneJoltGaps(it: Int) = when (it) {
        4 -> 7L
        3 -> 4L
        2 -> 2L
        1 -> 1L
        else -> throw IllegalStateException("Found unexpected run of $it")
    }

}
