package com.tjasink.adventofcode_2020.puzzle_12

import java.time.LocalDateTime
import java.time.Duration
import kotlin.math.absoluteValue

fun main() {
    val input = ClassLoader.getSystemResourceAsStream("puzzle_12/data.txt")
        .bufferedReader()
        .lineSequence()
        .toList()

    for (i in 1..10) {
        Day12().part1(input.map { it })
        Day12().part2(input.map { it })
    }

    for (i in 1..10) {
        val start1 = LocalDateTime.now()
        val part1Answer = Day12().part1(input.map { it })
        println("Part 1 time: ${Duration.between(start1, LocalDateTime.now()).toMillis()}ms")
        println("Part 1 answer: $part1Answer")

        val start2 = LocalDateTime.now()
        val part2Answer = Day12().part2(input.map { it })
        println("Part 2 time: ${Duration.between(start2, LocalDateTime.now()).toMillis()}ms")
        println("Part 2 answer: $part2Answer")
    }

}

class Day12 {

    fun part1(input: List<String>): Int {
        var x = 0
        var y = 0
        var heading = 90

        input.forEach { line ->
            val (letter, num) = decodeline(line)
            when (letter) {
                'N' -> {
                    y += num
                }
                'S' -> {
                    y -= num
                }
                'E' -> {
                    x += num
                }
                'W' -> {
                    x -= num
                }
                'F' -> {
                    when (heading) {
                        0 -> {
                            y += num
                        }
                        180 -> {
                            y -= num
                        }
                        90 -> {
                            x += num
                        }
                        270 -> {
                            x -= num
                        }
                        else -> throw IllegalStateException("WAT $line with $heading")
                    }
                }
                'L' -> {
                    heading = (heading - num)
                    if (heading < 0) heading += 360
                }
                'R' -> {
                    heading = (heading + num) % 360
                    if (heading > 360) heading -= 360
                }
                else -> throw IllegalStateException("WAT $line")
            }

        }

        return x.absoluteValue + y.absoluteValue

    }


    fun part2(input: List<String>): Int {
        var x = 0
        var y = 0

        var wpX = 10
        var wpY = 1

        input.forEach { line ->
            val (letter, num) = decodeline(line)
            when (letter) {
                'N' -> {
                    wpY += num
                }
                'S' -> {
                    wpY -= num
                }
                'E' -> {
                    wpX += num
                }
                'W' -> {
                    wpX -= num
                }
                'F' -> {
                    x += wpX * num
                    y += wpY * num
                }
                'L' -> {
                    when (num) {
                        180 -> {
                            wpX = -wpX
                            wpY = -wpY
                        }
                        270 -> {
                            val temp = wpY
                            wpY = -wpX
                            wpX = temp
                        }
                        90 -> {
                            val temp = wpY
                            wpY = wpX
                            wpX = -temp
                        }
                        else -> throw IllegalStateException("WAT $line")
                    }
                }
                'R' -> {
                    when (num) {
                        180 -> {
                            wpX = -wpX
                            wpY = -wpY
                        }
                        90 -> {
                            val temp = wpY
                            wpY = -wpX
                            wpX = temp
                        }
                        270 -> {
                            val temp = wpY
                            wpY = wpX
                            wpX = -temp
                        }
                        else -> throw IllegalStateException("WAT $line")
                    }
                }
                else -> throw IllegalStateException("WAT $line")
            }

        }

        return x.absoluteValue + y.absoluteValue
    }



    val regex = Regex("([A-Z])([0-9]+)")
    private fun decodeline(line: String): Pair<Char, Int> {
        val match = regex.find(line)
        val groups = match!!.destructured.toList()
        val letter = groups[0].first()
        val num = groups[1].toInt()
        return Pair(letter, num)
    }

}
