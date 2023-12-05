package y2023.day05

fun main() {
    AoCGenerics.printAndMeasureResults(
        part1 = { part1() },
        part2 = { part2() }
    )
}

data class Input(
    val seeds: List<Long>,
    val seedRanges: List<Pair<Long, Long>>,
    val seedToSoil: List<Triple<Long, Long, Long>>,
    val soilToFert: List<Triple<Long, Long, Long>>,
    val fertToWater: List<Triple<Long, Long, Long>>,
    val waterToLight: List<Triple<Long, Long, Long>>,
    val lightToTemp: List<Triple<Long, Long, Long>>,
    val tempToHumidity: List<Triple<Long, Long, Long>>,
    val humidityToLocation: List<Triple<Long, Long, Long>>,
)

fun input(): Input {
    val seeds = mutableListOf<Long>()
    val seedRanges = mutableListOf<Pair<Long, Long>>()
    val seedToSoil = mutableListOf<Triple<Long, Long, Long>>()
    val soilToFert = mutableListOf<Triple<Long, Long, Long>>()
    val fertToWater = mutableListOf<Triple<Long, Long, Long>>()
    val waterToLight = mutableListOf<Triple<Long, Long, Long>>()
    val lightToTemp = mutableListOf<Triple<Long, Long, Long>>()
    val tempToHumidity = mutableListOf<Triple<Long, Long, Long>>()
    val humidityToLocation = mutableListOf<Triple<Long, Long, Long>>()

    var currentMappings: MutableList<Triple<Long, Long, Long>> = seedToSoil
    AoCGenerics.getInputLines("/y2023/day05/input.txt").forEach { line ->
        when {
            line.trim().isEmpty() -> return@forEach
            line.trim().startsWith("seeds") -> {
                // seeds for part 1
                seeds.addAll(line.split(":")[1].trim().split(" ").map { it.toLong() })

                // seeds for part 2
                val numbers = line.split(":")[1].trim().split(" ").map { it.toLong() }
                numbers.chunked(2).forEach { chunk ->
                    seedRanges.add(Pair(chunk[0], chunk[1]))
                }


            }

            line.trim().startsWith("seed-to-soil") -> currentMappings = seedToSoil
            line.trim().startsWith("soil-to-fertilizer") -> currentMappings = soilToFert
            line.trim().startsWith("fertilizer-to-water") -> currentMappings = fertToWater
            line.trim().startsWith("water-to-light") -> currentMappings = waterToLight
            line.trim().startsWith("light-to-temperature") -> currentMappings = lightToTemp
            line.trim().startsWith("temperature-to-humidity") -> currentMappings = tempToHumidity
            line.trim().startsWith("humidity-to-location") -> currentMappings = humidityToLocation
            else -> parseLocations(line, currentMappings)
        }
    }

    return Input(
        seeds = seeds.toList(),
        seedRanges = seedRanges.toList(),
        seedToSoil = seedToSoil.toList(),
        soilToFert = soilToFert.toList(),
        fertToWater = fertToWater.toList(),
        waterToLight = waterToLight.toList(),
        lightToTemp = lightToTemp.toList(),
        tempToHumidity = tempToHumidity.toList(),
        humidityToLocation = humidityToLocation.toList()
    )
}


fun List<Triple<Long, Long, Long>>.getMapping(source: Long): Long {
    val mappingRange = this.find {
        source >= it.first && source < (it.first + it.third)
    }
    return if (mappingRange == null) {
        source
    } else {
        val diff = source - mappingRange.first
        mappingRange.second + diff
    }
}

fun List<Triple<Long, Long, Long>>.getRevMapping(location: Long): Long {
    val mappingRange = this.find {
        location >= it.second && location < (it.second + it.third)
    }
    return if (mappingRange == null) {
        location
    } else {
        val diff = location - mappingRange.second
        mappingRange.first + diff
    }
}

fun parseLocations(line: String, currentMappings: MutableList<Triple<Long, Long, Long>>) {
    val destinationRangeStart = line.split(" ")[0].toLong()
    val sourceRangeStart = line.split(" ")[1].toLong()
    val range = line.split(" ")[2].toLong()

    currentMappings.add(Triple(sourceRangeStart, destinationRangeStart, range))
}

fun part1(): Long {
    val input = input()

    return input.seeds.minOf { seed ->
        val soil = input.seedToSoil.getMapping(seed)
        val fert = input.soilToFert.getMapping(soil)
        val water = input.fertToWater.getMapping(fert)
        val light = input.waterToLight.getMapping(water)
        val temp = input.lightToTemp.getMapping(light)
        val hum = input.tempToHumidity.getMapping(temp)
        val location = input.humidityToLocation.getMapping(hum)

        location

    }
}

fun isInRange(seedRanges: List<Pair<Long, Long>>, seed: Long) = seedRanges.any { range ->
    seed >= range.first && seed < (range.first + range.second)
}


fun part2(): Long {
    val input = input()
    var location: Long = 0

    while (true) {
        val hum = input.humidityToLocation.getRevMapping(location)
        val temp = input.tempToHumidity.getRevMapping(hum)
        val light = input.lightToTemp.getRevMapping(temp)
        val water = input.waterToLight.getRevMapping(light)
        val fert = input.fertToWater.getRevMapping(water)
        val soil = input.soilToFert.getRevMapping(fert)
        val seed = input.seedToSoil.getRevMapping(soil)

        if (isInRange(input.seedRanges, seed)) {
            return location
        }
        location++
    }
}

