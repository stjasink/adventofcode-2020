package com.tjasink.adventofcode_2020.puzzle_07

fun main() {
    val input = ClassLoader.getSystemResourceAsStream("puzzle_07/bag_rules.txt")
        .bufferedReader()
        .lineSequence()
        .toList()

    val numThatCanContainShinyGold = Bags().countBagsThatCanContain("shiny gold", input)
    println("Bags that can contain shiny gold: $numThatCanContainShinyGold")

    val numThatNeedToBeInShinyGold = Bags().countBagsThatNeedToBeInside("shiny gold", input)
    println("Bags that need to be in shiny gold: $numThatNeedToBeInShinyGold")
}

class Bags {

    fun countBagsThatNeedToBeInside(colour: String, withRules: List<String>): Int {
        val rules = parseRules(withRules)
        val startRule = rules.find { it.bagColour == colour }!!
        return countNumContainedIn(startRule)
    }

    private fun countNumContainedIn(rule: BagRule): Int {
        return rule.contains.map { it.first * (1 + countNumContainedIn(it.second)) }.sum()
    }

    fun countBagsThatCanContain(colour: String, withRules: List<String>): Int {
        val rules = parseRules(withRules)

        val startRule = rules.find { it.bagColour == colour }!!
        val allContainingRules = allContainersOf(startRule)

        return allContainingRules.size
    }

    private fun allContainersOf(rule: BagRule): Set<BagRule> {
        return rule.containedIn + rule.containedIn.flatMap { allContainersOf(it) }
    }

    private fun parseRules(withRules: List<String>): Set<BagRule> {
        val rules = mutableSetOf<BagRule>()

        withRules.forEach { ruleString ->
            val containerColour = ruleString.split(" bags contain")[0]
            val containedColours = ruleString.split(" bags contain")[1]
                .split(',')
                .map { it.trimEnd('.') }
                .map { it.replace(" bags", "").replace(" bag", "") }
                .filterNot { it == " no other" }
                .map {
                    it.trim().split(' ', limit = 2).let {
                        Pair(it[0].toInt(), it[1])
                    }
                }

            val containerRule = findOrCreateRule(rules, containerColour)
            val containedRules = containedColours.map { Pair(it.first, findOrCreateRule(rules, it.second)) }

            containerRule.contains.addAll(containedRules)
            containedRules.map { it.second }.forEach { it.containedIn.add(containerRule) }
        }

//        rules.forEach { rule ->
//            println(rule.bagColour)
//            println("Contained in: ${rule.containedIn.map { it.bagColour }}")
//            println("Contains: ${rule.contains.map { it.bagColour }}")
//            println()
//        }

        return rules
    }

    private fun findOrCreateRule(
        rules: MutableSet<BagRule>,
        containerColour: String
    ) = rules.find { it.bagColour == containerColour } ?: BagRule(containerColour).apply { rules.add(this) }


    class BagRule(
        val bagColour: String,
        val contains: MutableSet<Pair<Int, BagRule>> = mutableSetOf(),
        val containedIn: MutableSet<BagRule> = mutableSetOf()
    )

}
