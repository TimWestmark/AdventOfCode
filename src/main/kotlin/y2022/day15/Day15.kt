package y2022.day15

import Coord

fun main() {
    AoCGenerics.printAndMeasureResults(
        part1 = { part1() },
        part2 = { part2() }
    )
}

fun input(): Pair<Set<Coord>, Set<Coord>> {
    val sensors = mutableSetOf<Coord>()
    val beacons = mutableSetOf<Coord>()

            AoCGenerics.getInputLines("/y2022/day15/input.txt").forEach { line ->
                sensors.add(Coord(
                    x = line.split(" ")[2].split("=")[1].split(",")[0].toInt(),
                    y = line.split(" ")[3].split("=")[1].split(":")[0].toInt()
                ))
                beacons.add(Coord(
                    x = line.split(" ")[8].split("=")[1].split(",")[0].toInt(),
                    y = line.split(" ")[9].split("=")[1].toInt()
                ))
            }


    return Pair(sensors.toSet(), beacons.toSet())

}

fun part1(): Int {
    val objects = input()
    val sensors = objects.first
    val beacons = objects.second

    return 1
}


fun part2(): Int {
   return 2
}

