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
        val sortedSeats = data.map { seatNumber(it) }.map { it.id }.sorted()
        sortedSeats.forEachIndexed { index, id ->
            if (index + 89 != id) {
                return index + 89
            }
        }
        return 0
    }

    fun highestSeatNumber(data: List<String>): Int {
        return data.map { seatNumber(it) }.maxByOrNull { it.id }!!.id
    }

    fun seatNumber(data: String): BoardingPass {
        val rowData = data.substring(0, 7)
        val columnData = data.substring(7)

        val row = rowData.replace('F', '0').replace('B', '1').toInt(2)
        val column = columnData.replace('L', '0').replace('R', '1').toInt(2)

        return BoardingPass(row, column)
    }

}

data class BoardingPass(
    val row: Int,
    val column: Int,
    val id: Int = row * 8 + column
)
