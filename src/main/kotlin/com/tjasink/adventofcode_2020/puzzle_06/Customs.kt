package com.tjasink.adventofcode_2020.puzzle_06

fun main() {
    val input = ClassLoader.getSystemResourceAsStream("puzzle_06/customs_cards.txt")
        .bufferedReader()
        .lineSequence()
        .toList()

    val sumForAnyone = Customs().collateAndCountAndAddGrouupAnswersForAnyonesAnswers(input)
    println("Total group answers where anyone answered: $sumForAnyone")

    val sumForEveryone = Customs().collateAndCountAndAddGrouupAnswersForEveryonesAnswers(input)
    println("Total group answers where everyone answered: $sumForEveryone")
}

class Customs {

    fun collateAndCountAndAddGrouupAnswersForAnyonesAnswers(data: List<String>): Long {
        return collateGrouupAnswers(data)
            .map { it.fold(mutableSetOf<Char>()) { acc, set -> acc.union(set).toMutableSet() } }
            .map { it.size }
            .fold(0L) { acc, i -> acc + i }
    }

    fun collateAndCountAndAddGrouupAnswersForEveryonesAnswers(data: List<String>): Long {
        return collateGrouupAnswers(data)
            .map { it.fold(it.first()) { acc, set -> acc.intersect(set).toMutableSet() } }
            .map { it.size }
            .fold(0L) { acc, i -> acc + i }
    }

    fun collateGrouupAnswers(data: List<String>): List<List<Set<Char>>> {
        val allAnswers = mutableListOf<List<Set<Char>>>()
        var groupAnswers = mutableListOf<Set<Char>>()
        (data + "").forEach { line ->
            if (line.isEmpty()) {
                allAnswers.add(groupAnswers)
                groupAnswers = mutableListOf<Set<Char>>()
            } else {
                val personAnswers = line.map { it }.toSet()
                groupAnswers.add(personAnswers)
            }
        }

        return allAnswers
    }

}
