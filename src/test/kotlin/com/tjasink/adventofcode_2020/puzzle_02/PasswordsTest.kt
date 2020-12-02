package com.tjasink.adventofcode_2020.puzzle_02

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class PasswordsTest {

    @Test
    fun `should count passwords that match policy 1`() {
        val passwordsAndPolicies = listOf(
                "3-5 f: fgfff",
                "1-5 r: cfqhhr",
                "6-8 n: lnnrnnnnn"
        )

        val result = Passwords().findNumberValid(passwordsAndPolicies, ::doesPasswordMatchPolicy1)

        assertEquals(3, result)
    }

    @Test
    fun `should not count passwords that do not match policy 1`() {
        val passwordsAndPolicies = listOf(
                "3-5 f: fghhf",
                "1-5 r: cfqhh",
                "6-8 n: lnnrnnnnnnnnnnnnnnnnn"
        )

        val result = Passwords().findNumberValid(passwordsAndPolicies, ::doesPasswordMatchPolicy1)

        assertEquals(0, result)
    }

    @Test
    fun `should count passwords that match policy 2`() {
        val passwordsAndPolicies = listOf(
                "1-3 a: abcde",
                "1-3 a: cbade"
        )

        val result = Passwords().findNumberValid(passwordsAndPolicies, ::doesPasswordMatchPolicy2)

        assertEquals(2, result)
    }

    @Test
    fun `should not count passwords that do not match policy 2`() {
        val passwordsAndPolicies = listOf(
                "1-3 b: cdefg",
                "2-9 c: ccccccccc"
        )

        val result = Passwords().findNumberValid(passwordsAndPolicies, ::doesPasswordMatchPolicy2)

        assertEquals(0, result)
    }
}