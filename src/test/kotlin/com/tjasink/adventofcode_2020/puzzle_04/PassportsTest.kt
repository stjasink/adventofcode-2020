package com.tjasink.adventofcode_2020.puzzle_04

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class PassportsTest {

    @Test
    fun `should count passports that have all fields and country is optional`() {
        val input = """
            ecl:gry pid:860033327 eyr:2020 hcl:#fffffd
            byr:1937 iyr:2017 cid:147 hgt:183cm

            iyr:2013 ecl:amb cid:350 eyr:2023 pid:028048884
            hcl:#cfa07d byr:1929

            hcl:#ae17e1 iyr:2013
            eyr:2024
            ecl:brn pid:760753108 byr:1931
            hgt:179cm

            hcl:#cfa07d eyr:2025 pid:166559648
            iyr:2011 ecl:brn hgt:59in
        """.trimIndent().split('\n')

        val result = Passports().countPassportsWithEnoughFields(input)

        assertEquals(2, result)
    }

    @Test
    fun `should count passports that have valid fields`() {
        val input = """
            pid:087499704 hgt:74in ecl:grn iyr:2012 eyr:2030 byr:1980
            hcl:#623a2f

            eyr:2029 ecl:blu cid:129 byr:1989
            iyr:2014 pid:896056539 hcl:#a97842 hgt:165cm

            hcl:#888785
            hgt:164cm byr:2001 iyr:2015 cid:88
            pid:545766238 ecl:hzl
            eyr:2022

            iyr:2010 hgt:158cm hcl:#b6652a ecl:blu byr:1944 eyr:2021 pid:093154719
        """.trimIndent().split('\n')

        val result = Passports().countPassportsWithValidFields(input)

        assertEquals(4, result)
    }

    @Test
    fun `should not count passports that have invalid fields`() {
        val input = """
            eyr:1972 cid:100
            hcl:#18171d ecl:amb hgt:170 pid:186cm iyr:2018 byr:1926

            iyr:2019
            hcl:#602927 eyr:1967 hgt:170cm
            ecl:grn pid:012533040 byr:1946

            hcl:dab227 iyr:2012
            ecl:brn hgt:182cm pid:021572410 eyr:2020 byr:1992 cid:277

            hgt:59cm ecl:zzz
            eyr:2038 hcl:74454a iyr:2023
            pid:3556412378 byr:2007
        """.trimIndent().split('\n')

        val result = Passports().countPassportsWithValidFields(input)

        assertEquals(0, result)
    }

    @Test
    fun `should validate fields`() {
        val passportChecker = Passports()

//        byr valid:   2002
//        byr invalid: 2003
//
//        hgt valid:   60in
//        hgt valid:   190cm
//        hgt invalid: 190in
//        hgt invalid: 190
//
//        hcl valid:   #123abc
//        hcl invalid: #123abz
//        hcl invalid: 123abc
//
//        ecl valid:   brn
//                ecl invalid: wat
//
//                pid valid:   000000001
//        pid invalid: 0123456789

        assertTrue(passportChecker.isValidField("byr", "2002"))
        assertFalse(passportChecker.isValidField("byr", "2003"))

        assertTrue(passportChecker.isValidField("hgt", "60in"))
        assertTrue(passportChecker.isValidField("hgt", "190cm"))
        assertFalse(passportChecker.isValidField("hgt", "190in"))
        assertFalse(passportChecker.isValidField("hgt", "190"))

        assertTrue(passportChecker.isValidField("hcl", "#123abc"))
        assertFalse(passportChecker.isValidField("hcl", "#123abz"))
        assertFalse(passportChecker.isValidField("hcl", "123abc"))

        assertTrue(passportChecker.isValidField("ecl", "brn"))
        assertFalse(passportChecker.isValidField("ecl", "wat"))

        assertTrue(passportChecker.isValidField("pid", "000000001"))
        assertFalse(passportChecker.isValidField("pid", "0123456789"))
    }

}