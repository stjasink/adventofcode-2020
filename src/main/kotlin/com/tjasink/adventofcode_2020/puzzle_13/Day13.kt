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
        val busIds = input[1].split(',').map { if (it == "x") null else it.toLong() }
        val mustBeDivisibleBy = busIds
            // map to (period, phase)
            .mapIndexed { index, busId -> Pair(busId, index.toLong()) }
            .filter { it.first != null }
            .map { it.first!! to it.second }

        return mustBeDivisibleBy.reduce { acc, bus ->
            val (bus1Period, bus1Phase) = acc
            val (bus2Period, bus2Phase) = bus
            val combinedPeriod = bus1Period * bus2Period
            val combinedPhase = findMatchedPhase(bus1Period, bus1Phase, bus2Period, bus2Phase)
            Pair(combinedPeriod, combinedPhase)
        }.let {
            (it.first + it.second) % it.first
        }
    }

    private fun findMatchedPhase(bus1Period: Long, bus1Phase: Long, bus2Period: Long, bus2Phase: Long): Long {
        var bus1Position = bus1Phase
        while (true) {
            val potentialBus2Position = bus1Position + bus2Phase
            if (potentialBus2Position % bus2Period == 0L) {
                return bus1Position
            }
            bus1Position += bus1Period
        }
    }

    private fun firstArrivalAfter(time: Int, busId: Int): Int {
        var arrivalTime = 0
        while (arrivalTime < time) {
            arrivalTime += busId
        }
        return arrivalTime
    }
}
