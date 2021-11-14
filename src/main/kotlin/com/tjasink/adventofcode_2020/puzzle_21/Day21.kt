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

        val allIngredients = mutableSetOf<String>()
        val allAllergens = mutableSetOf<String>()
        val allergensToPotentialIngredients = mutableMapOf<String, MutableSet<String>>()
        val ingredientsToPotentialAllergens = mutableMapOf<String, MutableSet<String>>()

        input.forEach { line ->
            val (ingredients, allergens) = parseIngredientsAndAllergens(line)
            println("$ingredients $allergens")

            allIngredients.addAll(ingredients)
            allAllergens.addAll(allergens)

            ingredients.forEach { ingredient ->
                if (ingredientsToPotentialAllergens.containsKey(ingredient)) {
                    ingredientsToPotentialAllergens[ingredient] = ingredientsToPotentialAllergens[ingredient]!!.union(allergens).toMutableSet()
                } else {
                    ingredientsToPotentialAllergens[ingredient] = allergens.toMutableSet()
                }
            }

            allergens.forEach { allergen ->
                if (allergensToPotentialIngredients.containsKey(allergen)) {
                    allergensToPotentialIngredients[allergen] = allergensToPotentialIngredients[allergen]!!.union(ingredients).toMutableSet()
                } else {
                    allergensToPotentialIngredients[allergen] = ingredients.toMutableSet()
                }
            }

        }

        val allSingleAllergensToIngredients = mutableMapOf<String, String>()
        var allergensWithOneIngredient = allergensToPotentialIngredients.filterValues { it.size == 1 }
        while (allergensWithOneIngredient.isNotEmpty()) {
            allergensWithOneIngredient.forEach { aToI ->
                val allergen = aToI.value.first()
                allSingleAllergensToIngredients.put(aToI.key, allergen)
                allergensToPotentialIngredients.forEach { potential ->
                    if (potential.value.contains(allergen)) {
                        potential.value.remove(allergen)
                    }
                }
                val keysWithEmptyValues = allergensToPotentialIngredients.filterValues { it.isEmpty() }.keys
                keysWithEmptyValues.forEach {
                    allergensToPotentialIngredients.remove(it)
                }
            }

            allergensWithOneIngredient = allergensToPotentialIngredients.filterValues { it.size == 1 }
        }

//        val allIngredientsWithAllergens = allergensToIngredients.values.flatten().toSet()
//        println("All ingredients: $allIngredients")
        println("ingredientsToPotentialAllergens: $ingredientsToPotentialAllergens")
        println("allergensToPotentialIngredients: $allergensToPotentialIngredients")
        println("ingredientsToAllergens: $allSingleAllergensToIngredients")

        return (allIngredients - allSingleAllergensToIngredients.keys).size.toLong()

    }

    override fun part2(input: List<String>): Long {
        return 0L
    }

    fun parseIngredientsAndAllergens(line: String): Pair<Set<String>, Set<String>> {
        val lineRegex = Regex("^(.+) \\(contains (.+)\\)")
        val match = lineRegex.find(line)!!
        val (ingredients, allergens) = match.destructured
        return Pair(ingredients.split(' ').toSet(), allergens.split(',').map { it.trim() }.toSet())
    }

}
