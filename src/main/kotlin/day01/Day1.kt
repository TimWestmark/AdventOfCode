package day01

import getInputLines

fun main() {
    println(part1())
    println(part2())
}

fun input(): List<Int> {
    val weights: MutableList<Int> = mutableListOf()

    var curElvesWeight = 0
//    getInputLines("/day1/test-input.txt")
    getInputLines("/day1/input.txt").forEach { line ->
            if (line.isBlank()) {
                weights.add(curElvesWeight)
                curElvesWeight = 0
            } else {
                curElvesWeight += Integer.valueOf(line)
            }
        }

    return weights.toList()

}

fun part1(): Int {
    return input().max()
}

fun part2(): Int {
    val lastIndex = input().lastIndex
    val sortedList = input().sorted()
    return sortedList[lastIndex] + sortedList[lastIndex - 1] + sortedList[lastIndex - 2]
}
