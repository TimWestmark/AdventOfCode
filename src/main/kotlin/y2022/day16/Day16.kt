package y2022.day16

import AoCGenerics


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
    var opened: Boolean = false,
    var seen: Boolean = false
)

fun input(): List<Valve> {
    return AoCGenerics.getInputLines("/y2022/day16/test-input.txt").map { line ->
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


fun part1(): Int {

    val nodes = input()

    val usefulStartNodes = nodes.filter { it.flowRate > 0 || it.name == "AA"}

    calculateDistanceFromImportantNodesToImportantNodes(importantNodes = usefulStartNodes, allNodes = nodes)


    var timeLeft = 30
    var flowTotal = 0
    var currentPosition = nodes.find { it.name == "AA" }!!
    val startPosition = nodes.find { it.name == "AA" }!!


    val test = currentPosition.distanceToNodes.keys.toList().permutations()

    return (test).parallelStream().map { order ->
        walkWay(startPosition, order, nodes)
    }.toList()
        .max()


    while (true) {
        val nextNode = currentPosition.distanceToNodes.keys.map {
            val curNode = nodes.find { node -> node.name == it}!!

            val curNodeValue = if (curNode.opened) 0 else  value(timeLeft, currentPosition.distanceToNodes[curNode.name]!!,curNode.flowRate)
            Pair(curNode, curNodeValue)
        }.maxBy { it.second }

        if (nextNode.second == 0) {
            break;
        }

        val timeTaken = currentPosition.distanceToNodes[nextNode.first.name]!! + 1 // distance + one to open
        timeLeft -= timeTaken
        flowTotal += timeLeft * nextNode.first.flowRate
        currentPosition.opened = true

        println("Going from ${currentPosition.name} to ${nextNode.first.name} | Time left: $timeLeft | Total Flow: $flowTotal")
        currentPosition = nextNode.first
    }
//
//    return flowTotal
}

fun walkWay(start: Valve, order: List<String>, allNodes: List<Valve>): Int {
    var timeLeft = 30
    var flowTotal = 0

    var currentNode = start

    for(nextNodeName in order)  {
        val nextNode = allNodes.find { it.name == nextNodeName }!!
        val timeTaken = currentNode.distanceToNodes[nextNode.name]!! + 1

        if (timeLeft-timeTaken <=0) break
        timeLeft -= timeTaken
        flowTotal += timeLeft * nextNode.flowRate
        currentNode = nextNode
    }

    return flowTotal
}

fun <T> List<T>.permutations(): List<List<T>> = if(isEmpty()) listOf(emptyList()) else  mutableListOf<List<T>>().also{result ->
    for(i in this.indices){
        (this - this[i]).permutations().forEach{
            result.add(it + this[i])
        }
    }
}


fun part2(): Int {
   return 2
}

