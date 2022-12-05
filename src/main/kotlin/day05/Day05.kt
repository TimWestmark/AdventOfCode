package day05

import getInputLines
import kotlin.system.measureTimeMillis

fun main() {
    val timeInMillisPart1 = measureTimeMillis {
        println("Solution Part 1: ${part1()}")
    }

    val timeInMillisPart2 = measureTimeMillis {
        println("Solution Part 2: ${part2()}")
    }

    println("(The part1 operation took $timeInMillisPart1 ms)")
    println("(The part2 operation took $timeInMillisPart2 ms)")


}

data class Instruction (
    val numberOfCreates: Int,
    val from: Int,
    val to: Int
)

fun input(): Pair<Map<Int, ArrayDeque<Char>>, List<Instruction>> {

    val allTheLines = getInputLines("/day05/input.txt")
    val stacks: Map<Int, ArrayDeque<Char>> = mutableMapOf(
        1 to ArrayDeque(),
        2 to ArrayDeque(),
        3 to ArrayDeque(),
        4 to ArrayDeque(),
        5 to ArrayDeque(),
        6 to ArrayDeque(),
        7 to ArrayDeque(),
        8 to ArrayDeque(),
        9 to ArrayDeque()
    )

    allTheLines.subList(0,8).forEach { line ->

        val lineElements = line.chunked(4)
        lineElements.forEachIndexed { index, content ->
            if (content.startsWith("[")) {
                stacks[index+1]!!.addFirst(content[1])
            }
        }




    }

    val instructions = allTheLines.subList(10, allTheLines.size).map {line ->
        val lineParts = line.split(" ")
        Instruction(
            lineParts[1].toInt(),
            lineParts[3].toInt(),
            lineParts[5].toInt(),
        )
    }


    return Pair(stacks, instructions)
}
fun part1(): String {
    val input = input()
    val stacks = input.first

    val instructions = input.second

    instructions.forEach { instruction ->
        repeat(instruction.numberOfCreates) {
            val removedElement = stacks[instruction.from]!!.last()
            stacks[instruction.from]!!.removeLast()
            stacks[instruction.to]!!.addLast(removedElement)
        }

    }

    return stacks.map { entry ->
        entry.value.last()
    }.joinToString("")
}


fun part2(): String {
    val input = input()
    val stacks = input.first

    val instructions = input.second

    instructions.forEach { instruction ->

        val removedElements: MutableList<Char> = mutableListOf()
        repeat(instruction.numberOfCreates) {
            removedElements.add(stacks[instruction.from]!!.last())
            stacks[instruction.from]!!.removeLast()
        }
        removedElements.reversed().forEach {element ->
            stacks[instruction.to]!!.addLast(element)
        }

    }

    return stacks.map { entry ->
        entry.value.last()
    }.joinToString("")
}