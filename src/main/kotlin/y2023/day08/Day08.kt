package y2023.day08

import NumberStuff

fun main() {
    AoCGenerics.printAndMeasureResults(
        part1 = { part1() },
        part2 = { part2() }
    )
}

data class Input(
    val instruction: String,
    val maps: Map<String, Pair<String,String>>
)

fun input(): Input {
    val lines =  AoCGenerics.getInputLines("/y2023/day08/input.txt")
    val instruction = lines[0]

    val mapLines = lines.subList(2, lines.size)

    val maps = mapLines.associate {
        val source = it.split("=")[0].trim()
        val destL = it.split("=")[1].trim().split(",")[0].trim().split("(")[1].trim()
        val destR = it.split("=")[1].trim().split(",")[1].trim().split(")")[0].trim()

        source to Pair(destL, destR)
    }
    return Input(instruction, maps)
}

fun part1(): Int {
    val input = input()
    val instruction = input.instruction
    val maps = input.maps

    var currentLocation = "AAA"
    var steps = 0

    while (currentLocation != "ZZZ") {
        val nextInstruction = instruction[steps % instruction.length]

        currentLocation = when {
            nextInstruction == 'L' -> maps[currentLocation]!!.first
            else -> maps[currentLocation]!!.second
        }

        steps++
    }
    return steps
}

fun part2(): Long {
    val input = input()
    val currentPositions = input.maps.keys.filter { it.endsWith("A") }.toMutableList()
    val steps = currentPositions.map { pos -> getSteps(pos, input) }
    return NumberStuff.findLCMOfListOfNumbers(steps)
}

fun getSteps(start: String, input: Input ): Long {
    val instruction = input.instruction
    val maps = input.maps


    var currentLocation = start
    var steps = 0L

    while (!currentLocation.endsWith("Z")) {
        val nextInstruction = instruction[(steps % instruction.length).toInt()]

        currentLocation = when {
            nextInstruction == 'L' -> maps[currentLocation]!!.first
            else -> maps[currentLocation]!!.second
        }

        steps++
    }
    return steps

}
