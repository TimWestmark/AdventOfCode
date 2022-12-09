package y2022.day09

import java.lang.IllegalStateException
import kotlin.math.abs

fun main() {
    AoCGenerics.printAndMeasureResults(
        part1 = { part1() },
        part2 = { part2() }
    )
}

enum class Direction {
    LEFT,
    RIGHT,
    UP,
    DOWN
}

data class Move(
    val direction: Direction,
    val steps: Int
)

fun input(): List<Move> {
    return AoCGenerics.getInputLines("/y2022/day09/input.txt").map { line ->
        Move(
            direction = when (line.split(" ")[0]) {
                "U" -> Direction.UP
                "D" -> Direction.DOWN
                "L" -> Direction.LEFT
                "R" -> Direction.RIGHT
                else -> throw IllegalStateException("Invalid Move")
            },
            steps = line.split(" ")[1].toInt()
        )
    }
}

data class Field(
    var visitedByTail: Boolean = false,
    val x: Int,
    val y: Int
)

fun initField(size:Int): List<List<Field>> {
    return  IntRange(0, size).map { y ->
        IntRange(0, size)
            .map { x ->
                Field(false, x, y)

            }
    }
}

fun moveHead(head: Field, direction: Direction, field: List<List<Field>>): Field {
    return when (direction) {
        Direction.LEFT -> field[head.y][head.x - 1]
        Direction.RIGHT -> field[head.y][head.x + 1]
        Direction.UP -> field[head.y - 1][head.x]
        Direction.DOWN -> field[head.y + 1][head.x]
    }
}

fun moveTail(head:Field, tail: Field, field:List<List<Field>>): Field {
    return when {
        // Move the tail horizontally
        tail.y == head.y && abs(tail.x - head.x) > 1 -> field[tail.y][(tail.x + head.x) / 2]
        // Move the tail vertically
        tail.x == head.x && abs(tail.y - head.y) > 1 -> field[(tail.y + head.y) / 2][tail.x]

        // move diagonally
        abs(tail.y - head.y) > 1 && abs(tail.x - head.x) > 1 -> field[(tail.y + head.y) / 2][(tail.x + head.x) / 2]
        abs(tail.y - head.y) > 1 -> field[(tail.y + head.y) / 2][head.x]
        abs(tail.x - head.x) > 1 -> field[head.y][(tail.x + head.x) / 2]
        // Do not move
        else -> return tail
    }
}

fun part1(): Int {

    val commands = input()
    val fieldSize = 1000

    val field = initField(fieldSize)
    val start = field[fieldSize/2][fieldSize/2]

    start.visitedByTail = true
    var head = start
    var tail = start

    commands.forEach { command ->
        repeat(command.steps) {
            // Move the Head
            head = moveHead(head, command.direction, field)
            // Move the Tail
            tail = moveTail(head, tail, field)

            tail.visitedByTail = true
        }
    }

    return field.flatten().count { it.visitedByTail }
}

fun part2(): Int {

    val commands = input()
    val fieldSize = 1000

    val field = initField(fieldSize)
    val start = field[fieldSize/2][fieldSize/2]

    start.visitedByTail = true
    var head = start

    val tails: MutableList<Field> = MutableList(9) { start}

    commands.forEach { command ->
        repeat(command.steps) {
            // Move the Head
            head = moveHead(head, command.direction, field)
            var previousHead = head

            // Move the Tails
            tails.forEachIndexed { index, tail ->
                tails[index] = moveTail(previousHead, tail, field)
                previousHead = tails[index]
            }

            tails.last().visitedByTail = true
        }
    }

    return field.flatten().count { it.visitedByTail }
}


