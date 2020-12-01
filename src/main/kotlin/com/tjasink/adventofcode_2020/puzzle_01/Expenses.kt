package com.tjasink.adventofcode_2020.puzzle_01

fun main() {
    val input = ClassLoader.getSystemResourceAsStream("puzzle_01/expenses.txt")
            .bufferedReader()
            .lineSequence()
            .map { it.toInt() }
            .toList()

    val outputTwo = Expenses().calculateForTwo(input)
    val outputThree = Expenses().calculateForThree(input)

    println("For two numbers: $outputTwo")
    println("For three numbers: $outputThree")
}

class Expenses {

    fun calculateForTwo(costs: List<Int>): Int {
        for (x in costs) {
            for (y in costs) {
                if (x + y == 2020) {
                    return x * y
                }
            }
        }
        return 0
    }

    fun calculateForThree(costs: List<Int>): Int {
        for (x in costs) {
            for (y in costs) {
                for (z in costs) {
                    if (x + y + z == 2020) {
                        return x * y * z
                    }
                }
            }
        }
        return 0
    }
}
