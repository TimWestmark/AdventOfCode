package y2023.day01

fun main() {
    AoCGenerics.printAndMeasureResults(
        part1 = { part1() },
        part2 = { part2() }
    )
}

fun input(): List<String> {
    return AoCGenerics.getInputLines("/y2023/day01/input.txt")
}

fun part1(): Int {

    return input().sumOf {
        val first = it.first { char -> char.isDigit()}
        val last = it.last { char -> char.isDigit() }

        (first.toString() + last.toString()).toInt()
    }
}


fun part2(): Int {

    val writtenToDigit = mapOf(
        "one" to 1,
        "two" to 2,
        "three" to 3,
        "four" to 4,
        "five" to 5,
        "six" to 6,
        "seven" to 7,
        "eight" to 8,
        "nine" to 9,
    )

    val allDigits = input().map {line ->
        val digits = mutableListOf<Int>()

        line.forEachIndexed { i, char ->
            if(char.isDigit()) {
                digits.add(char.digitToInt())
            } else {
                writtenToDigit.forEach { (written, digit) ->
                    if (line.substring(i).startsWith(written)) digits.add(digit)
                }
            }
        }
        digits
    }

    return allDigits.sumOf {
        (it.first().toString() + it.last().toString()).toInt()
    }
}
