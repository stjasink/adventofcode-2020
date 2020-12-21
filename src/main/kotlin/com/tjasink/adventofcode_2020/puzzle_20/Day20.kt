package com.tjasink.adventofcode_2020.puzzle_20

import com.tjasink.adventofcode_2020.common.Solver
import com.tjasink.adventofcode_2020.common.loadInput
import com.tjasink.adventofcode_2020.common.runAndTime
import kotlin.math.sqrt

fun main() {
    val input = loadInput("puzzle_20/data.txt")
    val solver = Day20()
    runAndTime(solver, input)
}

class Day20 : Solver {

    override fun part1(input: List<String>): Long {
        val tiles = splitIntoTiles(input)
        val grid = putTilesIntoCorrectPositions(tiles)
        return grid.first().first()!!.number.toLong() *
                grid.first().last()!!.number.toLong() *
                grid.last().first()!!.number.toLong() *
                grid.last().last()!!.number.toLong()
    }

    override fun part2(input: List<String>): Long {
        val tiles = splitIntoTiles(input)

        val strangeGrid = putTilesIntoCorrectPositions(tiles)

        println()
        println("${strangeGrid[0][0]!!.number} ${strangeGrid[0][1]!!.number} ${strangeGrid[0][2]!!.number}")
        println("${strangeGrid[1][0]!!.number} ${strangeGrid[1][1]!!.number} ${strangeGrid[1][2]!!.number}")
        println("${strangeGrid[2][0]!!.number} ${strangeGrid[2][1]!!.number} ${strangeGrid[2][2]!!.number}")
        println()
        
        val grid = Array<Array<Tile?>>(strangeGrid.size) { Array(strangeGrid.first().size) { null } }
        for (y in grid.indices) {
            for (x in grid[y].indices) {
                grid[x][y] = strangeGrid[y][x]
            }
        }

        println()
        println("${grid[0][0]!!.number} ${grid[0][1]!!.number} ${grid[0][2]!!.number}")
        println("${grid[1][0]!!.number} ${grid[1][1]!!.number} ${grid[1][2]!!.number}")
        println("${grid[2][0]!!.number} ${grid[2][1]!!.number} ${grid[2][2]!!.number}")
        println()

        for (row in grid) {
            for (tile in row) {
                tile!!.print()
            }
        }

        for (row in grid) {
            for (x in row.indices) {
                row[x] = row[x]?.removeEdges()
            }
        }

        for (row in grid) {
            for (tile in row) {
                tile!!.print()
            }
        }

        val image = combineTiles(grid)

        image.print()
        
        val allImageVersions = listOf(image, image.flipX(), image.flipY(), image.flipX().flipY()) +
                listOf(image.rotateRight(1), image.rotateRight(1).flipX(), image.rotateRight(1).flipY(), image.rotateRight(1).flipX().flipY()) +
                listOf(image.rotateRight(2), image.rotateRight(2).flipX(), image.rotateRight(2).flipY(), image.rotateRight(2).flipX().flipY()) +
                listOf(image.rotateRight(3), image.rotateRight(3).flipX(), image.rotateRight(3).flipY(), image.rotateRight(3).flipX().flipY())

        val imageWithMonsters = allImageVersions.find { it.lookForSeaMonsters() > 0 }!!

        imageWithMonsters.print()

        println("Num monsters: ${imageWithMonsters.lookForSeaMonsters()}")

        return (imageWithMonsters.countAllHashes() - (imageWithMonsters.lookForSeaMonsters() * 15)).toLong()
    }

    private fun combineTiles(grid: Array<Array<Tile?>>): Tile {
        val newRows = mutableListOf<String>()
        val tileSize = grid[0][0]!!.data.size
        for (row in grid) {
            for (y in 0 until tileSize) {
                val newRow = StringBuilder()
                for (tile in row) {
                    for (x in 0 until tileSize) {
                        newRow.append(tile!!.data[y][x])
                    }
                }
                newRows.add(newRow.toString())
            }
        }
        return Tile(0, newRows, 0, false, false)
    }

    private fun putTilesIntoCorrectPositions(tiles: List<Tile>): Array<Array<Tile?>> {
        val gridEdgeSize = sqrt(tiles.size.toDouble()).toInt()
        val grid = Array<Array<Tile?>>(gridEdgeSize) { Array(gridEdgeSize) { null } }

        val tileEdgesWithCounts = tileEdgesWithCounts(tiles)
        val gridEdges = tileEdgesWithCounts.filter { it.value == 1 }.map { it.key }

        println("counts: $tileEdgesWithCounts")
        println("Grid edges: $gridEdges")

        val tilesLeftToUse = tiles.flatMap { tile ->
                    listOf(tile, tile.flipX(), tile.flipY(), tile.flipX().flipY()) +
                    listOf(tile.rotateRight(1), tile.rotateRight(1).flipX(), tile.rotateRight(1).flipY(), tile.rotateRight(1).flipX().flipY()) +
                    listOf(tile.rotateRight(2), tile.rotateRight(2).flipX(), tile.rotateRight(2).flipY(), tile.rotateRight(2).flipX().flipY()) +
                    listOf(tile.rotateRight(3), tile.rotateRight(3).flipX(), tile.rotateRight(3).flipY(), tile.rotateRight(3).flipX().flipY())
        }.toMutableList()

        for (y in 0 until gridEdgeSize) {
            for (x in 0 until gridEdgeSize) {
                val gridEdgeDirectionsNeeded = findGridEdgesNeededFor(x, y, gridEdgeSize)
                val neighbourEdgesNeeded = findNeighbourEdges(grid, x, y)

                val tileThatFits = tilesLeftToUse.find { tile ->

                    val hasCorrectGridEdges = !gridEdgeDirectionsNeeded.map { direction ->
                        gridEdges.contains(tile.edge(direction))
                    }.contains(false)

                    val hasCorrectNeighbourEdges = !neighbourEdgesNeeded.map {
                        tile.edge(it.key) == it.value
                    }.contains(false)

                    hasCorrectGridEdges && hasCorrectNeighbourEdges
                } ?: throw IllegalStateException("No tile found to fit at ($x, $y)")

                tilesLeftToUse.removeIf { it.number == tileThatFits.number }

                grid[x][y] = tileThatFits
            }
        }

        return grid
    }

