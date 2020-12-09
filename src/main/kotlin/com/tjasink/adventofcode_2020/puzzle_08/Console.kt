package com.tjasink.adventofcode_2020.puzzle_08

import java.util.*

fun main() {
    val input = ClassLoader.getSystemResourceAsStream("puzzle_08/code.txt")
        .bufferedReader()
        .lineSequence()
        .toList()

    for (i in 1..5) {
        val start1 = Date()
        val accumulatorAtLoopStart = Console().findAccumulatorAtLoopOrCompletionFromStrings(input)
        println("Time: ${Date().time - start1.time}")
        println("Accumulator at loop start: ${accumulatorAtLoopStart.first}")

        val start2 = Date()
        val accumulatorAtSuccess = Console().findAccumulatorAtSuccessfulCompletion(input)
        println("Time: ${Date().time - start2.time}")
        println("Accumulator at successful completion: $accumulatorAtSuccess")
    }

}

class Console {

    fun findAccumulatorAtLoopOrCompletionFromStrings(code: List<String>): Pair<Int, CompletionType> {
        return findAccumulatorAtLoopOrCompletion(code.map { decodeLine(it) })
    }

    fun findAccumulatorAtLoopOrCompletion(code: List<Instruction>): Pair<Int, CompletionType> {
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
            val (opcode, num) = code[pointer]
            when (opcode) {
                OpCode.nop -> {
                    pointer += 1
                }
                OpCode.acc -> {
                    acc += num
                    pointer += 1
                }
                OpCode.jmp -> {
                    pointer += num
                }
            }
        }
    }

    private fun pointersUsed(code: List<Instruction>): Set<Int> {
        var acc = 0
        var pointer = 0
        val pointersAlreadyUsed = mutableSetOf<Int>()

        while (true) {
            if (pointersAlreadyUsed.contains(pointer)) {
                return pointersAlreadyUsed
            } else if (pointer == code.size) {
                return pointersAlreadyUsed
            }
            pointersAlreadyUsed.add(pointer)
            val (opcode, num) = code[pointer]
            when (opcode) {
                OpCode.nop -> {
                    pointer += 1
                }
                OpCode.acc -> {
                    acc += num
                    pointer += 1
                }
                OpCode.jmp -> {
                    pointer += num
                }
            }
        }
    }

    fun findAccumulatorAtSuccessfulCompletion(codeStrings: List<String>): Int {
        var changesTried = 0
        val code = codeStrings.map { decodeLine(it) }
        val pointersUsedInFirstRun = pointersUsed(code)
        for (changeLine in code.indices) {
            if (!pointersUsedInFirstRun.contains(changeLine)) {
                continue
            }
            val newCode = mutableListOf<Instruction>().also { it.addAll(code) }
            val (opcode, num) = code[changeLine]
            when (opcode) {
                OpCode.nop -> {
                    newCode[changeLine] = Instruction(OpCode.jmp, num)
                }
                OpCode.jmp -> {
                    newCode[changeLine] = Instruction(OpCode.nop, num)
                }
                OpCode.acc -> {
                    continue
                }
            }
            changesTried += 1
            val result = findAccumulatorAtLoopOrCompletion(newCode)
            if (result.second == CompletionType.SUCCESS) {
                println("Changes tried: $changesTried")
                return result.first
            }
        }
        return 0
    }

    private fun decodeLine(line: String): Instruction {
        val split = line.split(' ')
        return Instruction(OpCode.valueOf(split[0]), split[1].toInt())
    }

    enum class OpCode {
        nop,
        acc,
        jmp
    }

    data class Instruction(
        val opcode: OpCode,
        val num: Int
    )

    enum class CompletionType {
        LOOP,
        SUCCESS
    }

}
