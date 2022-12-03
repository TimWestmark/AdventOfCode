package day03

import getInputLines

fun main() {
    println("Solution Part 1: ${part1()}")
    println("Solution Part 2: ${part2()}")
}

fun input(): List<Pair<String, String>> {
    return getInputLines("/day03/input.txt").map { line ->
        Pair(line.substring(0, line.length/2)
            ,line.substring(line.length/2)
        )
    }
}

fun input2(): List<List<String>> {
    return getInputLines("/day03/input.txt").chunked(3)
}

fun Char.getValue(): Int {
    return when {
        this.isLowerCase() -> this.code - 96
        else -> this.code - 38
    }

}

fun part1(): Int {
    return input().map { compartments ->
        compartments.first.toSet().intersect(compartments.second.toSet()).first()
    }.sumOf { char ->
        char.getValue()
    }

}

fun part2(): Int {
    return input2().map { group ->
        group[0].toSet()
            .intersect(group[1].toSet())
            .intersect(group[2].toSet())
            .first()
    }.sumOf { char ->
        char.getValue()
    }
}
