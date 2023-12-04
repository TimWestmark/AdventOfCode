package y2023.day04

import kotlin.math.pow

fun main() {
    AoCGenerics.printAndMeasureResults(
        part1 = { part1() },
        part2 = { part2() }
    )
}

data class ScratchCard(
    val winningNumber: Set<Int>,
    val drawnNumbers: Set<Int>,
    var copies: Int,
)

fun input() =
    AoCGenerics.getInputLines("/y2023/day04/input.txt")
        .map {
            val winning = it.split(":")[1].split("|")[0]
            val drawn = it.split(":")[1].split("|")[1]

            ScratchCard(
                copies = 1,
                winningNumber = winning.trim().split(" ").filter { number -> number != "" }.map { number -> number.trim().toInt() }.toSet(),
                drawnNumbers = drawn.trim().split(" ").filter { number -> number != "" }.map { number -> number.trim().toInt() }.toSet()
            )
        }


fun part1() =
    input().sumOf { card ->
        val matches = card.drawnNumbers.intersect(card.winningNumber)
        when {
            matches.isEmpty() -> 0.0
            else -> 2.0.pow((matches.size - 1).toDouble())
        }
    }.toInt()

fun part2(): Int {
    val cards = input()

    cards.forEachIndexed { index, card ->
        val matches = card.winningNumber.intersect(card.drawnNumbers).size
        repeat(matches) {
            if (index + 1 + it < cards.size) {
                cards[index + 1 + it].copies += card.copies
            }
        }
    }

   return cards.sumOf { it.copies }
}
