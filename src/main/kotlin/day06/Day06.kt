package day06

import getInputLines
import printAndMeasureResults

fun main() {
    printAndMeasureResults(
        part1 = { allParts(4) },
        part2 = { allParts(14) }
    )
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
            solution = index + distinctChars
        }
        index++
    }

    return solution
}
