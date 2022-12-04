package day01

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

fun input(): List<Int> {
    val weights: MutableList<Int> = mutableListOf()

    var curElvesWeight = 0
//    getInputLines("/day1/test-input.txt")
    getInputLines("/day01/input.txt").forEach { line ->
            if (line.isBlank()) {
                weights.add(curElvesWeight)
                curElvesWeight = 0
            } else {
                curElvesWeight += Integer.valueOf(line)
            }
        }

    return weights.toList()

}

fun part1(): Int? {
    return input().maxOrNull()
}

fun part2(): Int? {

    val lastIndex = input().lastIndex
    if (lastIndex <= 2) return null

    val sortedList = input().sorted()
    return sortedList[lastIndex] + sortedList[lastIndex - 1] + sortedList[lastIndex - 2]
}
