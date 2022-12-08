package y2022.day04


fun main() {
    AoCGenerics.printAndMeasureResults(
        part1 = { part1() },
        part2 = { part2() }
    )
}

fun input(): List<Pair<IntRange, IntRange>> {
    return AoCGenerics.getInputLines("/y2022/day04/input.txt").map { line ->
        Pair(
            IntRange(
                line.split(",")[0].split("-")[0].toInt(),
                line.split(",")[0].split("-")[1].toInt()
            ),
            IntRange(
                line.split(",")[1].split("-")[0].toInt(),
                line.split(",")[1].split("-")[1].toInt()
            )
        )
    }
}
fun part1(): Int {
    return input().map { elvesRanges ->
        val firstRange = elvesRanges.first.toSet()
        val secondRange = elvesRanges.second.toSet()

        val intersection = firstRange.intersect(secondRange)
        intersection.size == firstRange.size || intersection.size == secondRange.size

    }.count { it }
}


fun part2(): Int {
    return input().map { elvesRanges ->
        val firstRange = elvesRanges.first.toSet()
        val secondRange = elvesRanges.second.toSet()

        val intersection = firstRange.intersect(secondRange)

        intersection.isNotEmpty()
    }.count { it }
}