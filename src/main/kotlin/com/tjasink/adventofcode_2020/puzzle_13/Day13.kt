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
            .minByOrNull { it.second }!!
        return earliestBusAndWaitTime.first.toLong() * earliestBusAndWaitTime.second.toLong()
    }

    override fun part2(input: List<String>): Long {
        return input[1].split(',')
            .mapIndexed { index, it -> if (it == "x") null else Bus(period = it.toLong(), phase = index.toLong()) }
            .filterNotNull()
            .reduce { accBus, newBus ->
                Bus(period = accBus.period * newBus.period, phase = findCoincidingPosition(accBus, newBus))
            }.phase
    }

    private fun findCoincidingPosition(bus1: Bus, bus2: Bus): Long {
        var position = bus1.phase
        while ((position + bus2.phase) % bus2.period != 0L) position += bus1.period
        return position
    }

    private fun firstArrivalAfter(time: Int, busId: Int): Int {
        var arrivalTime = 0
        while (arrivalTime < time) {
            arrivalTime += busId
        }
        return arrivalTime
    }

    data class Bus(
        val period: Long,
        val phase: Long
    )
}
