package y2024.day07

import Coroutines.parallelMap
import StringStuff

fun main() {
    AoCGenerics.printAndMeasureResults(
        part1 = { part1() },
        part2 = { part2() }
    )
}
fun input(): List<Pair<Long, List<Long>>> {
    return AoCGenerics.getInputLines("/y2024/day07/input.txt").map { line ->
        val (a, b) = line.split(":")
        Pair(a.toLong(), b.trim().split(" ").map { it.trim().toLong() })
    }
}

fun isValid(testValue: Long, numbers: List<Long>, operators: List<String>): Boolean {
    val operatorCombinations = StringStuff.generateStringCombinations(operators, numbers.size - 1)
    return operatorCombinations.any { combinations ->
        calculateResult(numbers, combinations, testValue) == testValue
    }
}

fun calculateResult(numbers: List<Long>, operators: List<String>, testValue: Long): Long {
    var result = numbers.first()
    for (i in operators.indices) {
        val nextNumber = numbers[i + 1]
        result = when (operators[i]) {
            "+" -> result + nextNumber
            "*" -> result * nextNumber
            "||" -> (result.toString() + nextNumber.toString()).toLong()
            else -> throw IllegalArgumentException("Unknown operator: ${operators[i]}")
        }

        // No need to keep calculating if we already know the result is too high
        if (result > testValue) {
            return -1
        }
    }
    return result
}

fun part1(): Long {
    val equations = input()
    val operators = listOf("+", "*")

    return equations.sumOf { (testValue, numbers) ->
        if (isValid(testValue, numbers, operators)) testValue else 0
    }
}

fun part2(): Long {
    val equations = input()
    val operators = listOf("+", "*", "||")


    return equations.parallelMap { (testValue, numbers) ->
        if (isValid(testValue, numbers, operators)) testValue else 0
    }.sum()

}
