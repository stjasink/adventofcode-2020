package com.tjasink.adventofcode_2020.puzzle_16

import com.tjasink.adventofcode_2020.common.Solver
import com.tjasink.adventofcode_2020.common.loadInput
import com.tjasink.adventofcode_2020.common.runAndTime

fun main() {
    val input = loadInput("puzzle_16/data.txt")
    val solver = Day16()
    runAndTime(solver, input)
}

class Day16 : Solver {

    override fun part1(input: List<String>): Long {
        val (rules, myTicket, otherTickets) = parseData(input)

        val allValidRanges = rules.flatMap { it.validRanges }
        val invalidValues = otherTickets.flatMap { valueList ->
            valueList.filterNot { value ->
                allValidRanges.map { range -> range.contains(value) }.contains(true)
            }
        }

        return invalidValues.sum().toLong()
    }

    override fun part2(input: List<String>): Long {
        val (fields, myTicket, otherTickets) = parseData(input)
        val allValidRanges = fields.flatMap { it.validRanges }

        val validTickets = otherTickets.filter { valueList ->
            valueList.filterNot { value ->
                allValidRanges.map { range -> range.contains(value) }.contains(true)
            }.isEmpty()
        }

        val validTicketValues = mutableListOf<List<Int>>()
        for (fieldNum in validTickets.first().indices) {
            validTicketValues.add(validTickets.map { it[fieldNum] })
        }

        val fieldsInOrder = Array<Field?>(fields.size) { null }.toMutableList()
        val fieldsToMatch = fields.toMutableList()
        while (fieldsToMatch.isNotEmpty()) {
            for (ticketFieldNum in myTicket.indices) {
                val ticketFieldValues = validTicketValues[ticketFieldNum]
                val matchingFields = fieldsToMatch.filter { field ->
                    !fieldsInOrder.contains(field) && field.validRanges.containsAll(ticketFieldValues)
                }
                if (matchingFields.size == 1) {
                    fieldsInOrder[ticketFieldNum] = matchingFields.first()
                    fieldsToMatch.remove(matchingFields.first())
                }
            }
        }

        val myDepartureFields = myTicket.filterIndexed { index, _ ->
            fieldsInOrder[index]!!.name.startsWith("departure")
        }.map { it.toLong() }

        return myDepartureFields.reduce { acc, i -> acc * i }
    }

    private fun parseData(input: List<String>): Triple<List<Field>, List<Int>, List<List<Int>>> {
        var doingRules = true
        var doingMyTicket = false
        var doingOtherTickets = false

        val rules = mutableListOf<Field>()
        val myTicket = mutableListOf<List<Int>>()
        val otherTickets = mutableListOf<List<Int>>()

        input.forEach { line ->
            when {
                line.isBlank() -> {
                    doingRules = false
                    doingMyTicket = false
                    doingOtherTickets = false
                }
                line == "your ticket:" -> {
                    doingMyTicket = true
                }
                line == "nearby tickets:" -> {
                    doingOtherTickets = true
                }
                doingRules -> {
                    rules.add(parseRuleLine(line))
                }
                doingMyTicket -> {
                    myTicket.add(parseTicketLine(line))
                }
                doingOtherTickets -> {
                    otherTickets.add(parseTicketLine(line))
                }
            }
        }

        return Triple(rules, myTicket.first(), otherTickets)
    }

    private val ruleRegex = Regex("([a-z ]+): ([\\d]+)-([\\d]+) or ([\\d]+)-([\\d]+)")
    private fun parseRuleLine(line: String): Field {
        val found = ruleRegex.find(line)!!
        val (name, start1, end1, start2, end2) = found.destructured
        return Field(name, listOf(IntRange(start1.toInt(), end1.toInt()), IntRange(start2.toInt(), end2.toInt())))
    }

    private fun parseTicketLine(line: String): List<Int> {
        return line.split(',').map { it.toInt() }
    }

    data class Field(
        val name: String,
        val validRanges: List<IntRange>
    )

    fun List<IntRange>.contains(num: Int): Boolean {
        return this.any { it.contains(num) }
    }

    fun List<IntRange>.containsAll(nums: Collection<Int>): Boolean {
        return nums.find { !this.contains(it) } == null
    }

}
