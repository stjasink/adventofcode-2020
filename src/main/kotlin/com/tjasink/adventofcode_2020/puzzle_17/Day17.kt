package com.tjasink.adventofcode_2020.puzzle_17

import com.tjasink.adventofcode_2020.common.Solver
import com.tjasink.adventofcode_2020.common.loadInput
import com.tjasink.adventofcode_2020.common.runAndTime
import java.lang.StringBuilder

fun main() {
    val input = loadInput("puzzle_17/data.txt")
    val solver = Day17()
    runAndTime(solver, input)
}

class Day17 : Solver {

    override fun part1(input: List<String>): Long {
        var plan = CubePlan(listOf(input))
//        plan.print()
        for (round in 0 until 6) {
            val newPlan = plan.nextRoundPart1Rules()
//            newPlan.print()
            plan = newPlan
        }

        return plan.countAllActive().toLong()
    }

    override fun part2(input: List<String>): Long {
        var plan = HyperCubePlan(listOf(listOf(input)))
        for (round in 0 until 6) {
            val newPlan = plan.nextRoundPart2Rules()
            plan = newPlan
        }

        return plan.countAllActive().toLong()
    }

    class CubePlan(
        val data: List<List<String>>
    ) {
        val xSize = data.first().first().length
        val ySize = data.first().size
        val zSize = data.size

        val Active = '#'
        val InActive = '.'

        fun nextRoundPart1Rules(): CubePlan {
            return nextRound(
                inActiveFlipRule = { x, y, z -> countAdjacentActive(x, y, z) == 3 },
                activeFlipRule = { x, y, z ->
                    val activeNeighbours = countAdjacentActive(x, y, z)
                    activeNeighbours != 2 && activeNeighbours != 3
                }
            )
        }

        private fun nextRound(inActiveFlipRule: (Int, Int, Int) -> Boolean, activeFlipRule: (Int, Int, Int) -> Boolean): CubePlan {
            val newPlan = mutableListOf<MutableList<String>>()
            for (z in -1 until zSize + 1) {
                val newYBit = mutableListOf<String>()
                for (y in -1 until xSize + 1) {
                    val newRow = StringBuilder()
                    for (x in -1 until ySize + 1) {
                        val newState = when (stateAt(x, y, z)) {
                            InActive -> if (inActiveFlipRule(x, y, z)) Active else InActive
                            Active -> if (activeFlipRule(x, y, z)) InActive else Active
                            else -> throw IllegalStateException("WAT")
                        }
                        newRow.append(newState)
                    }
                    newYBit.add(newRow.toString())
                }
                newPlan.add(newYBit)
            }

            return CubePlan(newPlan)
        }

        private fun stateAt(xPos: Int, yPos: Int, zPos: Int): Char {
            return if (xPos < 0 || xPos >= xSize || yPos < 0 || yPos >= ySize || zPos < 0 || zPos >= zSize) {
                InActive
            } else {
                data[zPos][yPos][xPos]
            }
        }

        private fun countAdjacentActive(xPos: Int, yPos: Int, zPos: Int): Int {
            var count = 0
            for (z in zPos - 1 .. zPos + 1) {
                for (y in yPos - 1 .. yPos + 1) {
                    for (x in xPos - 1 .. xPos + 1) {
                        if (x >= 0 && y >= 0 && z >= 0
                            && x < xSize && y < ySize && z < zSize
                            && !(x == xPos && y == yPos && z == zPos)) {
                            if (stateAt(x, y, z) == Active) {
                                count += 1
                            }
                        }
                    }
                }
            }

            return count
        }

        fun countAllActive(): Int {
            var count = 0
            for (z in 0 until zSize) {
                for (y in 0 until ySize) {
                    for (x in 0 until xSize) {
                        if (stateAt(x, y, z) == Active) {
                            count += 1
                        }
                    }
                }
            }
            return count
        }

        fun print(roundNum: Int) {
            for (z in 0 until zSize) {
                println("z = ${z-roundNum}")
                println()
                for (y in 0 until ySize) {
                    println(rowAt(y, z))
                }
                println()
            }
        }

        private fun rowAt(yPos: Int, zPos: Int): String {
            return data[zPos][yPos]
        }
    }

