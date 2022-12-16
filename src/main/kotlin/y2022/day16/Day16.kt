package y2022.day16

import AoCGenerics
import kotlin.math.max


fun main() {
    AoCGenerics.printAndMeasureResults(
        part1 = { part1() },
        part2 = { part2() }
    )
}

data class Valve(
    val flowRate: Int,
    val name: String,
    val connectedValve: List<String>,
    val distanceToNodes: MutableMap<String, Int> = mutableMapOf(),
    var seen: Boolean = false
)

fun input(): List<Valve> {
    return AoCGenerics.getInputLines("/y2022/day16/input.txt").map { line ->
        val inputParts = line.split(" ")
        val valveName = inputParts[1]
        val flowRate = inputParts[4].split("=")[1].split(";")[0].toInt()
        val valveNames = inputParts.subList(9,inputParts.size).map { it.replace(",","") }

        Valve(
            flowRate,
            valveName,
            valveNames
        )
    }
}

fun calculateDistanceFromImportantNodesToImportantNodes(importantNodes: List<Valve>, allNodes: List<Valve>) {
    importantNodes.forEach {
        var distance = 1
        val toBeVisited = ArrayDeque<Valve?>()
        allNodes.forEach { node -> node.seen = false }
        it.seen = true
        toBeVisited.add(it)
        toBeVisited.add(null)

        while (toBeVisited.isNotEmpty()) {
            val element = toBeVisited.removeFirst()

            if (element == null) {
                distance++
                toBeVisited.add(null)
                if (toBeVisited.first() == null) break else continue
            }

            val nextValves = element.connectedValve.map { nextValve -> allNodes.find { node -> node.name == nextValve }!! }
            nextValves.forEach { nextValve  ->
                if (!nextValve.seen) {
                    toBeVisited.add(nextValve)
                    nextValve.seen = true
                    if (nextValve.flowRate > 0) {
                        it.distanceToNodes[nextValve.name] = distance
                    }
                }
            }
        }

    }

}


fun value(timeRemaining: Int, distance: Int, flowRate: Int): Int {
    return if ((timeRemaining-distance-1) > 0) {
        flowRate * (timeRemaining-distance-1)
    } else {
        0
    }


}

fun knapsackRec(w: IntArray, v: IntArray, n: Int, W: Int): Int {
    return if (n <= 0) {
        0
    } else if (w[n - 1] > W) {
        knapsackRec(w, v, n - 1, W)
    } else {
        max(
            knapsackRec(w, v, n - 1, W), v[n - 1]
                    + knapsackRec(w, v, n - 1, W - w[n - 1])
        )
    }
}

fun part1(): Int {

    val nodes = input()

    val usefulStartNodes = nodes.filter { it.flowRate > 0 }

    calculateDistanceFromImportantNodesToImportantNodes(importantNodes = usefulStartNodes, allNodes = nodes)



    var timeIsTicking = 30



    return 1
}


fun part2(): Int {
   return 2
}

