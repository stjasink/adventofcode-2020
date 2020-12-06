package com.tjasink.adventofcode_2020.puzzle_06

fun main() {
    val input = ClassLoader.getSystemResourceAsStream("puzzle_06/customs_cards.txt")
        .bufferedReader()
        .lineSequence()
        .toList()

    val sumForAnyone = Customs().collateAndCountAndAddGroupAnswersForAnyonesAnswers(input)
    println("Total group answers where anyone answered: $sumForAnyone")

    val sumForEveryone = Customs().collateAndCountAndAddGroupAnswersForEveryonesAnswers(input)
    println("Total group answers where everyone answered: $sumForEveryone")
}

class Customs {

    fun collateAndCountAndAddGroupAnswersForAnyonesAnswers(data: List<String>) =
        collateAndProcess(data, Set<Char>::union)

    fun collateAndCountAndAddGroupAnswersForEveryonesAnswers(data: List<String>) =
        collateAndProcess(data, Set<Char>::intersect)

    fun collateAndProcess(data: List<String>, operation: Set<Char>.(Set<Char>) -> Set<Char>): Long {
        return collateGrouupAnswers(data)
            .map { it.reduce { acc, set -> acc.operation(set) } }
            .map { it.size.toLong() }
            .reduce { acc, i -> acc + i }
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
