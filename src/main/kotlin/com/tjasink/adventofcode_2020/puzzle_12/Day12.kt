package com.tjasink.adventofcode_2020.puzzle_12

import com.tjasink.adventofcode_2020.common.loadInput
import com.tjasink.adventofcode_2020.common.timeCode
import kotlin.math.absoluteValue

fun main() {
    val input = loadInput("puzzle_12/data.txt")
    val solver = Day12()

    val part1Answer = solver.part1(input)
    println("Part 1 answer: $part1Answer")
    val part1Time = timeCode { solver.part1(input) }
    println("Part 1 time: $part1Time / ${part1Time.toMillis()}ms / ${part1Time.toNanos() / 1000}μs")

    val part2Answer = solver.part2(input)
    println("Part 2 answer: $part2Answer")
    val part2Time = timeCode { solver.part2(input) }
    println("Part 2 time: $part2Time / ${part2Time.toMillis()}ms / ${part2Time.toNanos() / 1000}μs")
}

class Day12 {

    fun part1(input: List<String>): Int {
        var x = 0
        var y = 0
        var heading = 90

        input.map { decodeLine(it) }
            .forEach { (letter, num) ->
                when (letter) {
                    'N' -> y += num
                    'S' -> y -= num
                    'E' -> x += num
                    'W' -> x -= num
                    'F' -> when (heading) {
                        0 -> y += num
                        180 -> y -= num
                        90 -> x += num
                        270 -> x -= num
                        else -> throw IllegalStateException("WAT $letter$num with $heading")
                    }
                    'L' -> heading = (heading - num).let { if (it < 0) it + 360 else it }
                    'R' -> heading = (heading + num).let { if (it >= 360) it - 360 else it }
                    else -> throw IllegalStateException("WAT $$letter$num")
                }
            }

        return x.absoluteValue + y.absoluteValue
    }

    fun part2(input: List<String>): Int {
        var x = 0
        var y = 0
        var wpX = 10
        var wpY = 1

        input.map { decodeLine(it) }
            .forEach { (letter, num) ->
                when (letter) {
                    'N' -> wpY += num
                    'S' -> wpY -= num
                    'E' -> wpX += num
                    'W' -> wpX -= num
                    'F' -> {
                        x += wpX * num
                        y += wpY * num
                    }
                    'L' -> rotateLeft(wpX, wpY, num).apply { wpX = this.first; wpY = this.second }
                    'R' -> rotateRight(wpX, wpY, num).apply { wpX = this.first; wpY = this.second }
                    else -> throw IllegalStateException("WAT $letter$num")
                }
            }

        return x.absoluteValue + y.absoluteValue
    }

    private fun rotateRight(x: Int, y: Int, degrees: Int): Pair<Int, Int> {
        return when (degrees) {
            180 -> Pair(-x, -y)
            90 -> Pair(y, -x)
            270 -> Pair(-y, x)
            else -> throw IllegalStateException("WAT $degrees")
        }
    }

    private fun rotateLeft(x: Int, y: Int, degrees: Int): Pair<Int, Int> {
        return when (degrees) {
            180 -> Pair(-x, -y)
            270 -> Pair(y, -x)
            90 -> Pair(-y, x)
            else -> throw IllegalStateException("WAT $degrees")
        }
    }

    private fun decodeLine(line: String): Pair<Char, Int> {
        val letter = line[0]
        val num = line.substring(1).toInt()
        return Pair(letter, num)
    }

}
