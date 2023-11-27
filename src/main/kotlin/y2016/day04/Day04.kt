package y2016.day04

fun main() {
    AoCGenerics.printAndMeasureResults(
        part1 = { part1() },
        part2 = { part2() }
    )
}

fun input(): Rooms {
    return AoCGenerics.getInputLines("/y2016/day04/input.txt")
        .map {
            Room(
                checkSum = it.split("[")[1].dropLast(1),
                id = it.split("[")[0].split("-").last().toInt(),
                name = it.split("[")[0].split("-").dropLast(1).joinToString("")
            )
        }
}

typealias Rooms = List<Room>

data class Room(
    val name: String,
    val id: Int,
    val checkSum: String
)


fun Room.valid(): Boolean {

    val charCountMap = sortedMapOf<Char, Int>()

    this.name.forEach {
        if (charCountMap.containsKey(it)) {
            charCountMap[it] = charCountMap[it]?.plus(1)
        } else {
            charCountMap[it] = 1
        }
    }

    val result = mutableListOf<Char>()

    while (result.size < 5) {
        val maxOccurrences = charCountMap.maxByOrNull { it.value }!!.value
        val allWithMax = charCountMap.filter { it.value == maxOccurrences }.keys.sorted()
        result.addAll(allWithMax)
        allWithMax.forEach { charCountMap.remove(it) }
    }

    val calculatedCheck = result.take(5).joinToString("")

    return calculatedCheck == this.checkSum
}

const val alphabet = "abcdefghijklmnopqrstuvwxyz"

fun Room.decrypt(): String = this.name.map { char ->
    alphabet[(alphabet.indexOf(char) + this.id) % 26]
}.joinToString("")


fun part1(): Int {
    return input().filter { it.valid() }.sumOf { it.id }
}

fun part2(): Int {
    val room = input()
        .filter { it.valid() }
        .first { it.decrypt().contains("northpole") }

    return room.id
}

