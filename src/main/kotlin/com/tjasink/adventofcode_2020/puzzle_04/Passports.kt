package com.tjasink.adventofcode_2020.puzzle_04

fun main() {
    val input = ClassLoader.getSystemResourceAsStream("puzzle_04/passports.txt")
        .bufferedReader()
        .lineSequence()
        .toList()

    val numWithEnoughFields = Passports().countPassportsWithEnoughFields(input)

    println("Number with enough fields: $numWithEnoughFields")

    val numWithValidFields = Passports().countPassportsWithValidFields(input)

    println("Number with valid fields: $numWithValidFields")
}

class Passports {

    private val fieldsRequired = setOf(
        "byr",
        "iyr",
        "eyr",
        "hgt",
        "hcl",
        "ecl",
        "pid",
//        "cid"
    )

    fun countPassportsWithEnoughFields(data: List<String>): Int {
        var numValid = 0
        var fieldsFound = mutableSetOf<String>()
        (data + "").forEach { line ->
            if (line.isEmpty()) {
                if (fieldsFound.containsAll(fieldsRequired)) {
                    numValid += 1
                }
                fieldsFound = mutableSetOf<String>()
            }
            val fieldsOnLine = line.split(' ')
                .map { it.split(':') }
                .map { it.first() }
            fieldsFound.addAll(fieldsOnLine)
        }

        return numValid
    }

    fun countPassportsWithValidFields(data: List<String>): Int {
        var numValid = 0
        var fieldsValid = mutableSetOf<String>()
        (data + "").forEach { line ->
            if (line.isEmpty()) {
                if (fieldsValid.containsAll(fieldsRequired)) {
                    numValid += 1
                }
                fieldsValid = mutableSetOf()
            } else {
                val validFieldsOnLine = line.split(' ')
                    .map { it.split(':') }
                    .filter { isValidField(it[0], it[1]) }
                    .map { it.first() }
                fieldsValid.addAll(validFieldsOnLine)
            }
        }

        return numValid
    }

    fun isValidField(name: String, value: String): Boolean {
        return when (name) {
            "byr" -> { // (Birth Year) - four digits; at least 1920 and at most 2002.
                value.matches(Regex("[0-9]{4}")) && value.toInt() in 1920..2002
            }
            "iyr" -> { // (Issue Year) - four digits; at least 2010 and at most 2020.
                value.matches(Regex("[0-9]{4}")) && value.toInt() in 2010..2020
            }
            "eyr" -> { // (Expiration Year) - four digits; at least 2020 and at most 2030.
                value.matches(Regex("[0-9]{4}")) && value.toInt() in 2020..2030
            }
            "hgt" -> { // (Height) - a number followed by either cm or in:
                        //If cm, the number must be at least 150 and at most 193.
                        //If in, the number must be at least 59 and at most 76.
                val heightRegEx = Regex("(\\d+)(in|cm)")
                val match = heightRegEx.find(value)
                if (match == null) {
                    false
                } else {
                    val (num, units) = match.destructured
                    when (units) {
                        "cm" -> {
                            num.toInt() in 150..193
                        }
                        "in" -> {
                            num.toInt() in 59..76
                        }
                        else -> false
                    }
                }
            }
            "hcl" -> { // (Hair Color) - a # followed by exactly six characters 0-9 or a-f.
                value.matches(Regex("#[0-9a-f]{6}"))
            }
            "ecl" -> { // (Eye Color) - exactly one of: amb blu brn gry grn hzl oth.
                setOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth").contains(value)
            }
            "pid" -> { // (Passport ID) - a nine-digit number, including leading zeroes.
                value.matches(Regex("[0-9]{9}"))
            }
            else -> false
        }
    }

}
