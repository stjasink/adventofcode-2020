package com.tjasink.adventofcode_2020.common

import java.time.Duration
import java.time.LocalDateTime

fun timeCode(code: () -> Unit): Duration {
    for (i in 1..10) {
        code()
    }
    val timingsInNanos = mutableListOf<Long>()
    for (i in 1..10) {
        val start1 = LocalDateTime.now()
        code()
        timingsInNanos.add(Duration.between(start1, LocalDateTime.now()).toNanos())
    }
    return Duration.ofNanos(timingsInNanos.average().toLong())
}
