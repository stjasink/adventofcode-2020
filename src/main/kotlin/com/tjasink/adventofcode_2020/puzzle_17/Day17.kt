package com.tjasink.adventofcode_2020.puzzle_17

import com.tjasink.adventofcode_2020.common.Solver
import com.tjasink.adventofcode_2020.common.loadInput
import com.tjasink.adventofcode_2020.common.runAndTime
import java.lang.StringBuilder
import kotlin.math.max

fun main() {
    val input = loadInput("puzzle_17/data.txt")
    val solver = Day17()
    runAndTime(solver, input)
}

class Day17 : Solver {

    override fun part1(input: List<String>): Long {
        return calculateForStartPlaneAndDimensions(input, 3)
    }

    override fun part2(input: List<String>): Long {
        return calculateForStartPlaneAndDimensions(input, 4)
    }

    fun calculateForStartPlaneAndDimensions(
        input: List<String>,
        numDimensionsNeeded: Int
    ): Long {
        val startPoints = generateStartPoints(input, numDimensionsNeeded)

        var plan = Plan(startPoints)
        for (round in 0 until 6) {
            val newPlan = plan.nextRound()
            plan = newPlan
        }

        return plan.countAllActive().toLong()
    }

    private fun generateStartPoints(
        input: List<String>,
        numDimensionsNeeded: Int
    ): Set<Vector> {
        val startPoints = mutableSetOf<Vector>()
        for (y in input.indices) {
            for (x in input[y].indices) {
                if (input[y][x] == '#') {
                    val components2d = listOf(x, y)
                    val moreDimensionsNeeded = numDimensionsNeeded - 2
                    val components = components2d + Array(moreDimensionsNeeded) { 0 }
                    startPoints.add(components)
                }
            }
        }
        return startPoints
    }

    class Plan(
        val activePoints: Set<Vector>
    ) {
        fun nextRound(): Plan {
            val newActivePoints = mutableSetOf<Vector>()

            val inactiveNeighbours = findAllInactiveNeighboursOfActivePoints()

            for (point in inactiveNeighbours) {
                val numActiveNeighbours = countActiveNeighboursFor(point, activePoints)
                val isNewActive = (numActiveNeighbours == 3)
                if (isNewActive) {
                    newActivePoints.add(point)
                }
            }

            for (point in activePoints) {
                val numActiveNeighbours = countActiveNeighboursFor(point, activePoints)
                val isNewActive = (numActiveNeighbours == 2 || numActiveNeighbours == 3)
                if (isNewActive) {
                    newActivePoints.add(point)
                }
            }

            return Plan(newActivePoints)
        }

        fun countActiveNeighboursFor(point: Vector, activePoints: Set<Vector>): Int {
            val neighbours = findNeighboursFor(point)
            val activeNeighbours = neighbours.intersect(activePoints)
//            println("Counting for ${point}")
//            activeNeighbours.forEach {
//                println(it)
//            }
            return activeNeighbours.size
        }

        fun findAllInactiveNeighboursOfActivePoints(): Set<Vector> {
            val allNeighbours = activePoints.flatMap { findNeighboursFor(it) }.toSet()
            return allNeighbours - activePoints
        }

        fun countAllActive() = activePoints.size


        companion object {
            fun findNeighboursFor(point: Vector): Set<Vector> {
                return findNeighboursAndSelfFor(point) - setOf(point)
            }

            fun findNeighboursAndSelfFor(point: Vector): Set<Vector> {
                if (point.size == 1) {
                    return setOf(listOf(point.first() - 1), listOf(point.first()), listOf(point.first() + 1))
                }
                val neighboursIgnoringFirstDimension = findNeighboursAndSelfFor(point.drop(1))
                val allComponents =
                    (point.first() - 1..point.first() + 1).flatMap { newFirstDimValue ->
                        neighboursIgnoringFirstDimension.map { listOf(newFirstDimValue) + it }
                    }
                return allComponents.toSet()
            }
        }
    }
}

typealias Vector = List<Int>

