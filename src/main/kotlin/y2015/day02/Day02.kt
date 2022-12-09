package y2015.day02

fun main() {
    AoCGenerics.printAndMeasureResults(
        part1 = { part1() },
        part2 = { part2() }
    )
}

data class Present(
    val length: Int,
    val width: Int,
    val height: Int,
)

fun Present.calculatePaperNeeded(): Int {
    return 2 * length * width + 2 * width * height + 2 * height * length + minOf(
        length * width,
        width * height,
        height * length
    )
}

fun Present.calculateRibbonNeeded(): Int {
     val wrap = when {
        maxOf(length, width, height) == length -> 2 * width + 2 * height
        maxOf(length, width, height) == width -> 2 * length + 2 * height
        else  -> 2 * width + 2 * length
    }

    return length * width * height + wrap
}

fun input(): List<Present> {
    return AoCGenerics.getInputLines("/y2015/day02/input.txt").map { line ->
        val measures = line.split("x").map { it.toInt() }
        Present(measures[0], measures[1], measures[2])
    }
}

fun part1(): Int {
    val presents = input()
    return presents.sumOf { it.calculatePaperNeeded() }
}


fun part2(): Int {
    val presents = input()
    return presents.sumOf { it.calculateRibbonNeeded() }
}

