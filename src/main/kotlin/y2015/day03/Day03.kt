package y2015.day03

fun main() {
    AoCGenerics.printAndMeasureResults(
        part1 = { part1() },
        part2 = { part2() }
    )
}

fun input(): String {
    return AoCGenerics.getInputLines("/y2015/day03/input.txt").first()
}

fun part1(): Int {
    var x = 0
    var y = 0

    val visited: MutableSet<String> = mutableSetOf("0/0")
    input().forEach {
        when (it) {
            '^' -> y++
            'v' -> y--
            '<' -> x--
            '>' -> x++
        }
        visited.add("$x/$y")
    }

    return visited.size
}


fun part2(): Int {
    var x = 0
    var y = 0
    var xRobo = 0
    var yRobo = 0

    val visited: MutableSet<String> = mutableSetOf("0/0")
    input().forEachIndexed { index, it ->
        when (it) {
            '^' -> if (index % 2 == 0) y++ else yRobo++
            'v' -> if (index % 2 == 0) y-- else yRobo--
            '<' -> if (index % 2 == 0) x-- else xRobo--
            '>' -> if (index % 2 == 0) x++ else xRobo++
        }
        visited.add("$x/$y")
        visited.add("$xRobo/$yRobo")
    }

    return visited.size
}

