package y2023.day06

fun main() {
    AoCGenerics.printAndMeasureResults(
        part1 = { part1() },
        part2 = { part2() }
    )
}

data class Race(
    val duration: Int,
    val distance: Long
)

fun input1(): List<Race> {
    val inputLines = AoCGenerics.getInputLines("/y2023/day06/input.txt")
    val timeLine = inputLines[0].split(":")[1].trim()
    val distanceLine = inputLines[1].split(":")[1].trim()
    val times = timeLine.split(" ").filter { it != "" }.map { it.toInt() }
    val distances = distanceLine.split(" ").filter { it != "" }.map { it.toLong() }

    return times.mapIndexed { index, i ->
        Race(
            duration = i,
            distance = distances[index]
        )
    }
}

fun input2(): Race {
    val inputLines = AoCGenerics.getInputLines("/y2023/day06/input.txt")
    val timeLine = inputLines[0].split(":")[1].trim()
    val distanceLine = inputLines[1].split(":")[1].trim()
    val times = timeLine.split(" ").filter { it != "" }
    val distances = distanceLine.split(" ").filter { it != "" }

    return Race(
        duration = times.joinToString("").toInt(),
        distance = distances.joinToString("").toLong()
    )

}

fun part1() =
    input1().map { race ->
        var winPossibilities = 0

        repeat(race.duration) {chargeTime ->
            val way = (race.duration - chargeTime) * chargeTime
            if (way > race.distance) winPossibilities++

        }
        winPossibilities
    }.reduce(Int::times)

fun part2(): Int {
   val race = input2()

    var winPossibilities = 0

    repeat(race.duration) {chargeTime ->
        val way = (race.duration.toLong() - chargeTime) * chargeTime
        if (way > race.distance) winPossibilities++
    }

    return winPossibilities
}
