package y2022.day12

import Coord
import Coordinated
import Matrix
import MatrixUtils
import MatrixUtils.filterMatrixElement
import MatrixUtils.forEachMatrixElement
import MatrixUtils.move
import java.lang.IllegalStateException


fun main() {
    AoCGenerics.printAndMeasureResults(
        part1 = { part1() },
        part2 = { part2() }
    )
}

data class Field(
    val elevation: Int,
    var seen: Boolean = false,
)

fun input(): Triple<Matrix<Coordinated<Field>>, Coordinated<Field>, Coordinated<Field>> {

    var start: Coordinated<Field>? = null
    var target: Coordinated<Field>? = null

    val field = MatrixUtils.createMatrix(AoCGenerics.getInputLines("/y2022/day12/input.txt")) { y, row ->
        row.mapIndexed { x, char ->
            val currentCoord = Coordinated(
                coord = Coord(x,y),
                data = Field(
                    elevation = when (char) {
                        'S' -> 0
                        'E' -> 25
                        else -> char.code - 97
                    }
                )
            )

            if (char == 'S') start = currentCoord
            if (char == 'E') target = currentCoord

            currentCoord
        }
    }

    if (start == null || target == null) {
        throw IllegalStateException("No start or target found!")
    } else {
        return Triple(field, start!!, target!!)
    }
}

fun getLevel(board: Matrix<Coordinated<Field>>, start:Coordinated<Field>, target: Coordinated<Field>): Int {
    board.forEachMatrixElement { field -> field.data.seen = false }

    var level = 0
    val toBeVisited = ArrayDeque<Coordinated<Field>?>()
    toBeVisited.add(start)
    toBeVisited.add(null)

    start.data.seen = true
    while (toBeVisited.isNotEmpty()) {
        val curField = toBeVisited.removeFirst()

        if (curField == null) {
            level++
            toBeVisited.add(null)
            if (toBeVisited.first() == null) break else continue
        }


        if (curField.coord == target.coord) {
            return level
        }

        val possibleTargetFields = MatrixUtils.SimpleDirection.values()
            .mapNotNull { move -> board.move(curField, move) }

        possibleTargetFields
            .filter { f ->
                f.data.elevation <= curField.data.elevation + 1
            }
            .forEach { usefulField ->
                if (!usefulField.data.seen) {
                    toBeVisited.add(usefulField)
                    usefulField.data.seen = true
                }
            }

    }

    return Int.MAX_VALUE
}


fun part1(): Int {
    val input = input()

    val board = input.first
    val startPosition = input.second
    val target = input.third

    return getLevel(board, startPosition, target)
}


fun part2(): Int {
    val input = input()

    val board = input.first
    val startPositions = input.first.filterMatrixElement { field -> field.data.elevation == 0 }
    val target = input.third

    val distances = startPositions.map { getLevel(board, it,  target) }

    return distances.minOrNull()!!
}

