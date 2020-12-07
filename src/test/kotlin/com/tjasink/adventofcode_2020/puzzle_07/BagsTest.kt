package com.tjasink.adventofcode_2020.puzzle_07

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class BagsTest {

    val rules = """
            light red bags contain 1 bright white bag, 2 muted yellow bags.
            dark orange bags contain 3 bright white bags, 4 muted yellow bags.
            bright white bags contain 1 shiny gold bag.
            muted yellow bags contain 2 shiny gold bags, 9 faded blue bags.
            shiny gold bags contain 1 dark olive bag, 2 vibrant plum bags.
            dark olive bags contain 3 faded blue bags, 4 dotted black bags.
            vibrant plum bags contain 5 faded blue bags, 6 dotted black bags.
            faded blue bags contain no other bags.
            dotted black bags contain no other bags.
        """.trimIndent().split('\n')

    @Test
    fun `should count bags that can contain shiny gold bag`() {
        val answer = Bags().countBagsThatCanContain("shiny gold", rules)

        assertEquals(4, answer)
    }

    @Test
    fun `should count bags that need to be inside shiny gold bag`() {
        val answer = Bags().countBagsThatNeedToBeInside("shiny gold", rules)

        assertEquals(32, answer)
    }
}