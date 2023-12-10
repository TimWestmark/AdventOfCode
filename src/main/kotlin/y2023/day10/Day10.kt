package y2023.day10

import Coord
import Coordinated
import Matrix
import MatrixUtils
import MatrixUtils.filterMatrixElement
import MatrixUtils.move
import MatrixUtils.SimpleDirection

fun main() {
    AoCGenerics.printAndMeasureResults(
        part1 = { part1() },
        part2 = { part2() }
    )
}

data class Field(
    var raw: String,
    val isStart: Boolean,
    val isPipe: Boolean,
    var isOnLoop: Boolean = false,
    val connections: MutableList<Connection>,
    var distanceFromStart: Int = Int.MAX_VALUE
)

typealias Connection =  SimpleDirection

fun getConnections(char: Char): MutableList<Connection> {
    return when (char) {
        '|' -> mutableListOf(SimpleDirection.UP, SimpleDirection.DOWN)
        '-' -> mutableListOf(SimpleDirection.LEFT, SimpleDirection.RIGHT)
        'L' -> mutableListOf(SimpleDirection.UP, SimpleDirection.RIGHT)
        'J' -> mutableListOf(SimpleDirection.UP, SimpleDirection.LEFT)
        '7' -> mutableListOf(SimpleDirection.LEFT, SimpleDirection.DOWN)
        'F' -> mutableListOf(SimpleDirection.RIGHT, SimpleDirection.DOWN)
        else -> mutableListOf()
    }
}

fun input(): Matrix<Coordinated<Field>> {
    return MatrixUtils.createMatrix(AoCGenerics.getInputLines("/y2023/day10/input.txt")) { y, row ->
        row.mapIndexed { x, char ->
            val isPipe = char == 'S' || char != '.'
            Coordinated(
                coord = Coord(x, y),
                data = Field(
                    raw = char.toString(),
                    isStart = char == 'S',
                    isPipe = isPipe,
                    connections = if (isPipe) getConnections(char) else mutableListOf()
                )
            )
        }
    }
}


fun updateStartConnections(start: Coordinated<Field>, map: Matrix<Coordinated<Field>> ) {
    val startConnections = mutableListOf<Connection>()
    if (map.move(start, SimpleDirection.UP)?.data?.isPipe == true && map.move(start, SimpleDirection.UP)?.data!!.connections.contains(SimpleDirection.DOWN)) startConnections.add(SimpleDirection.UP)
    if (map.move(start, SimpleDirection.DOWN)?.data?.isPipe == true && map.move(start, SimpleDirection.DOWN)?.data!!.connections.contains(SimpleDirection.UP)) startConnections.add(SimpleDirection.DOWN)
    if (map.move(start, SimpleDirection.LEFT)?.data?.isPipe == true && map.move(start, SimpleDirection.LEFT)?.data!!.connections.contains(SimpleDirection.RIGHT)) startConnections.add(SimpleDirection.LEFT)
    if (map.move(start, SimpleDirection.RIGHT)?.data?.isPipe == true && map.move(start, SimpleDirection.RIGHT)?.data!!.connections.contains(SimpleDirection.LEFT)) startConnections.add(SimpleDirection.RIGHT)
    start.data.connections.addAll(startConnections)
    updateStartRaw(start, startConnections)
}

fun updateStartRaw(start: Coordinated<Field>, startConnections: MutableList<Connection>) {
    start.data.raw = when {
          startConnections.containsAll(listOf(SimpleDirection.UP, SimpleDirection.DOWN)) -> '|'.toString()
          startConnections.containsAll(listOf(SimpleDirection.LEFT, SimpleDirection.RIGHT)) -> '-'.toString()
          startConnections.containsAll(listOf(SimpleDirection.UP, SimpleDirection.RIGHT)) -> 'L'.toString()
          startConnections.containsAll(listOf(SimpleDirection.UP, SimpleDirection.LEFT)) -> 'J'.toString()
          startConnections.containsAll(listOf(SimpleDirection.LEFT, SimpleDirection.DOWN)) -> '7'.toString()
          else -> 'F'.toString()
    }
}

fun part1(): Int {
    val map = input()
    val start = map.filterMatrixElement { it.data.isStart }.first()
    updateStartConnections(start, map)

    start.data.connections.forEach { direction ->
        var currentField = start
        var distance = 1
        var nextDirection = direction
        do {
            currentField = map.move(currentField, nextDirection)!!

            currentField.data.isOnLoop = true
            currentField.data.distanceFromStart = minOf(currentField.data.distanceFromStart, distance)

            nextDirection = when (nextDirection) {
                SimpleDirection.UP -> currentField.data.connections.first { it != SimpleDirection.DOWN }
                SimpleDirection.DOWN -> currentField.data.connections.first { it != SimpleDirection.UP }
                SimpleDirection.LEFT -> currentField.data.connections.first { it != SimpleDirection.RIGHT }
                SimpleDirection.RIGHT -> currentField.data.connections.first { it != SimpleDirection.LEFT }
            }
            distance++
        } while (currentField.coord != start.coord)
    }

    return map.filterMatrixElement { it.data.isOnLoop }
        .filter { !it.data.isStart }
        .maxOf { it.data.distanceFromStart }
    
}


fun markOnLoop(start: Coordinated<Field>, map: Matrix<Coordinated<Field>>) {
    var currentField = start
    var distance = 1
    var nextDirection = start.data.connections.first()
    do {
        currentField = map.move(currentField, nextDirection)!!

        currentField.data.isOnLoop = true

        nextDirection = when (nextDirection) {
            SimpleDirection.UP -> currentField.data.connections.first { it != SimpleDirection.DOWN }
            SimpleDirection.DOWN -> currentField.data.connections.first { it != SimpleDirection.UP }
            SimpleDirection.LEFT -> currentField.data.connections.first { it != SimpleDirection.RIGHT }
            SimpleDirection.RIGHT -> currentField.data.connections.first { it != SimpleDirection.LEFT }
        }

        distance++
    } while (currentField.coord != start.coord)
}

fun part2(): Int {
    val map = input()

    val start = map.filterMatrixElement { it.data.isStart }.first()
    updateStartConnections(start, map)
    markOnLoop(start, map)
    val dots = map.filterMatrixElement { !it.data.isOnLoop }
    return dots.filter { isEnclosed(it, map) }.size
}

fun getAllLoopPartsInDirection(direction: SimpleDirection, map: Matrix<Coordinated<Field>>, start: Coordinated<Field>): List<Coordinated<Field>> {
    val loopParts = mutableListOf<Coordinated<Field>>()

    var currentField: Coordinated<Field>? = start

    while (true) {
        currentField = map.move(currentField!!, direction)
        if (currentField == null) break

        if (currentField.data.isOnLoop) loopParts.add(currentField)
    }

    return loopParts
}

fun isEnclosed(
    dot: Coordinated<Field>,
    map: List<List<Coordinated<Field>>>
): Boolean {
    // A point is enclosed in a shape, if it crosses the boundary an odd number of times in any direction
    val loopPartsInDirection = getAllLoopPartsInDirection(SimpleDirection.LEFT, map, dot)
    val relevantPipeParts = loopPartsInDirection.filter { "|LJ".contains(it.data.raw) }
    return relevantPipeParts.isNotEmpty() && relevantPipeParts.size % 2 == 1
}
