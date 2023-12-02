package y2023.day02

fun main() {
    AoCGenerics.printAndMeasureResults(
        part1 = { part1() },
        part2 = { part2() }
    )
}

data class Game(
    val id: Int,
    var red: Int = 0,
    var blue: Int = 0,
    var green: Int = 0
)

fun input(): List<Game> {
    return AoCGenerics.getInputLines("/y2023/day02/input.txt").map { line ->
        val game = Game(
            id = line.split(":")[0].split(" ")[1].toInt()
        )

        val gameRecords = line.split(":")[1].trim()

        // Split the sets and then the draws. Doesn't hurt to mix sets as we only search for the highest of each color
        val draws = gameRecords.split(";").map { it.trim().split(",") }.flatten()
        draws.forEach { draw ->
            val drawCount = draw.trim().split(" ")[0].trim().toInt()
            val drawColor = draw.trim().split(" ")[1].trim()
            when (drawColor) {
                "blue" -> game.blue = maxOf(game.blue, drawCount)
                "red" -> game.red = maxOf(game.red, drawCount)
                "green" -> game.green = maxOf(game.green, drawCount)
            }
        }
        game
    }
}

fun Game.valid(): Boolean {
    return this.blue <= 14 && this.green <= 13 && this.red <= 12
}

fun part1(): Int {
    return input().sumOf {
        if (it.valid()) it.id else 0
    }
}

fun part2() =
    input().sumOf {
        it.green * it.red * it.blue
    }
