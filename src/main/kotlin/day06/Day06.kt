package day06

import getInputLines
import kotlin.system.measureTimeMillis

fun main() {
    val timeInMillisPart1 = measureTimeMillis {
        println("Solution Part 1: ${allParts(4)}")
    }

    val timeInMillisPart2 = measureTimeMillis {
        println("Solution Part 2: ${allParts(14)}")
    }

    println("(The part1 operation took $timeInMillisPart1 ms)")
    println("(The part2 operation took $timeInMillisPart2 ms)")


}


fun input(): String {
    return getInputLines("/day06/input.txt").first()
//    return getInputLines("/day06/test-input.txt").first()
}

fun allParts(distinctChars: Int): Int {
    val line = input()
    var solution = 0
    var index = 0
    while (solution == 0) {
        val chars = line.substring(index, index + distinctChars)
        if (chars.toSet().size == distinctChars) {
            solution = index+distinctChars
        }
        index++
    }

    return solution
}