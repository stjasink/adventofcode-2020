package com.tjasink.adventofcode_2020.puzzle_03

fun main() {
    val input = ClassLoader.getSystemResourceAsStream("puzzle_03/trees.txt")
            .bufferedReader()
            .lineSequence()
            .toList()

    val treeCounter = Trees()
    val numTreesSlope3 = treeCounter.countTrees(input, 3, 1)
    println("Number of trees slope of 3/1: $numTreesSlope3")

    val allSlopes = listOf(
            Pair(1, 1),
            Pair(3, 1),
            Pair(5, 1),
            Pair(7, 1),
            Pair(1, 2)
    )

    val product = treeCounter.countAndMultiplyTrees(input, allSlopes)
    println("Num trees multiplied: $product")
}

class Trees {

    fun countAndMultiplyTrees(mapStart: List<String>, allSlopes: List<Pair<Int, Int>>): Long {
        val numTreesAllSlopes = allSlopes.map { countTrees(mapStart, it.first, it.second).toLong() }
        val product = numTreesAllSlopes.reduce { acc, i ->  acc * i }
        println(product)
        return product
    }

    fun countTrees(mapStart: List<String>, slopeX: Int, slopeY: Int): Int {
        val map = extendMap(mapStart, slopeX, slopeY)

        var numTrees = 0
        for (turn in map.indices) {
            val y = turn * slopeY
            if (y >= map.size) {
                return numTrees
            }
            val x = turn * slopeX
//            println(map[y])
//            println("$turn: $y/$x")
            if (map[y][x] == '#') numTrees += 1
        }

        return numTrees
    }

    private fun extendMap(mapStart: List<String>, slopeX: Int, slopeY: Int): List<String> {
        val height = mapStart.size
        val width = mapStart[0].length
        val minTotalWidth = height * slopeX / slopeY
        val timesToRepeat = (minTotalWidth / width) + 1

        return mapStart.map {
            it.repeat(timesToRepeat)
        }
    }

}
