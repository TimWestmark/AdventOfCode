package y2023.day03

import Coord
import Coordinated
import Matrix
import MatrixUtils
import MatrixUtils.filterMatrixElement
import MatrixUtils.move

fun main() {
    AoCGenerics.printAndMeasureResults(
        part1 = { part1() },
        part2 = { part2() }
    )
}

data class Field(
    val digit: Int?,
    val isPartNumberIndicator: Boolean,
    val isPotentialGear: Boolean,
    var counted: Boolean = false
)

fun input(): Matrix<Coordinated<Field>> =
    MatrixUtils.createMatrix(AoCGenerics.getInputLines("/y2023/day03/input.txt")) { y, row ->
        row.mapIndexed { x, char ->
            Coordinated(
                coord = Coord(x, y),
                data = Field(
                    digit = if (char.isDigit()) char.digitToInt() else null,
                    isPartNumberIndicator = !char.isDigit() && char != '.',
                    isPotentialGear = char == '*'
                )
            )
        }
    }

fun getNumberFromDigitAndMarkCounted(board: Matrix<Coordinated<Field>>, digitField: Coordinated<Field>): Int {
    var numberString = ""

    // search digits to the left
    var curField: Coordinated<Field>? = digitField
    while (curField?.data?.digit != null && !curField.data.counted) {
        numberString = "${curField.data.digit}$numberString"
        curField.data.counted = true
        curField = board.move(curField, MatrixUtils.SimpleDirection.LEFT)
    }

    // search digits to the right
    curField = board.move(digitField, MatrixUtils.SimpleDirection.RIGHT)
    while (curField?.data?.digit != null && !curField.data.counted) {
        numberString = "$numberString${curField.data.digit}"
        curField.data.counted = true
        curField = board.move(curField, MatrixUtils.SimpleDirection.RIGHT)
    }

    return if (numberString == "") 0 else numberString.toInt()
}

fun part1(): Int {
    val board = input()

    return board.filterMatrixElement { it.data.isPartNumberIndicator }
        .map { symbolField ->
            val adjacentFields = MatrixUtils.AdvancedDirection.values().mapNotNull { direction ->
                board.move(symbolField, direction)
            }
            adjacentFields.filter { it.data.digit != null }
        }
        .flatten()
        .sumOf {
            getNumberFromDigitAndMarkCounted(board, it)
        }
}

fun part2(): Int {
    val input = input()
    return input.filterMatrixElement { it.data.isPotentialGear }
        .sumOf { potentialGear ->
            val adjacentNumbers = MatrixUtils.AdvancedDirection.values()
                .mapNotNull { input.move(potentialGear, it) } // All adjacent fields
                .filter { it.data.digit != null } // All adjacent digit fields
                .map { getNumberFromDigitAndMarkCounted(input, it) } // All adjacent numbers, including 0 for duplicates
                .filter { it != 0 } // just adjacent numbers

            if (adjacentNumbers.size == 2) {
                adjacentNumbers[0] * adjacentNumbers[1]
            } else 0
        }
}
