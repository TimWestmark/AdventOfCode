package y2015.day05

fun main() {
    AoCGenerics.printAndMeasureResults(
        part1 = { part1() },
        part2 = { part2() }
    )
}

fun input(): List<String> {
    return AoCGenerics.getInputLines("/y2015/day05/input.txt")
}

fun String.hasDoubleChar(): Boolean {
    var prevChar: Char? = null
    this.forEach { char ->
        if (prevChar == char) {
            return true
        }
        prevChar = char
    }

    return false
}

fun String.hasCleanDoublePair(): Boolean {
    val pairs = mutableListOf<String>()
    for (i in 0 until this.length-1) {
        pairs.add(this.substring(i, i+2))
    }

    if (pairs.toSet().size == pairs.size) {
        return false
    }

    for (i in 0 until pairs.size-1) {
        if (pairs[i] == pairs[i+1]) {
            return false
        }
    }

    return true

}

fun String.hasRepeatingCharWithOneSpace(): Boolean {
    var prevChar: Char? = null
    var twoPrev: Char? = null
    this.forEach { char ->
        if (char == twoPrev) {
            return true
        }
        twoPrev = prevChar
        prevChar = char
    }
    return false
}

fun part1(): Int {
    val vowels = listOf('a', 'e', 'i', 'o', 'u')
    val denyList = listOf("ab", "cd", "pq", "xy")
    return input().map {line ->
        when {
            line.count { char -> vowels.contains(char) } < 3 -> false
            !line.hasDoubleChar() -> false
            denyList.any {deny -> line.contains(deny)} -> false
            else -> true
        }
    }.count { it }

}


fun part2(): Int {
    return input().map { line ->
        when {
            !line.hasCleanDoublePair() -> false
            !line.hasRepeatingCharWithOneSpace() -> false
            else -> true
        }
    }.count { it }
}