    private fun findNeighbourEdges(grid: Array<Array<Tile?>>, x: Int, y: Int): Map<EdgeDirection, String> {
        val gridEdgeSize = grid.size
        val edgesNeeded = mutableMapOf<EdgeDirection, String>()
        if (x > 0 && grid[x - 1][y] != null) edgesNeeded.put(
            EdgeDirection.Left,
            grid[x - 1][y]!!.edge(EdgeDirection.Right)
        )
        if (y > 0 && grid[x][y - 1] != null) edgesNeeded.put(
            EdgeDirection.Top,
            grid[x][y - 1]!!.edge(EdgeDirection.Bottom)
        )
        if (x < gridEdgeSize - 1 && grid[x + 1][y] != null) edgesNeeded.put(
            EdgeDirection.Right,
            grid[x + 1][y]!!.edge(EdgeDirection.Left)
        )
        if (y < gridEdgeSize - 1 && grid[x][y + 1] != null) edgesNeeded.put(
            EdgeDirection.Bottom,
            grid[x][y + 1]!!.edge(EdgeDirection.Top)
        )
        return edgesNeeded
    }

    private fun findGridEdgesNeededFor(x: Int, y: Int, edgeSize: Int): Set<EdgeDirection> {
        val edgesNeeded = mutableSetOf<EdgeDirection>()
        if (x == 0) edgesNeeded.add(EdgeDirection.Left)
        if (x == edgeSize - 1) edgesNeeded.add(EdgeDirection.Right)
        if (y == 0) edgesNeeded.add(EdgeDirection.Top)
        if (y == edgeSize - 1) edgesNeeded.add(EdgeDirection.Bottom)
        return edgesNeeded
    }

    private fun tileEdgesWithCounts(allTiles: List<Tile>): Map<String, Int> {
        val allEdges = allTiles.flatMap { tile ->
            tile.edgesForAllFlips()
        }
        return allEdges.groupingBy { it }.eachCount()
    }

    fun splitIntoTiles(input: List<String>): List<Tile> {
        val inputWithNoBlanks = input.filterNot { it.isBlank() }
        return inputWithNoBlanks.chunked(11).map { Tile.fromLines(it) }
    }

    fun removeEdgesAndCombine(tiles: List<Tile>): Tile {
        return tiles.first()
    }

    data class Tile(
        val number: Int,
        val data: List<String>,
        val rotations: Int,
        val flippedX: Boolean,
        val flippedY: Boolean
    ) {
        companion object {
            fun fromLines(lines: List<String>): Tile {
                val number = lines.first().replace("Tile ", "").replace(":", "").toInt()
                return Tile(number, lines.drop(1), 0, false, false)
            }
        }

        fun print() {
            println("Tile $number:")
            data.forEach { println(it) }
        }

        fun edge(edgeDirection: EdgeDirection): String {
            return when (edgeDirection) {
                EdgeDirection.Top -> data.first()
                EdgeDirection.Right -> data.map { it.last() }.joinToString("")
                EdgeDirection.Bottom -> data.last()
                EdgeDirection.Left -> data.map { it.first() }.joinToString("")
            }
        }

        fun edges(): List<String> {
            return EdgeDirection.values().map { edge(it) }
        }

        fun edgesForAllFlips(): Set<String> {
            return (edges() + flipX().edges() + flipY().edges()).toSet()
        }

        fun rotateRight(): Tile {
            val newData = mutableListOf<String>()
            for (i in data.first().indices) {
                newData.add(data.map { it[i] }.joinToString("").reversed())
            }
            return Tile(number, newData, (rotations + 1) % 4, flippedX, flippedY)
        }

        fun rotateRight(numRotations: Int): Tile {
            var rotatedTile = this
            for (i in 0 until numRotations) {
                rotatedTile = rotatedTile.rotateRight()
            }
            return rotatedTile
        }

        fun flipX(): Tile {
            return Tile(number, data.map { it.reversed() }, rotations, !flippedX, flippedY)
        }

        fun flipY(): Tile {
            return Tile(number, data.reversed(), rotations, flippedX, !flippedY)
        }

        fun removeEdges(): Tile {
            val newData = data.drop(1).dropLast(1).map { it.drop(1).dropLast(1) }
            return Tile(number, newData, rotations, flippedX, flippedY)
        }

        fun lookForSeaMonsters(): Int {
            val monsterLooksLike = "                  # \n#    ##    ##    ###\n #  #  #  #  #  #   "

            val lineGapSize = data.first().length - 20
            val monsterRegex = monsterLooksLike
                .replace(' ', '.')
//                .replace("#", "(#)")
                .split('\n')
                .joinToString(".{$lineGapSize}")
                .let { Regex(it) }

            val imageInOneString = data.joinToString("")

            println("regex $monsterRegex")
            println("One string: $imageInOneString")

            val matches = monsterRegex.findAll(imageInOneString).toList()
            println("Matches: ${matches.size}")
            return matches.size
        }
        
        fun countAllHashes(): Int {
            return data.map { it.count { it == '#' } }.sum()
        }

    }

    enum class EdgeDirection {
        Top,
        Right,
        Bottom,
        Left
    }
}
