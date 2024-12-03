package y2024.day03

fun main() {
    AoCGenerics.printAndMeasureResults(
        part1 = { part1() },
        part2 = { part2() }
    )
}

fun input(): List<String> {
    return AoCGenerics.getInputLines("/y2024/day03/input.txt")
}

fun calculateSum(operations: List<String>): Int {
    return operations.sumOf { line ->
        val numb1 = line.split("(")[1].split(",")[0].toInt()
        val numb2 = line.split(",")[1].split(")")[0].toInt()
        numb2 * numb1
    }
}

fun part1(): Int {
    val input = input()
    val matches = mutableListOf<String>()
    val regex = Regex("mul\\([0-9]{1,3},[0-9]{1,3}\\)")
    input.forEach { line ->
        matches.addAll(regex.findAll(line).map { it.value })
    }
    return calculateSum(matches)
}

fun part2(): Int {
    val input = input()
    val matches = mutableListOf<String>()
    val regex = Regex("(mul\\([0-9]{1,3},[0-9]{1,3}\\))|don't\\(\\)|do\\(\\)")

    input.forEach { line ->
        matches.addAll(regex.findAll(line).map { it.value })
    }

    val cleanedList = mutableListOf<String>()
    var mulEnabled = true

    matches.forEach {
        when (it) {
            "do()" -> mulEnabled = true
            "don't()" -> mulEnabled = false
            else -> if (mulEnabled) cleanedList.add(it)
        }
    }
    return calculateSum(cleanedList)
}
