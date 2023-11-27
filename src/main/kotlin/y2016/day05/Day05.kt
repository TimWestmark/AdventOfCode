package y2016.day05

import java.math.BigInteger
import java.security.MessageDigest

fun main() {
    AoCGenerics.printAndMeasureResults(
        part1 = { part1() },
        part2 = { part2() }
    )
}

fun input(): String {
    return AoCGenerics.getInputLines("/y2016/day05/input.txt").first()
}

fun md5(input:String): String {
    val md = MessageDigest.getInstance("MD5")
    return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
}

fun part1(): String {
    val input = input()
    var result = ""
    var i = 0

    while (result.length < 8) {
        val hash = md5(input + i)
        if (hash.startsWith("00000")){
            result += hash[5]
        }
        i++
    }

    return result
}


fun part2(): String {
    val input = input()

    val result = "________".toCharArray()
    var i = 0

    while (result.any { it == '_' }) {
        val hash = md5(input + i)
        if (hash.startsWith("00000")){
            val pos = hash[5]
            if (pos.isDigit() && pos.digitToInt() < 8 && result[pos.digitToInt()] == '_') {
                result[pos.digitToInt()] = hash[6]
                println(result)
            }
        }
        i++
    }
    return result.joinToString("")
}

