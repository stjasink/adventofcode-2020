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
        return 0L
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

//        fun nextRoundPart2Rules(): CubePlan {
//            return nextRound(
//                inActiveFlipRule = { x, y, z -> countVisibleOccupied(x, y, z) == 0 },
//                activeFlipRule = { x, y, z -> countVisibleOccupied(x, y, z) >= 5 })
//        }

        private fun nextRound(inActiveFlipRule: (Int, Int, Int) -> Boolean, activeFlipRule: (Int, Int, Int) -> Boolean): CubePlan {
            val newPlan = mutableListOf<MutableList<String>>()
            for (z in -1 until zSize + 1) {
                val newYBit = mutableListOf<String>()
                for (y in -1 until xSize + 1) {
                    val newRow = StringBuilder()
                    for (x in -1 until ySize + 1) {
                        val newState = when (stateAt(x, y, z)) {
                            InActive -> {
                                if (inActiveFlipRule(x, y, z)) {
                                    Active
                                } else {
                                    InActive
                                }
                            }
                            Active -> {
                                if (activeFlipRule(x, y, z)) {
                                    InActive
                                } else {
                                    Active
                                }
                            }
                            else -> throw IllegalStateException("WAT")
                        }
                        newRow.append(newState)
                    }
                    newYBit.add(newRow.toString())
//                    println()
                }
                newPlan.add(newYBit)
//                println()
//                println()
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
//            print(count)

            return count
        }

//        private fun countVisibleOccupied(xPos: Int, yPos: Int): Int {
//            var count = 0
//
//            if (xPos < xSize - 1) {
//                for (x in xPos + 1 until xSize) {
//                    if (data[yPos][x] == Active) {
//                        count += 1
//                        break
//                    } else if (data[yPos][x] == EmptySeat) {
//                        break
//                    }
//                }
//            }
//            if (xPos > 0) {
//                for (x in xPos - 1 downTo 0) {
//                    if (data[yPos][x] == Active) {
//                        count += 1
//                        break
//                    } else if (data[yPos][x] == EmptySeat) {
//                        break
//                    }
//                }
//            }
//
//            if (yPos < ySize - 1) {
//                for (y in yPos + 1 until ySize) {
//                    if (data[y][xPos] == Active) {
//                        count += 1
//                        break
//                    } else if (data[y][xPos] == EmptySeat) {
//                        break
//                    }
//                }
//            }
//            if (yPos > 0) {
//                for (y in yPos - 1 downTo 0) {
//                    if (data[y][xPos] == Active) {
//                        count += 1
//                        break
//                    } else if (data[y][xPos] == EmptySeat) {
//                        break
//                    }
//                }
//            }
//
//            run {
//                var x = xPos - 1
//                var y = yPos - 1
//                while (x >= 0 && y >= 0 && x < xSize && y < ySize) {
//                    if (data[y][x] == Active) {
//                        count += 1
//                        break
//                    } else if (data[y][x] == EmptySeat) {
//                        break
//                    }
//                    x -= 1
//                    y -= 1
//                }
//            }
//
//            run {
//                var x = xPos + 1
//                var y = yPos + 1
//                while (x >= 0 && y >= 0 && x < xSize && y < ySize) {
//                    if (data[y][x] == Active) {
//                        count += 1
//                        break
//                    } else if (data[y][x] == EmptySeat) {
//                        break
//                    }
//                    x += 1
//                    y += 1
//                }
//            }
//
//            run {
//                var x = xPos - 1
//                var y = yPos + 1
//                while (x >= 0 && y >= 0 && x < xSize && y < ySize) {
//                    if (data[y][x] == Active) {
//                        count += 1
//                        break
//                    } else if (data[y][x] == EmptySeat) {
//                        break
//                    }
//                    x -= 1
//                    y += 1
//                }
//            }
//
//            run {
//                var x = xPos + 1
//                var y = yPos - 1
//                while (x >= 0 && y >= 0 && x < xSize && y < ySize) {
//                    if (data[y][x] == Active) {
//                        count += 1
//                        break
//                    } else if (data[y][x] == EmptySeat) {
//                        break
//                    }
//                    x += 1
//                    y -= 1
//                }
//            }
//
//            return count
//        }

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

        fun print() {
            for (z in 0 until zSize) {
                println("z = $z")
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
}
