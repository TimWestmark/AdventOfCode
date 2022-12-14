package y2022.day14

import Coord

fun main() {
    AoCGenerics.printAndMeasureResults(
        part1 = { part1() },
        part2 = { part2() }
    )
}

enum class Element {
    ROCK,
    SAND,
    AIR,
    SAND_SOURCE
}

fun input(): Pair<MutableMap<Int, MutableMap<Int, Element>>, Int> {
    val allPaths = AoCGenerics.getInputLines("/y2022/day14/input.txt").map { line ->
        val lineCoords = line.split(" -> ")
        val paths: MutableList<Pair<Coord, Coord>> = mutableListOf()
        for (i in 0 until lineCoords.size - 1) {
            paths.add(
                Pair(
                    Coord(
                        x = lineCoords[i].split(",")[0].toInt(),
                        y = lineCoords[i].split(",")[1].toInt(),
                    ),
                    Coord(
                        x = lineCoords[i + 1].split(",")[0].toInt(),
                        y = lineCoords[i + 1].split(",")[1].toInt()

                    )
                )
            )
        }
        paths.toList()

    }.flatten()

    val maxFall = allPaths.map { pair -> listOf(pair.first, pair.second) }.flatten().maxOfOrNull { it.y }!!

    val map: MutableMap<Int, MutableMap<Int, Element>> = mutableMapOf()


    // TODO: Adjust these values
    for (x in 0 until 1000) {
        map[x] = mutableMapOf()
        for (y in 0 until maxFall + 2) {
            map[x]!![y] =  Element.AIR
        }
    }

    map[500]!![0] = Element.SAND_SOURCE

    allPaths.forEach { path ->
        val start = path.first
        val end = path.second

        for (x in minOf(start.x, end.x) .. maxOf(start.x, end.x) ) {
            for(y in minOf(start.y, end.y) .. maxOf(start.y, end.y)) {
                map[x]!![y] = Element.ROCK
            }
        }
    }

    return Pair(map, maxFall+2)
}

fun part1(): Int {

    val map = input().first

    var fallingIntoTheAbyss =  false
    var sandCounter = 0
    while (!fallingIntoTheAbyss) {
        var newSand = Coord(500,0)
        sandCounter++
        while (true) {
            when {
                map[newSand.x]!![newSand.y+1] == null -> {
                    fallingIntoTheAbyss = true
                    break
                } // Endless abyss of Sand piles
                map[newSand.x]!![newSand.y+1] == Element.AIR -> {
                    newSand = newSand.copy(y = newSand.y + 1) // move down
                    continue
                }
                map[newSand.x-1]!![newSand.y+1] == Element.AIR -> {
                    newSand = newSand.copy(x = newSand.x-1 ,y = newSand.y + 1) // move down left
                    continue
                }
                map[newSand.x+1]!![newSand.y+1] == Element.AIR -> {
                    newSand = newSand.copy(x = newSand.x+1 ,y = newSand.y + 1) // move down right
                    continue
                }
                else -> {
                    map[newSand.x]!![newSand.y] = Element.SAND // Sand block has been placed
                    break
                }
            }
        }

    }




    return sandCounter-1
}


fun part2(): Int {
    val map = input().first
    val maxY = input().second
    for (x in 0 until 1000) {
        map[x]!![maxY] = Element.ROCK
    }

    var fallingIntoTheAbyss =  false
    var sandCounter = 0
    while (!fallingIntoTheAbyss) {
        var newSand = Coord(500,0)
        sandCounter++
        while (true) {
            when {
                map[newSand.x]!![newSand.y+1] == null -> {
                    fallingIntoTheAbyss = true // Endless abyss of Sand piles
                    break
                }
                map[newSand.x]!![newSand.y+1] == Element.AIR -> {
                    newSand = newSand.copy(y = newSand.y + 1) // move down
                    continue
                }
                map[newSand.x-1]!![newSand.y+1] == Element.AIR -> {
                    newSand = newSand.copy(x = newSand.x-1 ,y = newSand.y + 1) // move down left
                    continue
                }
                map[newSand.x+1]!![newSand.y+1] == Element.AIR -> {
                    newSand = newSand.copy(x = newSand.x+1 ,y = newSand.y + 1) // move down right
                    continue
                }
                else -> {
                    map[newSand.x]!![newSand.y] = Element.SAND // Sand block has been placed
                    if (newSand.x == 500 && newSand.y == 0) fallingIntoTheAbyss = true
                    break
                }
            }
        }

    }


    return sandCounter
}

