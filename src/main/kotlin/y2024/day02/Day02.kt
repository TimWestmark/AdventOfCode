package y2024.day02

import kotlin.math.abs

fun main() {
    AoCGenerics.printAndMeasureResults(
        part1 = { part1() },
        part2 = { part2() }
    )
}

fun input(): List<List<Int>> {
    return AoCGenerics.getInputLines("/y2024/day02/input.txt").map { line ->
        line.split(" ").map { it.toInt() }
    }
}

fun List<Int>.isSafe(): Boolean {
    var increasing = true
    var decreasing = true
    var differenceRangeOk = true
    for (i in 0 until size - 1) {
        // check conditions
        if (decreasing && this[i] <= this[i + 1]) {
            decreasing = false
        }
        if (increasing && this[i] >= this[i + 1]) {
            increasing = false
        }
        if (abs(this[i] - this[i + 1]) > 3) {
            differenceRangeOk = false
        }

        // if we are not safe, we can stop!
        if (!((increasing || decreasing) && differenceRangeOk)) {
            return false
        }
    }

    return true
}

fun List<Int>.isSafeWithDampener(): Boolean {
    if (isSafe()) {
        return true
    }

    // Looking at each possible sublist until one is safe
    for(i in indices) {
        val new = this.toMutableList()
        new.removeAt(i)

        if(new.isSafe()) {
            return true
        }
    }
    return false
}

fun part1(): Int {
    val reports = input()
    return reports.count { it.isSafe() }
}

fun part2(): Int {
    val reports = input()
    return reports.count { it.isSafeWithDampener() }
}

