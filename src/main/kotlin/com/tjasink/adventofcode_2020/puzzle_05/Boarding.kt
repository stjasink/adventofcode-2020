package com.tjasink.adventofcode_2020.puzzle_05

fun main() {
    val input = ClassLoader.getSystemResourceAsStream("puzzle_05/boarding_cards.txt")
        .bufferedReader()
        .lineSequence()
        .toList()

    val highestId = Boarding().highestSeatNumber(input)
    println("Highest seat number: $highestId")

    val emptyId = Boarding().emptySeatNumber(input)
    println("Empty seat number: $emptyId")
}

class Boarding {

    fun emptySeatNumber(data: List<String>): Int {
        val sortedSeats = data.map { seatId(it) }.sorted()
        sortedSeats.forEachIndexed { index, id ->
            if (index + 89 != id) {
                return index + 89
            }
        }
        return 0
    }

    fun highestSeatNumber(data: List<String>): Int {
        return data.map { seatId(it) }.maxOrNull()!!
    }

    fun seatId(data: String) = data
        .replace('F', '0')
        .replace('B', '1')
        .replace('L', '0')
        .replace('R', '1')
        .toInt(2)

}