    class HyperCubePlan(
        val data: List<List<List<String>>>
    ) {
        val xSize = data.first().first().first().length
        val ySize = data.first().first().size
        val zSize = data.first().size
        val wSize = data.size

        val Active = '#'
        val InActive = '.'

        fun nextRoundPart2Rules(): HyperCubePlan {
            return nextRound(
                inActiveFlipRule = { x, y, z, w -> countAdjacentActive(x, y, z, w) == 3 },
                activeFlipRule = { x, y, z, w ->
                    val activeNeighbours = countAdjacentActive(x, y, z, w)
                    activeNeighbours != 2 && activeNeighbours != 3
                }
            )
        }

        private fun nextRound(inActiveFlipRule: (Int, Int, Int, Int) -> Boolean, activeFlipRule: (Int, Int, Int, Int) -> Boolean): HyperCubePlan {
            val newPlan = mutableListOf<MutableList<MutableList<String>>>()
            for (w in -1 until wSize + 1) {
                val newZBit = mutableListOf<MutableList<String>>()
                for (z in -1 until zSize + 1) {
                    val newYBit = mutableListOf<String>()
                    for (y in -1 until xSize + 1) {
                        val newRow = StringBuilder()
                        for (x in -1 until ySize + 1) {
                            val newState = when (stateAt(x, y, z, w)) {
                                InActive -> if (inActiveFlipRule(x, y, z, w)) Active else InActive
                                Active -> if (activeFlipRule(x, y, z, w)) InActive else Active
                                else -> throw IllegalStateException("WAT")
                            }
                            newRow.append(newState)
                        }
                        newYBit.add(newRow.toString())
                    }
                    newZBit.add(newYBit)
                }
                newPlan.add(newZBit)
            }

            return HyperCubePlan(newPlan)
        }

        private fun stateAt(xPos: Int, yPos: Int, zPos: Int, wPos: Int): Char {
            return if (xPos < 0 || xPos >= xSize
                || yPos < 0 || yPos >= ySize
                || zPos < 0 || zPos >= zSize
                || wPos < 0 || wPos >= wSize) {
                InActive
            } else {
                data[wPos][zPos][yPos][xPos]
            }
        }

        private fun countAdjacentActive(xPos: Int, yPos: Int, zPos: Int, wPos: Int): Int {
            var count = 0
            for (w in wPos - 1 .. wPos + 1) {
                for (z in zPos - 1..zPos + 1) {
                    for (y in yPos - 1..yPos + 1) {
                        for (x in xPos - 1..xPos + 1) {
                            if (x >= 0 && y >= 0 && z >= 0 && w >= 0
                                && x < xSize && y < ySize && z < zSize && w < wSize
                                && !(x == xPos && y == yPos && z == zPos && w == wPos)
                            ) {
                                if (stateAt(x, y, z, w) == Active) {
                                    count += 1
                                }
                            }
                        }
                    }
                }
            }
//            print(count)

            return count
        }

        fun countAllActive(): Int {
            var count = 0
            for (w in 0 until wSize) {
                for (z in 0 until zSize) {
                    for (y in 0 until ySize) {
                        for (x in 0 until xSize) {
                            if (stateAt(x, y, z, w) == Active) {
                                count += 1
                            }
                        }
                    }
                }
            }
            return count
        }

        fun print(roundNum: Int) {
            for (w in 0 until wSize) {
                for (z in 0 until zSize) {
                    println("z=${z-roundNum}, w=${w-roundNum}")
                    println()
                    for (y in 0 until ySize) {
                        println(rowAt(y, z, w))
                    }
                    println()
                }
            }
        }

        private fun rowAt(yPos: Int, zPos: Int, wPos: Int): String {
            return data[wPos][zPos][yPos]
        }
    }
}
