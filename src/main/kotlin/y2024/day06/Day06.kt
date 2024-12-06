package y2024.day06

import Coord
import Matrix
import MatrixUtils.createDeepCopy
import go

fun main() {
    AoCGenerics.printAndMeasureResults(
        part1 = { part1() },
        part2 = { part2() }
    )
}

fun input(): Pair<Matrix<Tile>,Coord> {
    var guardianStart = Coord(-1,-1)
    val map =  MatrixUtils.createMatrix(AoCGenerics.getInputLines("/y2024/day06/input.txt")) { y, row ->
        row.mapIndexed { x, c ->
            if (c == '^') {
                guardianStart = Coord(x,y)
            }

            Tile(Coord(x, y), c == '#')
        }
    }

    return Pair(map, guardianStart)
}

data class Tile(val coord: Coord, var isObstacle: Boolean)


fun getVisitedTiles(): Set<Coord> {
    val map = input().first
    var guardianPostition = input().second

    var guardianDirection = MatrixUtils.SimpleDirection.UP
    val visited = mutableSetOf(guardianPostition)

    do {
        val next = guardianPostition.go(guardianDirection)
        if (next.x < 0 || next.y < 0 || next.y >= map.size || next.x >= map[0].size) {
            break
        }
        if (map[next.y][next.x].isObstacle) {
            guardianDirection = when (guardianDirection) {
                MatrixUtils.SimpleDirection.UP -> MatrixUtils.SimpleDirection.RIGHT
                MatrixUtils.SimpleDirection.RIGHT -> MatrixUtils.SimpleDirection.DOWN
                MatrixUtils.SimpleDirection.DOWN -> MatrixUtils.SimpleDirection.LEFT
                MatrixUtils.SimpleDirection.LEFT -> MatrixUtils.SimpleDirection.UP
            }
        } else {
            guardianPostition = next
            visited.add(guardianPostition)
        }
    } while (true)

    return visited
}

fun part1(): Int {
    return getVisitedTiles().size

}

fun isGuardianStuckInLoop(map: Matrix<Tile>, guardianStart: Coord): Boolean {
    var guardianDirection = MatrixUtils.SimpleDirection.UP
    var guardianPostition = guardianStart
    val visitedWithDir = mutableSetOf(Pair(guardianStart, guardianDirection))
    do {
        val next = guardianPostition.go(guardianDirection)
        if (next.x < 0 || next.y < 0 || next.y >= map.size || next.x >= map[0].size) {
            break
        }
        if (map[next.y][next.x].isObstacle) {
            guardianDirection = when (guardianDirection) {
                MatrixUtils.SimpleDirection.UP -> MatrixUtils.SimpleDirection.RIGHT
                MatrixUtils.SimpleDirection.RIGHT -> MatrixUtils.SimpleDirection.DOWN
                MatrixUtils.SimpleDirection.DOWN -> MatrixUtils.SimpleDirection.LEFT
                MatrixUtils.SimpleDirection.LEFT -> MatrixUtils.SimpleDirection.UP
            }
        } else {
            guardianPostition = next
            if (visitedWithDir.contains(Pair(guardianPostition, guardianDirection))) {
                return true
            }
            visitedWithDir.add(Pair(guardianPostition, guardianDirection))
        }
    } while (true)

    return false
}

fun part2(): Int {
    val map = input().first
    val guardianStartPostition = input().second

    val tilesToCheckWithObstacle = getVisitedTiles() - guardianStartPostition

    var loopCount = 0

    tilesToCheckWithObstacle.parallelStream().forEach { coord: Coord ->
        val adjustedMap = map.createDeepCopy { it.copy() }

        adjustedMap[coord.y][coord.x].isObstacle = true

        if (isGuardianStuckInLoop(adjustedMap, guardianStartPostition)) {
            loopCount++
        }
    }
    return loopCount
}

