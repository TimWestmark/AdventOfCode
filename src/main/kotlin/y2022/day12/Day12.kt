package y2022.day12


fun main() {
    AoCGenerics.printAndMeasureResults(
        part1 = { part1() },
        part2 = { part2() }
    )
}

data class Coord(
    val x: Int,
    val y: Int,
)

fun Coord.left() = Coord(x - 1, y)
fun Coord.right() = Coord(x + 1, y)
fun Coord.up() = Coord(x, y + 1)
fun Coord.down() = Coord(x, y - 1)

fun Coord.go(move: Move) = when (move) {
    Move.UP -> this.up()
    Move.DOWN -> this.down()
    Move.LEFT -> this.left()
    Move.RIGHT -> this.right()
}

data class Field(
    val coord: Coord,
    val elevation: Int,
    var seen: Boolean = false,
    var seenAfter: Int = Int.MAX_VALUE
)

enum class Move {
    UP,
    DOWN,
    LEFT,
    RIGHT
}

fun input(): Triple<List<Field>, Field, Field> {

    val fields: MutableList<Field> = mutableListOf()
    var start: Field? = null
    var target: Field? = null
    AoCGenerics.getInputLines("/y2022/day12/input.txt").forEachIndexed { y, row ->
        row.forEachIndexed { x, char ->
            val field = Field(
                Coord(
                    x = x,
                    y = y
                ),
                elevation = when (char) {
                    'S' -> 0
                    'E' -> 25
                    else -> char.code - 97
                }
            )
            fields.add(field)
            if (char == 'S') start = field
            if (char == 'E') target = field


        }
    }

    return Triple(fields, start!!, target!!)

}

fun List<Field>.getField(coord: Coord): Field? {
    return this.firstOrNull { it.coord == coord }
}

fun getLevel(board: List<Field>, start:Field, target: Field): Int {
    board.forEach { field -> field.seen = false }
    var level = 0
    val toBeVisited = ArrayDeque<Field?>()
    toBeVisited.add(start)
    toBeVisited.add(null)

    start.seen = true
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

        val possibleTargetFields = Move.values()
            .mapNotNull { move -> board.getField(curField.coord.go(move)) }

        possibleTargetFields
            .filter { f ->
                f.elevation <= curField.elevation + 1
            }
            .forEach { usefulField ->
                if (!usefulField.seen) {
                    toBeVisited.add(usefulField)
                    usefulField.seen = true
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
    val startPositions = input.first.filter { field -> field.elevation == 0 }
    val target = input.third

    val distances = startPositions.map { getLevel(board, it,  target) }

    return distances.min()
}

