package com.tjasink.adventofcode_2020.puzzle_02

fun main() {
    val input = ClassLoader.getSystemResourceAsStream("puzzle_02/passwords.txt")
            .bufferedReader()
            .lineSequence()
            .toList()

    val numberValidPolicy1 = Passwords().findNumberValid(input, ::doesPasswordMatchPolicy1)
    val numberValidPolicy2 = Passwords().findNumberValid(input, ::doesPasswordMatchPolicy2)

    println("Number valid policy 1: $numberValidPolicy1")
    println("Number valid policy 2: $numberValidPolicy2")
}

fun doesPasswordMatchPolicy1(policy: Passwords.Policy, password: String): Boolean {
    val numLetters = password.count { it == policy.char}
    return numLetters >= policy.min && numLetters <= policy.max
}

fun doesPasswordMatchPolicy2(policy: Passwords.Policy, password: String): Boolean {
    val letter1 = password[policy.min - 1]
    val letter2 = password[policy.max - 1]
    return (letter1 == policy.char).xor(letter2 == policy.char)
}

class Passwords {

    fun findNumberValid(passwordsAndPolicies: List<String>, policyMatcher: (Policy, String) -> Boolean): Int {
        val passes = passwordsAndPolicies.map {
            val (policy, password) = parsePolicyAndPassword(it)
            policyMatcher(policy, password)
        }
        return passes.count { it }
    }

    private fun parsePolicyAndPassword(policyAndPassword: String): Pair<Policy, String> {
        val splitOnColon = policyAndPassword.split(":")
        val password = splitOnColon[1].trim()
        val splitOnSpace = splitOnColon[0].split(" ")
        val splitOnDash = splitOnSpace[0].split("-")
        val policy = Policy(splitOnDash[0].toInt(), splitOnDash[1].toInt(), splitOnSpace[1].first())
        return Pair(policy, password)
    }

    data class Policy(
            val min: Int,
            val max: Int,
            val char: Char)

}
