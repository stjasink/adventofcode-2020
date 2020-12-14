package com.tjasink.adventofcode_2020.puzzle_14

import com.tjasink.adventofcode_2020.common.Solver
import com.tjasink.adventofcode_2020.common.loadInput
import com.tjasink.adventofcode_2020.common.runAndTime

fun main() {
    val input = loadInput("puzzle_14/data.txt")
    val solver = Day14()
    runAndTime(solver, input)
}

class Day14 : Solver {

    override fun part1(input: List<String>): Long {
        return processLines(input, this::part1MemoryAssignment)
    }

    override fun part2(input: List<String>): Long {
        return processLines(input, this::part2MemoryAssignment)
    }

    private fun processLines(input: List<String>, doAssignments: (String, MutableMap<Long, Long>, Long, Long) -> Unit): Long {
        var currentMask = ""
        val memory = mutableMapOf<Long, Long>()

        val maskRegex = Regex("mask = ([X01]{36})")
        val memRegex = Regex("mem\\[([0-9]+)] = ([0-9]+)")

        input.forEach {
            if (maskRegex.matches(it)) {
                val found = maskRegex.find(it)!!
                currentMask = found.destructured.component1()
            } else {
                val found = memRegex.find(it)!!
                val memAddress = found.destructured.component1().toLong()
                val value = found.destructured.component2().toLong()
                doAssignments(currentMask, memory, memAddress, value)
            }
        }

        return memory.values.sum()
    }

    private fun part1MemoryAssignment(
        currentMask: String,
        memory: MutableMap<Long, Long>,
        memAddress: Long,
        value: Long
    ) {
        val maskedValue = applyPart1Mask(currentMask, value)
        memory[memAddress] = maskedValue
    }

    private fun part2MemoryAssignment(
        currentMask: String,
        memory: MutableMap<Long, Long>,
        memAddress: Long,
        value: Long
    ) {
        val maskedAddresses = applyPart2Mask(currentMask, memAddress)
        maskedAddresses.forEach { address -> memory[address] = value }
    }

    private fun applyPart1Mask(currentMask: String, value: Long): Long {
        val binaryValue = value.toString(2).takeLast(36).padStart(36, '0')
        val maskedValue = currentMask.mapIndexed { index, c ->
            when (c) {
                'X' -> binaryValue[index]
                '0' -> '0'
                '1' -> '1'
                else -> throw IllegalStateException("Found ${currentMask[index]} in mask")
            }
        }.joinToString("")
        return maskedValue.toLong(2)
    }

    private fun applyPart2Mask(currentMask: String, address: Long): List<Long> {
        val binaryValue = address.toString(2).takeLast(36).padStart(36, '0')
        val maskedValues = mutableListOf<StringBuilder>().apply { this.add(java.lang.StringBuilder()) }
        currentMask.forEachIndexed { index, c ->
            when (c) {
                'X' -> {
                    val newMaskedValues = maskedValues.map { StringBuilder(it) }
                    maskedValues.addAll(newMaskedValues)
                    maskedValues.forEachIndexed { maskIndex, mask -> mask.append(if (maskIndex < newMaskedValues.size) '0' else '1') }
                }
                '0' -> maskedValues.forEach { it.append(binaryValue[index]) }
                '1' -> maskedValues.forEach { it.append('1') }
                else -> throw IllegalStateException("Found ${currentMask[index]} in mask")
            }
        }
        return maskedValues.map { it.toString().toLong(2) }
    }
}
