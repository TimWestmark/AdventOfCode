package y2024.day01

import kotlin.math.abs

fun main() {
    AoCGenerics.printAndMeasureResults(
        part1 = { part1() },
        part2 = { part2() }
    )
}

fun input(): Pair<MutableList<Int>,MutableList<Int>> {
    val left = emptyList<Int>().toMutableList()
    val right = emptyList<Int>().toMutableList()
    AoCGenerics.getInputLines("/y2024/day01/input.txt").map {
        val parts = it.split("   ")
        left.add(parts[0].toInt())
        right.add(parts[1].toInt())
    }

    return Pair(left, right)
}

fun part1(): Int {
    val (left, right) = input()
    left.sort()
    right.sort()

    return left.indices.sumOf {
        abs(left[it] - right[it])
    }
}

fun part2(): Int {
    val (left, right) = input()
    val ocs = mutableMapOf<Int, Int>()
    return left.sumOf {l -> l * ocs.computeIfAbsent(l) { right.count { it == l } } }
}
