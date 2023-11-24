package y2016.day01


import java.lang.IllegalStateException
import kotlin.math.abs

fun main() {
    AoCGenerics.printAndMeasureResults(
        part1 = { part1() },
        part2 = { part2() }
    )
}

data class Instructions(
    val turnDirection: TurnDirection,
    val distance: Int
)

enum class TurnDirection {
    LEFT,
    RIGHT
}

enum class Direction {
    NORTH,
    EAST,
    SOUTH,
    WEST
}

fun input(): List<Instructions> {
    return AoCGenerics.getInputLines("/y2016/day01/input.txt").first()
        .split(",")
        .map {
            it.trim()
        }
        .map { Instructions(
            turnDirection =  when {
                it.first() == 'L' -> TurnDirection.LEFT
                it.first() == 'R' -> TurnDirection.RIGHT
                else -> throw IllegalStateException()
            },
            distance = it.substring(1).toInt()
        )
        }
}

fun part1(): Int {
    var facing = Direction.NORTH
    var distanceX = 0
    var distanceY = 0

    input().forEach{instruction ->
        facing = changeDirection(facing, instruction.turnDirection)

        when (facing) {
            Direction.NORTH -> distanceY += instruction.distance
            Direction.EAST ->  distanceX += instruction.distance
            Direction.SOUTH -> distanceY -= instruction.distance
            Direction.WEST ->  distanceX -= instruction.distance
        }
    }

    return abs(distanceX) + abs(distanceY)
}

fun part2(): Int {

    var facing = Direction.NORTH
    var distanceX = 0
    var distanceY = 0

    val visitedLocation = mutableSetOf("0,0")

    input().forEach{instruction ->

        facing = changeDirection(facing, instruction.turnDirection)

        when (facing) {
            Direction.NORTH -> {
                repeat(instruction.distance) {
                    if (visitedLocation.contains("$distanceX,${distanceY + 1}")) {
                        return abs(distanceX) + abs(distanceY + 1)
                    } else {
                        distanceY += 1
                        visitedLocation.add("$distanceX,$distanceY")
                    }
                }
            }
            Direction.EAST ->  {
                repeat(instruction.distance) {
                    if (visitedLocation.contains("${distanceX+1},$distanceY")) {
                        return abs(distanceX+1) + abs(distanceY)
                    } else {
                        distanceX += 1
                        visitedLocation.add("$distanceX,$distanceY")
                    }
                }
            }
            Direction.SOUTH -> {
                repeat(instruction.distance) {
                    if (visitedLocation.contains("$distanceX,${distanceY - 1}")) {
                        return abs(distanceX) + abs(distanceY - 1)
                    } else {
                        distanceY -= 1
                        visitedLocation.add("$distanceX,$distanceY")
                    }
                }
            }
            Direction.WEST -> {
                repeat(instruction.distance) {
                    if (visitedLocation.contains("${distanceX-1},$distanceY")) {
                        return abs(distanceX-1) + abs(distanceY)
                    } else {
                        distanceX -= 1
                        visitedLocation.add("$distanceX,$distanceY")
                    }
                }
            }
        }
    }

    return abs(distanceX) + abs(distanceY)
}

fun changeDirection(curDirection: Direction, turn: TurnDirection): Direction =
    when (curDirection) {
        Direction.NORTH -> if (turn == TurnDirection.LEFT) Direction.WEST else Direction.EAST
        Direction.EAST -> if (turn == TurnDirection.LEFT) Direction.NORTH else Direction.SOUTH
        Direction.SOUTH -> if (turn == TurnDirection.LEFT) Direction.EAST else Direction.WEST
        Direction.WEST -> if (turn == TurnDirection.LEFT) Direction.SOUTH else Direction.NORTH
    }

