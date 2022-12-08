package y2022.day02


fun main() {
    AoCGenerics.printAndMeasureResults(
        part1 = { part1() },
        part2 = { part2() }
    )
}

enum class Option {
    ROCK,
    PAPER,
    SCISSOR
}

enum class Result {
    WIN,
    LOSS,
    DRAW
}

data class Match (
    val myChoice: Option,
    val enemyChoice: Option,
    val desiredResult: Result
)

fun String.toOption(): Option {
    return when (this) {
        "A" -> Option.ROCK
        "B" -> Option.PAPER
        "C" -> Option.SCISSOR
        "X" -> Option.ROCK
        "Y" -> Option.PAPER
        "Z" -> Option.SCISSOR
        else -> throw IllegalStateException("Invalid Input")
    }
}

fun String.toResult(): Result {
    return when (this) {
        "X" -> Result.LOSS
        "Y" -> Result.DRAW
        "Z" -> Result.WIN
        else -> throw IllegalStateException("Invalid Input")
    }
}

fun input(): List<Match> {
    return AoCGenerics.getInputLines("/y2022/day02/input.txt").map { line ->
        val inputs = line.split(" ")
        val enemyChoice = inputs[0]
        val myChoice = inputs[1] // For Part 1
        val desiredResult = inputs[1] // For Part 2

        Match(myChoice.toOption(), enemyChoice.toOption(), desiredResult.toResult())
    }
}

fun Match.getScorePart1(): Int {
    var score = 0

    score += when (myChoice) {
        Option.ROCK -> 1
        Option.PAPER -> 2
        Option.SCISSOR -> 3
    }


    // Loss gives no points, so no need to look at it
    when {
        myChoice == enemyChoice -> score += 3 // Draws
        myChoice == Option.ROCK && enemyChoice == Option.SCISSOR -> score += 6
        myChoice == Option.PAPER && enemyChoice == Option.ROCK -> score += 6
        myChoice == Option.SCISSOR && enemyChoice == Option.PAPER -> score += 6
    }

    return score


}

fun Match.getScorePart2(): Int {
    var score = 0

    when (desiredResult) {
        Result.WIN -> score += 6
        Result.DRAW -> score += 3
        Result.LOSS -> {}
    }

    when {
        enemyChoice == Option.ROCK && desiredResult == Result.WIN -> score += 2 // Paper
        enemyChoice == Option.PAPER && desiredResult == Result.WIN -> score += 3 // Scissor
        enemyChoice == Option.SCISSOR && desiredResult == Result.WIN -> score += 1 // Rock
        enemyChoice == Option.ROCK && desiredResult == Result.DRAW -> score += 1 // Rock
        enemyChoice == Option.PAPER && desiredResult == Result.DRAW -> score += 2 // Paper
        enemyChoice == Option.SCISSOR && desiredResult == Result.DRAW -> score += 3 // Scissor
        enemyChoice == Option.ROCK && desiredResult == Result.LOSS -> score += 3 // Scissor
        enemyChoice == Option.PAPER && desiredResult == Result.LOSS -> score += 1 // Rock
        enemyChoice == Option.SCISSOR && desiredResult == Result.LOSS -> score += 2 // Paper
    }

    return score
}

fun part1(): Int {
    return input().sumOf { it.getScorePart1() }
}

fun part2(): Int {
    return input().sumOf { it.getScorePart2() }
}
