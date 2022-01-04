package com.tjasink.adventofcode_2020.puzzle_21

import com.tjasink.adventofcode_2020.common.Solver
import com.tjasink.adventofcode_2020.common.loadInput
import com.tjasink.adventofcode_2020.common.runAndTime

fun main() {
    val input = loadInput("puzzle_21/data.txt")
    val solver = Day21()
    runAndTime(solver, input)
}

class Day21 : Solver {

    override fun part1(input: List<String>): Long {
        val startAllergensToIngredients = mutableListOf<Pair<Set<String>, Set<String>>>()
        val allergenToIngredient = mutableMapOf<String, String>()

        input.forEach { line ->
            val (ingredients, allergens) = parseIngredientsAndAllergens(line)
            startAllergensToIngredients.add(Pair(allergens, ingredients))
        }

        var allergensToIngredients = mutableListOf<Pair<Set<String>, Set<String>>>()
        allergensToIngredients.addAll(startAllergensToIngredients)
        var newFound: Int
        do {
            newFound = 0
            // check for any that have been reduced to one-to-one
            allergensToIngredients.forEach { (allergens, ingredients) ->
                if (allergens.size == 1 && ingredients.size == 1) {
                    allergenToIngredient[allergens.first()] = ingredients.first()
                    newFound += 1
                }
            }

            // compare rows against each other
            allergensToIngredients.forEachIndexed { index1, (allergens1, ingredients1) ->
                allergensToIngredients.forEachIndexed { index2, (allergens2, ingredients2) ->
                    if (index2 > index1) {
                        val commonAllergens = allergens1.intersect(allergens2)
                        val commonIngredients = ingredients1.intersect(ingredients2)
                        if (commonAllergens.size == 1 && commonIngredients.size == 1) {
                            allergenToIngredient[commonAllergens.first()] = commonIngredients.first()
                            newFound += 1
                        }
                    }
                }
            }

            val newAllergensToIngredients = mutableListOf<Pair<Set<String>, Set<String>>>()
            allergensToIngredients.forEach { (allergens, ingredients) ->
                val unknownAllergens = allergens - allergenToIngredient.keys
                if (unknownAllergens.isNotEmpty()) {
                    newAllergensToIngredients.add(Pair(unknownAllergens, ingredients - allergenToIngredient.values))
                }
            }
            allergensToIngredients = newAllergensToIngredients

        } while (newFound > 0)

        val remainingIngredients = startAllergensToIngredients.map { it.second }.flatten() - allergenToIngredient.values
        return remainingIngredients.size.toLong()
    }

    override fun part2(input: List<String>): Long {
        return 0L
    }

    private fun parseIngredientsAndAllergens(line: String): Pair<Set<String>, Set<String>> {
        val lineRegex = Regex("^(.+) \\(contains (.+)\\)")
        val match = lineRegex.find(line)!!
        val (ingredients, allergens) = match.destructured
        return Pair(ingredients.split(' ').toSet(), allergens.split(',').map { it.trim() }.toSet())
    }

}
