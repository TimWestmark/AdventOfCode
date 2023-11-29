package y2016.day06

fun main() {
    AoCGenerics.printAndMeasureResults(
        part1 = { part1() },
        part2 = { part2() }
    )
}

fun input(): List<String> {
    return AoCGenerics.getInputLines("/y2016/day06/input.txt")
}

fun solve(lines:List<String>, byMin: Boolean): String {
    var result = ""

    repeat(lines[0].length) {
        val charCount = mutableMapOf<Char, Int>()
        lines.forEach { line ->
            val char = line[it]
            charCount[char] = if (charCount.containsKey(char)) {
                charCount[char]!! + 1
            } else {
                0
            }
        }

        result += if (byMin) {
            charCount.minByOrNull { it.value }!!.key
        } else {
            charCount.maxByOrNull { it.value }!!.key
        }
    }

    return result
}

fun part1(): String = solve(input(), false)
fun part2(): String = solve(input(), true)

