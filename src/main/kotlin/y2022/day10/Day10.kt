package y2022.day10

import kotlin.math.abs

fun main() {
    AoCGenerics.printAndMeasureResults(
        part1 = { part1() },
        part2 = { part2() }
    )
}

fun input(): List<String> {
    return AoCGenerics.getInputLines("/y2022/day10/input.txt")
}

fun getCycleValues(commands: List<String>): Map<Int, Int> {

    var x = 1
    val commandIterator = commands.iterator()
    var needsToWait = false
    var command = ""
    val valueByCycle: MutableMap<Int, Int> = mutableMapOf()

    for (cycle in 1..240) {

        valueByCycle[cycle] = x
        if (!needsToWait) {
            if (!commandIterator.hasNext()) {
                break
            }
            command = commandIterator.next()

            when (command) {
                "noop" -> {
                    needsToWait = false
                    continue
                }
                else -> {
                    needsToWait = true
                    continue
                }
            }
        }

        x += command.split(" ")[1].toInt()
        needsToWait = false
    }

    return valueByCycle
}

fun part1(): Int {


   val cycleValues = getCycleValues(input())

    val interestingCylcles = listOf(20,60,100,140,180,220)

    return interestingCylcles.sumOf { cycle ->
        cycle * cycleValues[cycle]!!
    }


}


fun part2(): Int {
    val cyclesValues = getCycleValues(input())

    for (row in 0 until 6) {
        for (charPosition in 1..40) {
            val cycleNumber = row * 40 + charPosition

            val x = cyclesValues[cycleNumber]!!
            val spritePosition = x + 1
            when {
                abs(charPosition - spritePosition) <= 1 -> print("#")
                else -> print(" ") // we use spaces instead of dots to prevent cluttering of the output with dots
            }

            // just for better visualization of the result letters
            if (charPosition % 5 == 0) {
                print("     ")
            }
        }
        println()
    }

    return 0 // Actual result in the console output (ZKGRKGRK)
}

