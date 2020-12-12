package com.tjasink.adventofcode_2020.common

fun loadInput(filename: String): List<String> {
    return ClassLoader.getSystemResourceAsStream(filename)
        .bufferedReader()
        .lineSequence()
        .toList()
}
