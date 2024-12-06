package y2024.day05

fun main() {
    AoCGenerics.printAndMeasureResults(
        part1 = { part1() },
        part2 = { part2() }
    )
}

fun input(): Input {
    val lines = AoCGenerics.getInputLines("/y2024/day05/input.txt")

    val split = lines.indexOf("")

    return Input(
        rules = lines.subList(0, split).map {
            val (a, b) = it.split("|")
            Pair(a.toInt(), b.toInt())
        },
        updates = lines.subList(split + 1, lines.size).map {
            it.split(",").map { it.toInt() }
        }
    )
}

data class Input(val rules: List<Pair<Int, Int>>, val updates: List<List<Int>>)

fun isUpdateInOrder(update: List<Int>, rules: List<Pair<Int, Int>>): Boolean {
    update.forEachIndexed { index, curNum ->
        val curRules = rules.filter { it.first == curNum }

        for (j in index + 1 until update.size) {
            if (update[j] !in curRules.map { it.second } ) return false
        }
    }
    return true
}

// Topological sort for reordering
fun topologicalSort(update: List<Int>, rules:  MutableMap<Int, MutableSet<Int>>): List<Int> {
    val inDegree = mutableMapOf<Int, Int>()
    val graph = mutableMapOf<Int, MutableList<Int>>()

    // Build the graph
    for (page in update) {
        inDegree[page] = 0
        graph[page] = mutableListOf()
    }
    for ((before, afterSet) in rules) {
        if (before in update) {
            for (after in afterSet) {
                if (after in update) {
                    graph[before]!!.add(after)
                    inDegree[after] = inDegree.getOrDefault(after, 0) + 1
                }
            }
        }
    }

    // Topological sorting using Kahn's Algorithm
    val queue = ArrayDeque<Int>()
    inDegree.filter { it.value == 0 }.keys.forEach { queue.add(it) }
    val sorted = mutableListOf<Int>()

    while (queue.isNotEmpty()) {
        val current = queue.removeFirst()
        sorted.add(current)
        for (neighbor in graph[current]!!) {
            inDegree[neighbor] = inDegree[neighbor]!! - 1
            if (inDegree[neighbor] == 0) {
                queue.add(neighbor)
            }
        }
    }
    return sorted
}

fun part1(): Int {
    val input = input()
    val rules = input.rules
    val updates = input.updates

    val validUpdates = updates.filter {
        isUpdateInOrder(it, rules)
    }

    return validUpdates.sumOf { it[it.size/2] }
}


fun part2(): Int {
    val input = input()
    val rules = input.rules
    val updates = input.updates

    val rulesMap = mutableMapOf<Int, MutableSet<Int>>()
    for (rule in rules) {
        rulesMap.computeIfAbsent(rule.first) { mutableSetOf() }.add(rule.second)
    }


    val invalidUpdates = updates.filter {
        !isUpdateInOrder(it, rules)
    }
    println(invalidUpdates)

    val ordered = invalidUpdates.map { unorderedUpdate ->
        topologicalSort(unorderedUpdate, rulesMap)
    }

    return ordered.sumOf { it[it.size/2] }
}

