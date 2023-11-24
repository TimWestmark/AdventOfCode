package y2016.day02

import AoCGenerics

fun main() {
    AoCGenerics.printAndMeasureResults(
        part1 = { part1() },
        part2 = { part2() }
    )
}

fun input(): List<String> {
    return AoCGenerics.getInputLines("/y2016/day02/input.txt")
}

val SQUARE_FIELD = listOf(
    listOf(1, 2, 3),
    listOf(4, 5, 6),
    listOf(7, 8, 9)
)


val STAR_FIELD  = listOf(
        listOf(null, null, "1", null, null),
        listOf(null, "2",  "3", "4",  null),
        listOf("5",  "6",  "7", "8",  "9"),
        listOf(null, "A",  "B", "C",  null),
        listOf(null, null, "D", null, null),
    )

fun part1(): String {

    val commands = input()

    var x = 1
    var y = 1

    var result = ""
    commands.forEach { command ->
        command.forEach { step ->
            when (step) {
                'U' -> if (y != 0) y -= 1
                'R' -> if (x != 2) x += 1
                'D' -> if (y != 2) y += 1
                'L' -> if (x != 0) x -= 1
            }
        }

        result += SQUARE_FIELD[y][x].toString()
    }

    return result
}


fun part2(): String {
    val commands = input()

    var x = 0
    var y = 2

    var result = ""
    commands.forEach { command ->
        command.forEach { step ->
            when (step) {
                'U' -> if (y != 0 && STAR_FIELD[y-1][x] != null) y -= 1
                'R' -> if (x != 4 && STAR_FIELD[y][x+1] != null) x += 1
                'D' -> if (y != 4 && STAR_FIELD[y+1][x] != null) y += 1
                'L' -> if (x != 0 && STAR_FIELD[y][x-1] != null) x -= 1
            }
        }

        result += STAR_FIELD[y][x].toString()
    }

    return result
}

