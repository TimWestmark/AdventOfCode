package y2022.day15

import Coord
import kotlin.math.abs

fun main() {
    AoCGenerics.printAndMeasureResults(
        part1 = { part1() },
        part2 = { part2() }
    )
}

fun input(): List<Pair<Coord, Coord>> {

    return AoCGenerics.getInputLines("/y2022/day15/test-input.txt").map { line ->
        Pair(
            Coord(
                x = line.split(" ")[2].split("=")[1].split(",")[0].toInt(),
                y = line.split(" ")[3].split("=")[1].split(":")[0].toInt()
            ), Coord(
                x = line.split(" ")[8].split("=")[1].split(",")[0].toInt(),
                y = line.split(" ")[9].split("=")[1].toInt()
            )
        )
    }
}


fun floodFillNonBeaconCoords(from: Coord, start:Coord, beacon: Coord,nonBeaconFields: MutableSet<Coord>, depth: Int) {
    if (depth == 0) return
    if (from == beacon) return

    if (nonBeaconFields.contains(from)) return

    if (from != start) nonBeaconFields.add(from)

    floodFillNonBeaconCoords(from.copy(x = from.x+1),start, beacon, nonBeaconFields, depth-1)
    floodFillNonBeaconCoords(from.copy(x = from.x-1),start, beacon, nonBeaconFields, depth-1)
    floodFillNonBeaconCoords(from.copy(y = from.y+1),start, beacon, nonBeaconFields, depth-1)
    floodFillNonBeaconCoords(from.copy(y = from.y-1),start, beacon, nonBeaconFields, depth-1)
}

fun part1(): Int {

    val nonBeaconCoords: MutableSet<Coord> = mutableSetOf()
    val sensorBeaconPairs = input()

    sensorBeaconPairs.forEach { pair ->
        val sensor = pair.first
        val beacon = pair.second

        val taxiDistance = abs(sensor.x - beacon.x) + abs(sensor.y - beacon.y)
        val nonBeaconFields = mutableSetOf<Coord>()
        println("For Sensor $sensor filling up to $taxiDistance fields away")
        floodFillNonBeaconCoords(sensor, sensor, beacon, nonBeaconFields, taxiDistance)
        nonBeaconCoords.addAll(nonBeaconFields)
        println(nonBeaconFields)
    }


    return nonBeaconCoords.count { coor -> coor.y == 10 }
}


fun part2(): Int {
    return 2
}

