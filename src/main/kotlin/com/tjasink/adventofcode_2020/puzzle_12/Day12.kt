package com.tjasink.adventofcode_2020.puzzle_12

import com.tjasink.adventofcode_2020.common.Solver
import com.tjasink.adventofcode_2020.common.loadInput
import com.tjasink.adventofcode_2020.common.runAndTime
import kotlin.math.absoluteValue

fun main() {
    val input = loadInput("puzzle_12/data.txt")
    val solver = Day12()
    runAndTime(solver, input)
}

class Day12: Solver {

    override fun part1(input: List<String>): Long {
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

        return (x.absoluteValue + y.absoluteValue).toLong()
    }

    override fun part2(input: List<String>): Long {
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
                    'R' -> rotateRight(wpX, wpY, num).apply { wpX = this.first; wpY = this.second }
                    'L' -> rotateRight(wpX, wpY, 360 - num).apply { wpX = this.first; wpY = this.second }
                    else -> throw IllegalStateException("WAT $letter$num")
                }
            }

        return (x.absoluteValue + y.absoluteValue).toLong()
    }

    private fun rotateRight(x: Int, y: Int, degrees: Int): Pair<Int, Int> {
        return when (degrees) {
            180 -> Pair(-x, -y)
            90 -> Pair(y, -x)
            270 -> Pair(-y, x)
            else -> throw IllegalStateException("WAT $degrees")
        }
    }

    private fun decodeLine(line: String): Pair<Char, Int> {
        val letter = line[0]
        val num = line.substring(1).toInt()
        return Pair(letter, num)
    }

}
