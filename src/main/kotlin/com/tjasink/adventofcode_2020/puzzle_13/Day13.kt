package com.tjasink.adventofcode_2020.puzzle_13

import java.time.LocalDateTime
import java.time.Duration

fun main() {
    val input = ClassLoader.getSystemResourceAsStream("puzzle_13/data.txt")
        .bufferedReader()
        .lineSequence()
        .toList()

    for (i in 1..10) {
        Day13().part1(input.map { it })
        Day13().part2(input.map { it })
    }


    for (i in 1..10) {
        val start1 = LocalDateTime.now()
        val part1Answer = Day13().part1(input.map { it })
        println("Part 1 time: ${Duration.between(start1, LocalDateTime.now())}")
        println("Part 1 answer: $part1Answer")

        val start2 = LocalDateTime.now()
        val part2Answer = Day13().part2(input.map { it })
        println("Part 2 time: ${Duration.between(start2, LocalDateTime.now())}")
        println("Part 2 answer: $part2Answer")
    }

}

class Day13 {

    fun part1(input: List<String>): Int {

        return 0
    }


    fun part2(input: List<String>): Int {

        return 0
    }

}
