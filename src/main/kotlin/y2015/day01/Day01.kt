package y2015.day01

fun main() {
    AoCGenerics.printAndMeasureResults(
        part1 = { part1() },
        part2 = { part2() }
    )
}

fun input(): String {
    return AoCGenerics.getInputLines("/y2015/day01/input.txt").first()
}

fun part1(): Int {
    val input = input()
    return input.count { it == '(' } - input.count{ it == ')' }
}


fun part2(): Int {
    val input = input()
    var currentFloor = 0
    var indexWhenEnteringCurrentFloorForTheFirstTime: Int? = null
    input.forEachIndexed { index, char ->
        when (char) {
            '(' -> currentFloor++
            ')' -> currentFloor--
        }
        if (currentFloor == -1 && indexWhenEnteringCurrentFloorForTheFirstTime == null) {
            indexWhenEnteringCurrentFloorForTheFirstTime = index
        }
    }

    return  indexWhenEnteringCurrentFloorForTheFirstTime!! + 1
}
