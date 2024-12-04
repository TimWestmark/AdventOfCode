package y2024.day04

import Matrix
import MatrixUtils

fun main() {
    AoCGenerics.printAndMeasureResults(
        part1 = { part1() },
        part2 = { part2() }
    )
}

fun input(): Matrix<Char> {
    return MatrixUtils.createMatrix(AoCGenerics.getInputLines("/y2024/day04/input.txt")) { _, row ->
        row.map { char ->
            char
        }
    }
}


fun count(grid: List<List<Char>>, word: String): Int {
    var count = 0

    val directions = listOf(
        Pair(1, 0),   // Right
        Pair(0, 1),   // Down
        Pair(1, 1),   // Diagonal Down-Right
        Pair(-1, 1),  // Diagonal Down-Left
        Pair(-1, 0),  // Left (Reverse)
        Pair(0, -1),  // Up (Reverse)
        Pair(-1, -1), // Diagonal Up-Left (Reverse)
        Pair(1, -1)   // Diagonal Up-Right (Reverse)
    )

    fun isValid(x: Int, y: Int): Boolean = x in grid.indices && y in grid[0].indices

    fun search(x: Int, y: Int, dx: Int, dy: Int): Boolean {
        word.indices.forEach { i ->
            val nx = x + i * dx
            val ny = y + i * dy
            if (!isValid(nx, ny)) {
                return false
            }
            if (grid[nx][ny] != word[i]) {
                return false
            }
        }
        return true
    }

    for (y in grid.indices) {
        for (x in grid[0].indices) {
            for ((dx, dy) in directions) {
                if (search(x, y, dx, dy)) {
                   count++
                }
            }
        }
    }

    return count
}

fun countCrossOccurrences(grid: List<List<Char>>, pattern: Map<Pair<Int, Int>, Char>): Int {
    var count = 0

    fun isValid(x: Int, y: Int): Boolean = x in grid.indices && y in grid[0].indices

    fun matchesPattern(x: Int, y: Int): Boolean {
        for ((diffX, diffY) in pattern.keys) {
            val checkX = x + diffX
            val checkY = y + diffY
            if (!isValid(checkX, checkY)) {
                return false
            }

            if (grid[checkY][checkX] != pattern[diffX to diffY]) {
                return false
            }
        }
        return true
    }

    for (y in grid.indices) {
        for (x in grid[0].indices) {
            if (matchesPattern(x, y)) {
               count++
            }
        }
    }

    return count
}

fun part1(): Int {
    val input = input()
    return count(input, "XMAS")
}

fun part2(): Int {
    val input = input()
    val patterns = listOf(
        mapOf(
            Pair(0, 0) to 'M', Pair(2, 0) to 'S',
            Pair(1, 1) to 'A',
            Pair(0, 2) to 'M', Pair(2, 2) to 'S'
        ),
        mapOf(
            Pair(0, 0) to 'M', Pair(2, 0) to 'M',
            Pair(1, 1) to 'A',
            Pair(0, 2) to 'S', Pair(2, 2) to 'S'
        ),
        mapOf(
            Pair(0, 0) to 'S', Pair(2, 0) to 'S',
            Pair(1, 1) to 'A',
            Pair(0, 2) to 'M', Pair(2, 2) to 'M'
        ),
        mapOf(
            Pair(0, 0) to 'S', Pair(2, 0) to 'M',
            Pair(1, 1) to 'A',
            Pair(0, 2) to 'S', Pair(2, 2) to 'M'
        ),
    )
    return patterns.sumOf { pattern -> countCrossOccurrences(input, pattern) }
}
