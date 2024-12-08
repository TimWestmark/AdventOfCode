package y2024.day08

import Coord
import Matrix
import MatrixUtils.forEachMatrixElement

fun main() {
    AoCGenerics.printAndMeasureResults(
        part1 = { part1() },
        part2 = { part2() }
    )
}

data class Tile(val coord: Coord, val freq: Char?, var isAntiNode: Boolean = false)

fun input(): Matrix<Tile> {
    return MatrixUtils.createMatrix(AoCGenerics.getInputLines("/y2024/day08/input.txt")) { y, row ->
        row.mapIndexed { x, c ->
            Tile(
                coord = Coord(x, y),
                freq = if (c == '.') null else c
            )
        }
    }
}

fun getAntennas(map: Matrix<Tile>): MutableList<Triple<Int, Int, Char>> {
    val antennas = mutableListOf<Triple<Int, Int, Char>>()

    map.forEachMatrixElement { tile ->
        if (tile.freq != null) {
            antennas.add(Triple(tile.coord.x, tile.coord.y, tile.freq))
        }
    }
    return antennas
}

fun part1(): Int {
    val map = input()
    val antennas = getAntennas(map)
    val antinodes = mutableSetOf<Pair<Int, Int>>()

    for (i in antennas.indices) {
        for (j in i + 1 until antennas.size) {
            val (x1, y1, freq1) = antennas[i]
            val (x2, y2, freq2) = antennas[j]

            if (freq1 != freq2) {
                continue
            }

            val dx = x2 - x1
            val dy = y2 - y1

            val antinode1 = Pair(x1 - dx, y1 - dy)
            val antinode2 = Pair(x2 + dx, y2 + dy)

            if (antinode1.first in map[0].indices && antinode1.second in map.indices) {
                antinodes.add(antinode1)
            }
            if (antinode2.first in map[0].indices && antinode2.second in map.indices) {
                antinodes.add(antinode2)
            }
        }
    }
    return antinodes.size
}

fun part2(): Int {
    val grid = input()
    val antennas = getAntennas(grid)
    val antinodes = mutableSetOf<Pair<Int, Int>>()

    for (i in antennas.indices) {
        for (j in i + 1 until antennas.size) {
            var (x1, y1, freq1) = antennas[i]
            var (x2, y2, freq2) = antennas[j]

            if (freq1 != freq2) {
                continue
            }
            // matching antennas are antinodes, now
            antinodes.add(Pair(x1, y1))
            antinodes.add(Pair(x2, y2))

            val dx = x2 - x1
            val dy = y2 - y1

            do {
                val antinode1 = Pair(x1 - dx, y1 - dy)
                val antinode2 = Pair(x2 + dx, y2 + dy)

                val isA1inGrid = antinode1.first in grid[0].indices && antinode1.second in grid.indices
                val isA2inGrid = antinode2.first in grid[0].indices && antinode2.second in grid.indices

                if (isA1inGrid) {
                    antinodes.add(antinode1)
                }
                if (isA2inGrid) {
                    antinodes.add(antinode2)
                }

                x1 = antinode1.first
                y1 = antinode1.second
                x2 = antinode2.first
                y2 = antinode2.second
            } while (isA1inGrid || isA2inGrid)
        }
    }
    return antinodes.size
}
