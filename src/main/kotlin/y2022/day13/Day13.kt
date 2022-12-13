package y2022.day13

fun main() {
    AoCGenerics.printAndMeasureResults(
        part1 = { part1() },
        part2 = { part2() }
    )
}


var index = 0
fun initParseData(line: String): List<Any> {

    index = 0
    return parseData(line)

}

fun parseData(line: String): List<Any> {
    val list: MutableList<Any> = mutableListOf()

    while (index < line.length) {
        when (line[index]) {
            '[' -> {
                index++
                list.add(parseData(line))
            }

            ']' -> {
                index++
                break
            }

            ',' -> index++
            else -> {
                var numberString: String = line[index].toString() // this is a digit
                do {
                    index++
                    if (line[index].isDigit()) {
                        numberString += line[index]
                    }
                } while (line[index].isDigit())
                list.add(numberString.toInt())
            }
        }
    }

    return list

}

fun input(): List<Pair<List<Any>, List<Any>>> {
    return AoCGenerics.getInputLines("/y2022/day13/test-input.txt")
        .chunked(3)
        .map {
            index = 0
            val firstData = it[0]
            val secondData = it[1]
            Pair(initParseData(firstData), initParseData(secondData))
        }
}

fun inOrder(pair: Pair<List<Any>, List<Any>>): Boolean {
    val first = pair.first
    val second = pair.second


    for (i in 0 until maxOf(first.size, second.size)) {
        when {
            first.size <= i -> return true
            second.size <= i -> return false
        }
    }

    return true
}

fun part1(): Int {

    val input = input()

    return input.mapIndexedNotNull { index, pair ->
        if (inOrder(pair)) {
            index + 1
        } else {
            null
        }
    }.sum()
}


fun part2(): Int {
    return 2
}

