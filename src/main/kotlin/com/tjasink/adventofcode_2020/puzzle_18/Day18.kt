package com.tjasink.adventofcode_2020.puzzle_18

import com.tjasink.adventofcode_2020.common.Solver
import com.tjasink.adventofcode_2020.common.loadInput
import com.tjasink.adventofcode_2020.common.runAndTime
import java.lang.RuntimeException

fun main() {
    val input = loadInput("puzzle_18/data.txt")
    val solver = Day18()
    runAndTime(solver, input)
}

class Day18 : Solver {

    override fun part1(input: List<String>): Long {
        val answers = input.map { Part1Evaluator(it).parse() }
        return answers.sum()
    }

    override fun part2(input: List<String>): Long {
        val answers = input.map { Part2Evaluator(it).parse() }
        return answers.sum()
    }

    abstract class Evaluator(private val str: String) {
        private var pos = -1
        private var ch = Char.MIN_VALUE

        fun parse(): Long {
            nextChar()
            val x = parseExpression()
            if (pos < str.length) throw RuntimeException("Unexpected: $ch")
            return x
        }

        private fun nextChar() {
            ch = if (++pos < str.length) str[pos] else Char.MIN_VALUE
        }

        fun eat(charToEat: Char): Boolean {
            while (ch == ' ') nextChar()
            if (ch == charToEat) {
                nextChar()
                return true
            }
            return false
        }

        fun parseFactor(): Long {
            eat(' ')
            val x: Long
            val startPos = pos
            when {
                eat('(') -> {
                    x = parseExpression()
                    eat(')')
                }
                ch.isDigit() -> {
                    while (ch.isDigit()) nextChar()
                    x = str.substring(startPos, pos).toLong()
                }
                else -> {
                    throw RuntimeException("Unexpected: $ch")
                }
            }
            return x
        }

        abstract fun parseExpression(): Long

    }

    class Part1Evaluator(str: String): Evaluator(str) {
        // everything is treated the same precedence in the expression

        override fun parseExpression(): Long {
            var x = parseFactor()
            while (true) {
                when {
                    eat('+') -> x += parseFactor()
                    eat('-') -> x -= parseFactor()
                    eat('*') -> x *= parseFactor()
                    eat('/') -> x /= parseFactor()
                    else -> return x
                }
            }
        }
    }

    class Part2Evaluator(str: String): Evaluator(str) {
        // usual meanings are flipped here to give + and - precedence instead of * and /

        override fun parseExpression(): Long {
            var x = parseTerm()
            while (true) {
                when {
                    eat('*') -> x *= parseTerm()
                    eat('/') -> x /= parseTerm()
                    else -> return x
                }
            }
        }

        fun parseTerm(): Long {
            var x = parseFactor()
            while (true) {
                when {
                    eat('+') -> x += parseFactor()
                    eat('-') -> x -= parseFactor()
                    else -> return x
                }
            }
        }
    }

}
