package com.tjasink.adventofcode_2020.puzzle_11

import java.lang.StringBuilder
import java.time.LocalDateTime
import java.time.Duration

fun main() {
    val input = ClassLoader.getSystemResourceAsStream("puzzle_11/data.txt")
        .bufferedReader()
        .lineSequence()
        .toList()

    for (i in 1..5) {
        val start1 = LocalDateTime.now()
        val part1Answer = Day11().part1(input.map { it })
        println("Part 1 time: ${Duration.between(start1, LocalDateTime.now()).toMillis()}μs")
        println("Part 1 answer: $part1Answer")

        val start2 = LocalDateTime.now()
        val part2Answer = Day11().part2(input.map { it })
        println("Part 2 time: ${Duration.between(start2, LocalDateTime.now()).toMillis()}μs")
        println("Part 2 answer: $part2Answer")
    }

}

class Day11 {

    fun part1(input: List<String>): Int {
        var plan = SeatingPlan(input)
//        plan.print()
        while (true) {
            val newPlan = plan.nextRoundPart1Rules()
//            newPlan.print()
            if (newPlan.data == plan.data) {
                return newPlan.countAllOccupied()
            }
            plan = newPlan
        }
    }

    fun part2(input: List<String>): Int {
        var plan = SeatingPlan(input)
//        plan.print()
        while (true) {
            val newPlan = plan.nextRoundPart2Rules()
//            newPlan.print()
            if (newPlan.data == plan.data) {
                return newPlan.countAllOccupied()
            }
            plan = newPlan
        }
    }

    class SeatingPlan(
        val data: List<String>
    ) {
        val width = data.first().length
        val height = data.size

        val EmptySeat = 'L'
        val OccupiedSeat = '#'
        val Floor = '.'

        fun nextRoundPart1Rules(): SeatingPlan {
            val newPlan = mutableListOf<String>()
            for (y in 0 until width) {
                val newRow = StringBuilder()
                for (x in 0 until height) {
                    val newState = when (data[y][x]) {
                        EmptySeat -> {
                            if (countAdjacentOccupied(x, y) == 0) {
                                OccupiedSeat
                            } else {
                                EmptySeat
                            }
                        }
                        OccupiedSeat -> {
                            if (countAdjacentOccupied(x, y) >= 4) {
                                EmptySeat
                            } else {
                                OccupiedSeat
                            }
                        }
                        Floor -> {
                            Floor
                        }
                        else -> throw IllegalStateException("WAT")
                    }
                    newRow.append(newState)
                }
                newPlan.add(newRow.toString())
            }
            return SeatingPlan(newPlan)
        }

        fun nextRoundPart2Rules(): SeatingPlan {
            val newPlan = mutableListOf<String>()
            for (y in 0 until width) {
                val newRow = StringBuilder()
                for (x in 0 until height) {
                    val newState = when (data[y][x]) {
                        EmptySeat -> {
                            if (countVisibleOccupied(x, y) == 0) {
                                OccupiedSeat
                            } else {
                                EmptySeat
                            }
                        }
                        OccupiedSeat -> {
                            if (countVisibleOccupied(x, y) >= 5) {
                                EmptySeat
                            } else {
                                OccupiedSeat
                            }
                        }
                        Floor -> {
                            Floor
                        }
                        else -> throw IllegalStateException("WAT")
                    }
                    newRow.append(newState)
                }
                newPlan.add(newRow.toString())
            }
            return SeatingPlan(newPlan)
        }

        private fun countAdjacentOccupied(xPos: Int, yPos: Int): Int {
            var count = 0
            for (y in yPos - 1 .. yPos + 1) {
                for (x in xPos - 1 .. xPos + 1) {
                    if (x >= 0 && y >= 0 && x < width && y < height && !(x == xPos && y == yPos)) {
                        if (data[y][x] == OccupiedSeat) {
                            count += 1
                        }
                    }
                }
            }
            return count
        }

        private fun countVisibleOccupied(xPos: Int, yPos: Int): Int {
            var count = 0

            if (xPos < width - 1) {
                for (x in xPos + 1 until width) {
                    if (data[yPos][x] == OccupiedSeat) {
                        count += 1
                        break
                    } else if (data[yPos][x] == EmptySeat) {
                        break
                    }
                }
            }
            if (xPos > 0) {
                for (x in xPos - 1 downTo 0) {
                    if (data[yPos][x] == OccupiedSeat) {
                        count += 1
                        break
                    } else if (data[yPos][x] == EmptySeat) {
                        break
                    }
                }
            }

            if (yPos < height - 1) {
                for (y in yPos + 1 until height) {
                    if (data[y][xPos] == OccupiedSeat) {
                        count += 1
                        break
                    } else if (data[y][xPos] == EmptySeat) {
                        break
                    }
                }
            }

            if (yPos > 0) {
                for (y in yPos - 1 downTo 0) {
                    if (data[y][xPos] == OccupiedSeat) {
                        count += 1
                        break
                    } else if (data[y][xPos] == EmptySeat) {
                        break
                    }
                }
            }

            run {
                var x = xPos - 1
                var y = yPos - 1
                while (x >= 0 && y >= 0 && x < width && y < height) {
                    if (data[y][x] == OccupiedSeat) {
                        count += 1
                        break
                    } else if (data[y][x] == EmptySeat) {
                        break
                    }
                    x -= 1
                    y -= 1
                }
            }

            run {
                var x = xPos + 1
                var y = yPos + 1
                while (x >= 0 && y >= 0 && x < width && y < height) {
                    if (data[y][x] == OccupiedSeat) {
                        count += 1
                        break
                    } else if (data[y][x] == EmptySeat) {
                        break
                    }
                    x += 1
                    y += 1
                }
            }

            run {
                var x = xPos - 1
                var y = yPos + 1
                while (x >= 0 && y >= 0 && x < width && y < height) {
                    if (data[y][x] == OccupiedSeat) {
                        count += 1
                        break
                    } else if (data[y][x] == EmptySeat) {
                        break
                    }
                    x -= 1
                    y += 1
                }
            }

            run {
                var x = xPos + 1
                var y = yPos - 1
                while (x >= 0 && y >= 0 && x < width && y < height) {
                    if (data[y][x] == OccupiedSeat) {
                        count += 1
                        break
                    } else if (data[y][x] == EmptySeat) {
                        break
                    }
                    x += 1
                    y -= 1
                }
            }

            return count
        }

        fun countAllOccupied(): Int {
            var count = 0
            for (y in 0 until height) {
                for (x in 0 until width) {
                    if (data[y][x] == OccupiedSeat) {
                        count += 1
                    }
                }
            }
            return count
        }

        fun print() {
            println()
            for (y in 0 until height) {
                println(data[y])
            }
            println()
        }
    }

}
