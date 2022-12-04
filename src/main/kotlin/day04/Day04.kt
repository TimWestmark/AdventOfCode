package day04

import getInputLines
import kotlin.system.measureTimeMillis

fun main() {
    val timeInMillisPart1 = measureTimeMillis {
        println("Solution Part 1: ${part1()}")
    }

    val timeInMillisPart2 = measureTimeMillis {
        println("Solution Part 2: ${part2()}")
    }

    println("(The part1 operation took $timeInMillisPart1 ms)")
    println("(The part2 operation took $timeInMillisPart2 ms)")


}

fun input(): List<Pair<IntRange, IntRange>> {
    return getInputLines("/day04/input.txt").map { line ->
        Pair(
            IntRange(
                line.split(",")[0].split("-")[0].toInt(),
                line.split(",")[0].split("-")[1].toInt()
            ),
            IntRange(
                line.split(",")[1].split("-")[0].toInt(),
                line.split(",")[1].split("-")[1].toInt()
            )
        )
    }
}
fun part1(): Int {
    return input().map { elvesRanges ->
        val firstRange = elvesRanges.first.toSet()
        val secondRange = elvesRanges.second.toSet()

        val intersection = firstRange.intersect(secondRange)
        intersection.size == firstRange.size || intersection.size == secondRange.size

    }.count { it }
}


fun part2(): Int {
    return input().map { elvesRanges ->
        val firstRange = elvesRanges.first.toSet()
        val secondRange = elvesRanges.second.toSet()

        val intersection = firstRange.intersect(secondRange)

        intersection.isNotEmpty()
    }.count { it }
}