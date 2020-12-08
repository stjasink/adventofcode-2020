package com.tjasink.adventofcode_2020.puzzle_08

import java.util.*

fun main() {
    val input = ClassLoader.getSystemResourceAsStream("puzzle_08/code.txt")
        .bufferedReader()
        .lineSequence()
        .toList()

    val start1 = Date()
    val accumulatorAtLoopStart = Console().findAccumulatorAtLoopOrCompletion(input)
    println("Time: ${Date().time - start1.time}")
    println("Accumulator at loop start: ${accumulatorAtLoopStart.first}")

    val start2 = Date()
    val accumulatorAtSuccess = Console().findAccumulatorAtSuccessfulCompletion(input)
    println("Time: ${Date().time - start2.time}")
    println("Accumulator at succesful completion: $accumulatorAtSuccess")

}

class Console {

    fun findAccumulatorAtLoopOrCompletion(code: List<String>): Pair<Int, CompletionType> {
        var acc = 0
        var pointer = 0
        val pointersAlreadyUsed = mutableSetOf<Int>()

        while (true) {
            if (pointersAlreadyUsed.contains(pointer)) {
                return Pair(acc, CompletionType.LOOP)
            } else if (pointer == code.size) {
                return Pair(acc, CompletionType.SUCCESS)
            }
            pointersAlreadyUsed.add(pointer)
            val (ins, num) = decodeLine(code[pointer])
            when (ins) {
                "nop" -> {
                    pointer += 1
                }
                "acc" -> {
                    acc += num
                    pointer += 1
                }
                "jmp" -> {
                    pointer += num
                }
            }
        }
    }

    fun findAccumulatorAtSuccessfulCompletion(code: List<String>): Int {
        for (changeLine in code.indices) {
            val newCode = mutableListOf<String>()
            newCode.addAll(code)
            val (ins, num) = decodeLine(code[changeLine])
            when (ins) {
                "nop" -> {
                    newCode[changeLine] = "jmp $num"
                }
                "jmp" -> {
                    newCode[changeLine] = "nop $num"
                }
                "acc" -> {
                    // do nothing
                }
            }
            val result = findAccumulatorAtLoopOrCompletion(newCode)
            if (result.second == CompletionType.SUCCESS) {
                return result.first
            }
        }
        return 0
    }

    private fun decodeLine(line: String): Pair<String, Int> {
        val split = line.split(' ')
        return Pair(split[0], split[1].toInt())
    }

    enum class CompletionType {
        LOOP,
        SUCCESS
    }

}
