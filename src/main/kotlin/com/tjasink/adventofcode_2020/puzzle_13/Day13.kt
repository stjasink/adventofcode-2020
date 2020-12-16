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
        return part2v3(input)

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
            // map to (phase, period)
            .mapIndexed { index, busId -> Pair(-index.toLong(), busId) }
            .filter { it.second != null }
            .map { it.first to it.second!! }

        return mustBeDivisibleBy.reduce { acc, bus ->
            val (bus1Phase, bus1Period) = acc
            val (bus2Phase, bus2Period) = bus
//            if (bus1Phase > Integer.MAX_VALUE) println("a in gcd is over")
//            println("bus1Phase less than MAX_INT: ${bus1Phase < Integer.MAX_VALUE}")
//            println("bus1Period less than MAX_INT: ${bus1Period < Integer.MAX_VALUE}")
//            println("bus2Phase less than MAX_INT: ${bus2Phase < Integer.MAX_VALUE}")
//            println("bus2Period less than MAX_INT: ${bus2Period < Integer.MAX_VALUE}")
            val (combinedPeriod, combinedPhase) = combine_phased_rotations(bus1Period, bus1Phase, bus2Period, bus2Phase)
            println("Buses $bus1Period/$bus1Phase and $bus2Period/$bus2Phase align at $combinedPeriod/$combinedPhase (${(combinedPhase + combinedPeriod) % combinedPeriod})")
            if ((combinedPhase - bus1Phase) % bus1Period != 0L) println("Bus 1 bad alignment")
            if ((combinedPhase - bus2Phase) % bus2Period != 0L) println("Bus 2 bad alignment")
            Pair(combinedPhase, combinedPeriod)
        }.let {
            (it.first + it.second) % it.second
        }
    }

//    fun bus_alignment(red_len: Long, green_len: Long, advantage: Long): Long {
//        val (period, phase) = combine_phased_rotations(
//            red_len, 0, green_len, (-advantage % green_len).let { if (it < 0) it + green_len else it }
//        )
//        return (-phase % period).let { if (it < 0) it + period else it }
//    }

    fun combine_phased_rotations(a_period: Long, a_phase: Long, b_period: Long, b_phase: Long): Pair<Long, Long> {
        if (a_period > Integer.MAX_VALUE) println("a_period in cpr is over")
        if (a_phase > Integer.MAX_VALUE) println("a_phase in cpr is over")
        if (b_period > Integer.MAX_VALUE) println("b_period in cpr is over")
        if (b_phase > Integer.MAX_VALUE) println("b_phase in cpr is over")
        val (gcd, s, t) = extended_gcd(a_period, b_period)
        val phase_difference = a_phase - b_phase
        val pd_mult = phase_difference / gcd
        val pd_remainder = phase_difference % gcd
        if (pd_remainder > 0L) throw IllegalStateException("Bus $a_period and $b_period never synchronise")
        val combined_period = (a_period / gcd) * b_period
        val combined_phase = (a_phase - s * pd_mult * a_period) % combined_period
        return Pair(combined_period, combined_phase)
    }

    fun extended_gcd(a: Long, b: Long): Triple<Long, Long, Long> {
        if (a > Integer.MAX_VALUE) println("a in gcd is over")
        if (b > Integer.MAX_VALUE) println("b in gcd is over")

        var old_r = a
        var r = b
        var old_s = 1L
        var s = 0L
        var old_t = 0L
        var t = 1L

        println("before")
        if (old_r > Integer.MAX_VALUE) println("old_r in gcd is over")
        if (old_s > Integer.MAX_VALUE) println("old_s in gcd is over")
        if (old_t > Integer.MAX_VALUE) println("old_t in gcd is over")

        while (r > 0L) {
            val quotient = old_r / r
            val remainder = old_r % r

            old_r = r
            r = remainder

            val temp_s = s
            s = old_s - quotient * s
            old_s = temp_s

            val temp_t = t
            t = old_t - quotient * t
            old_t = temp_t
        }

        println("after")
        if (old_r > Integer.MAX_VALUE) println("old_r in gcd is over")
        if (old_s > Integer.MAX_VALUE) println("old_s in gcd is over")
        if (old_t > Integer.MAX_VALUE) println("old_t in gcd is over")

        return Triple(old_r, old_s, old_t)
    }

    private fun firstArrivalAfter(time: Int, busId: Int): Int {
        var arrivalTime = 0
        while (arrivalTime < time) {
            arrivalTime += busId
        }
        return arrivalTime
    }
}
