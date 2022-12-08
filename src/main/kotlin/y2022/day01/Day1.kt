package y2022.day01

import java.lang.IllegalStateException

fun main() {
    AoCGenerics.printAndMeasureResults(
        part1 = { part1() },
        part2 = { part2() }
    )
}

fun input(): List<Int> {
    val weights: MutableList<Int> = mutableListOf()

    var curElvesWeight = 0
    AoCGenerics.getInputLines("/day01/input.txt").forEach { line ->
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
    return input().maxOrNull()!!
}

fun part2(): Int {

    val lastIndex = input().lastIndex
    if (lastIndex <= 2) throw IllegalStateException()

    val sortedList = input().sorted()
    return sortedList[lastIndex] + sortedList[lastIndex - 1] + sortedList[lastIndex - 2]
}
