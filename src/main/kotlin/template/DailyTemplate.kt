package template

import printAndMeasureResults

fun main() {
    printAndMeasureResults(
        part1 = { part1() },
        part2 = { part2() }
    )
}

fun part1(): Int {
    return 1
}


fun part2(): Int {
    return 2
}
