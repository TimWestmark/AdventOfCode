package y2023.day11

import AoCGenerics
import Coord
import distance

fun main() {
    AoCGenerics.printAndMeasureResults(
        part1 = { part1() },
        part2 = { part2() }
    )
}

private fun calculateGalaxyDistanceSum(expansionFactor: Int): Long {
    val galaxies = AoCGenerics.getInputLines("/y2023/day11/input.txt").flatMapIndexed { y, row ->
        row.mapIndexedNotNull { x, c ->
            if (c == '#') Coord(x, y) else null
        }
    }

    val galaxyXRanges = IntRange(galaxies.minOf {it.x}, galaxies.maxOf{it.x})
    val galaxyYRanges = IntRange(galaxies.minOf { it.y}, galaxies.maxOf { it.y })

    val spaceX = galaxyXRanges - galaxies.map{it.x}.toSet()
    val spaceY = galaxyYRanges - galaxies.map{it.y}.toSet()

    val expandedUniverseGalaxyCoords = galaxies.map { galaxy ->
        val addSpaceX = spaceX.count { it < galaxy.x } * expansionFactor
        val addSpaceY = spaceY.count { it < galaxy.y } * expansionFactor
        Coord(galaxy.x + addSpaceX, galaxy.y + addSpaceY)
    }

    val galaxyPairs = expandedUniverseGalaxyCoords.mapIndexed { index, galaxy ->
        expandedUniverseGalaxyCoords.slice(index + 1 until expandedUniverseGalaxyCoords.size).map { Pair(galaxy, it)}
    }.flatten()

    return galaxyPairs.sumOf { it.first.distance(it.second) }
}

fun part1() = calculateGalaxyDistanceSum(1)
fun part2() = calculateGalaxyDistanceSum(1000000-1)

