package y2015.day04

import java.math.BigInteger
import java.security.MessageDigest

fun main() {
    AoCGenerics.printAndMeasureResults(
        part1 = { part1() },
        part2 = { part2() }
    )
}

fun input(): String {
    return AoCGenerics.getInputLines("/y2015/day04/input.txt").first()
}

fun calculateMD5Sum(input: String): String {
    val md = MessageDigest.getInstance("MD5")
    return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
}

fun part1(): Int {
    val secretKey = input()

    var counter = 1
    while (true) {
        if (calculateMD5Sum(secretKey + counter).startsWith("00000")) {
            return counter
        }

        counter++
    }

}


fun part2(): Int {
    val secretKey = input()
    var counter = 1
    while (true) {
        if (calculateMD5Sum(secretKey + counter).startsWith("000000")) {
            return counter
        }
        counter++
    }
}

