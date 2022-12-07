import kotlin.system.measureTimeMillis

fun getInputLines(resource: String): List<String> {
    return object {}.javaClass.getResourceAsStream(resource)?.bufferedReader()!!.readLines()
}

fun printAndMeasureResults(part1: () -> Any, part2: () -> Any) {
    val timeInMillisPart1 = measureTimeMillis {
        println("Solution Part 1: ${part1()}")
    }

    val timeInMillisPart2 = measureTimeMillis {
        println("Solution Part 2: ${part2()}")
    }

    println("(The part1 operation took $timeInMillisPart1 ms)")
    println("(The part2 operation took $timeInMillisPart2 ms)")
}