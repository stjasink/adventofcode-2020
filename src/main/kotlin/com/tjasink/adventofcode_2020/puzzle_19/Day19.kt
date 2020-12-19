package com.tjasink.adventofcode_2020.puzzle_19

import com.tjasink.adventofcode_2020.common.Solver
import com.tjasink.adventofcode_2020.common.loadInput
import com.tjasink.adventofcode_2020.common.runAndTime
import java.lang.RuntimeException

fun main() {
    val input = loadInput("puzzle_19/data.txt")
    val solver = Day19()
    runAndTime(solver, input)
}

class Day19 : Solver {

    override fun part1(input: List<String>): Long {
        val (rules, data) = splitIntoRulesAndData(input)

        val numberRegex = Regex("^[\\d]+$")
        val letterRegex = Regex("^\"[ab]\"$")
        val operators = setOf("|", "(", ")")

        var currentRule = rules["0"]!!
        while (currentRule.contains(Regex("[\\d]"))) {
            val newRule = StringBuilder()
            val ruleTerms = currentRule.split(' ').filterNot { it.isBlank() }
            ruleTerms.forEach { term ->
                when {
                    numberRegex.matches(term) -> newRule.append("( ${rules[term]} )")
                    letterRegex.matches(term) -> newRule.append(term)
                    operators.contains(term) -> newRule.append(" $term ")
                    else -> throw IllegalStateException("Found unexpected term \"$term\"")
                }
                newRule.append(' ')
            }
            currentRule = newRule.toString()
//            println(currentRule)
        }
        currentRule = currentRule
            .replace("\"", "")
            .replace(" ", "")
            .let { "^$it\$" }
        
        println(currentRule)

        val superRegex = Regex(currentRule)
        val matchingData = data.filter { superRegex.matches(it) }

        return matchingData.size.toLong()
    }

    override fun part2(input: List<String>): Long {
        return 0
    }

    fun splitIntoRulesAndData(lines: List<String>): Pair<Map<String, String>, List<String>> {
        val rules = mutableMapOf<String, String>()
        val data = mutableListOf<String>()
        val ruleRegex = Regex("^([\\d]+): (.+)$")
        lines.forEach { line ->
            val ruleMatch = ruleRegex.find(line)
            if (ruleMatch != null) {
                val (ruleNum, ruleCode) = ruleMatch.destructured
                rules[ruleNum] = ruleCode
            } else if (line.isNotBlank()) {
                data.add(line)
            }
        }

        return Pair(rules, data)
    }

}
