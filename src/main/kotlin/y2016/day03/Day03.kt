package y2016.day03

fun main() {
    AoCGenerics.printAndMeasureResults(
        part1 = { part1() },
        part2 = { part2() }
    )
}

typealias Triangle = List<Int>

fun input(): List<Triangle> =
    AoCGenerics.getInputLines("/y2016/day03/input.txt")
        .map {
            val parts = it.trim().split("  ").filterNot { partString -> partString == "" }
            Triple(parts[0].trim().toInt(), parts[1].trim().toInt(), parts[2].trim().toInt()).toList().sorted()
        }

fun input2(): List<Triangle> =
    AoCGenerics.getInputLines("/y2016/day03/input.txt").chunked(3)
        .map { threeLines ->
            val line1 = threeLines[0].trim().split("  ").filterNot { partString -> partString == "" }
            val line2 = threeLines[1].trim().split("  ").filterNot { partString -> partString == "" }
            val line3 = threeLines[2].trim().split("  ").filterNot { partString -> partString == "" }

            listOf(
                Triple(line1[0].trim().toInt(), line2[0].trim().toInt(), line3[0].trim().toInt()).toList().sorted(),
                Triple(line1[1].trim().toInt(), line2[1].trim().toInt(), line3[1].trim().toInt()).toList().sorted(),
                Triple(line1[2].trim().toInt(), line2[2].trim().toInt(), line3[2].trim().toInt()).toList().sorted(),
            )
        }.flatten()

fun List<Triangle>.valid() = this.count {
    it[0] + it[1] > it[2]
}

fun part1() = input().valid()
fun part2() = input2().valid()
