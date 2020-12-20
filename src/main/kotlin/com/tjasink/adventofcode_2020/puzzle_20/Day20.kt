package com.tjasink.adventofcode_2020.puzzle_20

import com.tjasink.adventofcode_2020.common.Solver
import com.tjasink.adventofcode_2020.common.loadInput
import com.tjasink.adventofcode_2020.common.runAndTime

fun main() {
    val input = loadInput("puzzle_20/data.txt")
    val solver = Day20()
    runAndTime(solver, input)
}

class Day20 : Solver {

    override fun part1(input: List<String>): Long {
        val tiles = splitIntoTiles(input)
//        tiles.forEach {
//            it.print()
//            println(it.edgesForAllFlippages().toSet())
//            println()
//        }
        val tileInAllFlippages = tiles.flatMap { tile ->
            listOf(tile, tile.flipX(), tile.flipY())
        }
        val allEdges = tileInAllFlippages.flatMap { it.edges() }
        println("Number of edges: ${allEdges.size}")
        val differentEdges = allEdges.toSet()
        println("Number of different edges: ${differentEdges.size}")
        println("Different edges: $differentEdges")
        val allEdgesWithCounts = allEdges.groupingBy { it }.eachCount()
        println("All edges with counts: $allEdgesWithCounts")

        val edgesWithNoMatches = allEdgesWithCounts.filter { it.value == 1 }.map { it.key }
        println("Edges with no matches: $edgesWithNoMatches")

        val cornerTiles = tiles.filter { (it.edgesForAllFlippages() intersect edgesWithNoMatches).size == 2 }
        val cornerTileIds = cornerTiles.map { it.number.toLong() }
        println(cornerTileIds)
        return cornerTileIds.reduce{ acc, l -> acc * l }
    }

    override fun part2(input: List<String>): Long {
        return 0L
    }

    fun splitIntoTiles(input: List<String>): List<Tile> {
        val inputWithNoBlanks = input.filterNot { it.isBlank() }
        return inputWithNoBlanks.chunked(11).map { Tile.fromLines(it) }
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

//            fun edgeIdFromLine(line: String): Int {
//                return line.replace('#', '1').replace('.', '0').toInt(2)
//            }
        }

        fun print() {
            println("Tile $number:")
            data.forEach { println(it) }
        }

        fun edge(edge: TileEdge): String {
            return when (edge) {
                TileEdge.Top -> data.first()
                TileEdge.Right -> data.map { it.last() }.joinToString("")
                TileEdge.Bottom -> data.last()
                TileEdge.Left -> data.map { it.first() }.joinToString("")
            }
        }

        fun edges(): List<String> {
            return TileEdge.values().map { edge(it) }
        }

        fun edgesForAllFlippages(): Set<String> {
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

    }

    enum class TileEdge {
        Top,
        Right,
        Bottom,
        Left
    }
}

