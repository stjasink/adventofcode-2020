package com.tjasink.adventofcode_2020.puzzle_13

import com.tjasink.adventofcode_2020.common.Solver
import com.tjasink.adventofcode_2020.common.loadInput
import com.tjasink.adventofcode_2020.common.runAndTime

fun main() {
    val input = loadInput("puzzle_13/data.txt")
    val solver = Day13()
    runAndTime(solver, input)
}

class Day13 : Solver {

    override fun part1(input: List<String>): Long {
        val myEarliestArrival = input[0].toInt()
        val busIds = input[1].split(',').filterNot { it == "x" }.map { it.toInt() }
        val firstBusArrivalsAfterMyArrival = busIds.map { busId -> firstArrivalAfter(myEarliestArrival, busId) }
        val earliestBusAndWaitTime = busIds.zip(firstBusArrivalsAfterMyArrival)
            .map { Pair(it.first, it.second - myEarliestArrival) }
            .sortedBy { it.second }
            .first()

        return earliestBusAndWaitTime.first.toLong() * earliestBusAndWaitTime.second.toLong()
    }

    override fun part2(input: List<String>): Long {
        return part2v2(input)

        val busIds = input[1].split(',').map { if (it == "x") null else it.toLong() }
        val runningTotals = mutableListOf<Long?>().apply { this.addAll(busIds) }

        while (true) {
            runningTotals[0] = runningTotals[0]!! + busIds[0]!!
            var highestBusMatched = 0
            for (i in 1 until runningTotals.size) {
                if (runningTotals[i] == null) {
                    continue
                }
                while (runningTotals[i]!! < runningTotals[0]!! + i) {
                    runningTotals[i] = runningTotals[i]!! + busIds[i]!!
                }
                if (runningTotals[i] == runningTotals[0]!! + i) {
                    highestBusMatched = i
                    continue
                } else {
                    break
                }
            }
            if (highestBusMatched == busIds.size - 1) {
                return runningTotals[0]!!
            }
            if (highestBusMatched > 32) {
                println(runningTotals[0])
                println(highestBusMatched)
            }
        }
    }

    fun part2v2(input: List<String>): Long {
        val busIds = input[1].split(',').map { if (it == "x") null else it.toLong() }
        val mustBeDivisibleBy = busIds
            .mapIndexed { index, busId -> Pair(index, busId) }
            .filter { it.second != null }
            .map { it.first to it.second!! }

        val incrementBy = mustBeDivisibleBy.map { it.second }.first()
//        val incrementBy = mustBeDivisibleBy.sortedByDescending { it.second }.first().let { it.second + it.first }
        var startTime = incrementBy
        while (true) {
//            println(startTime)
            val currentlyDivisible = mustBeDivisibleBy.map { (gap, busId) ->
                (startTime + gap) % busId == 0L
            }
            if (!currentlyDivisible.contains(false)) {
                return startTime
            }
            startTime += incrementBy
        }
    }

    fun part2v3(input: List<String>): Long {
        val busIds = input[1].split(',').map { if (it == "x") null else it.toLong() }
        val mustBeDivisibleBy = busIds
            .mapIndexed { index, busId -> Pair(index, busId) }
            .filter { it.second != null }
            .map { it.first to it.second!! }

        return 0
    }

    private fun firstArrivalAfter(time: Int, busId: Int): Int {
        var arrivalTime = 0
        while (arrivalTime < time) {
            arrivalTime += busId
        }
        return arrivalTime
    }
}
