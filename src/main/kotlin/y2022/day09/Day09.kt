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


fun List<List<Field>>.print(head: Field, tail: Field, start: Field) {
    this.forEach { line ->
        line.forEach { cell ->

            when {
                cell.x == head.x && cell.y == head.y -> print("H")
                cell.x == tail.x && cell.y == tail.y -> print("T")
                cell.x == start.x && cell.y == start.y -> print("s")
                else -> print(".")
            }
        }

        println()
    }
}

fun List<List<Field>>.print2(head: Field, tails: List<Field>, start: Field) {
    this.forEach { line ->
        line.forEach { cell ->

            when {
                cell.x == head.x && cell.y == head.y -> print("H")
                tails.indexOf(cell) > -1 -> print(tails.indexOf(cell)+1)
                cell.x == start.x && cell.y == start.y -> print("s")
                else -> print(".")
            }
        }

        println()
    }
}

fun part1(): Int {

    val commands = input()


    val field = IntRange(0, 1000).map { y ->
        IntRange(0, 1000)
            .map { x ->
                Field(false, x, y)

            }
    }


    val start = field[500][500]
    start.visitedByTail = true
    var head = start
    var tail = start

    commands.forEach { command ->

//        println("${command.direction} ${command.steps}")
        repeat(command.steps) {

            // Move the Head
            head = when (command.direction) {
                Direction.LEFT -> field[head.y][head.x - 1]
                Direction.RIGHT -> field[head.y][head.x + 1]
                Direction.UP -> field[head.y - 1][head.x]
                Direction.DOWN -> field[head.y + 1][head.x]
            }

            when {
                // Move the tail horizontally
                tail.y == head.y && abs(tail.x - head.x) > 1 -> tail = field[tail.y][(tail.x + head.x) / 2]
                // Move the tail vertically
                tail.x == head.x && abs(tail.y - head.y) > 1 -> tail = field[(tail.y + head.y) / 2][tail.x]

                // move diagonally
                (abs(tail.y - head.y) > 1 || abs(tail.x - head.x) > 1) -> {
                    when (command.direction) {
                        Direction.UP, Direction.DOWN -> tail = field[(tail.y + head.y) / 2][head.x]
                        Direction.LEFT, Direction.RIGHT -> tail =  field[head.y][(tail.x + head.x) / 2]
                    }
                }
            }



            tail.visitedByTail = true
//            field.print(head, tail, start)
//            println("")
//            println("")
        }
    }

    return field.flatten().count { it.visitedByTail }
}


fun part2(): Int {

    val commands = input()


    val field = IntRange(0, 1000).map { y ->
        IntRange(0, 1000)
            .map { x ->
                Field(false, x, y)

            }
    }


    val start = field[500][500]
    start.visitedByTail = true
    var head = start
    val tails: MutableList<Field> = mutableListOf(
        start,
        start,
        start,
        start,
        start,
        start,
        start,
        start,
        start,
    )

    commands.forEach { command ->

        println("${command.direction} ${command.steps}")
        repeat(command.steps) {

            // Move the Head
            head = when (command.direction) {
                Direction.LEFT -> field[head.y][head.x - 1]
                Direction.RIGHT -> field[head.y][head.x + 1]
                Direction.UP -> field[head.y - 1][head.x]
                Direction.DOWN -> field[head.y + 1][head.x]
            }

            var previousHead = head

            tails.forEachIndexed { index, tail ->
                tails[index] = getNewTailCoord(previousHead, tail, command.direction, field)
                previousHead = tails[index]
            }



            tails.last().visitedByTail = true
//            field.print2(head, tails, start)
//            println("")
//            println("")
        }
    }

    return field.flatten().count { it.visitedByTail }
}


fun getNewTailCoord(head:Field, tail: Field, direction: Direction, field:List<List<Field>>): Field {
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

