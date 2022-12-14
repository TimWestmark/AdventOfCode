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
    return AoCGenerics.getInputLines("/y2022/day13/input.txt")
        .chunked(3)
        .map {
            index = 0
            val firstData = it[0]
            val secondData = it[1]
            Pair(initParseData(firstData), initParseData(secondData))
        }
}

fun compareTo(a: List<Any>, b: List<Any>): Int {
    return inOrder(Pair(a,b))
}

fun inOrder(pair: Pair<List<Any>, List<Any>>): Int {
    val left = pair.first
    val right = pair.second


    for (i in 0 until maxOf(left.size, right.size)) {
        when {
            left.size <= i -> return 1
            right.size <= i -> return -1

            left[i] is Int && right[i] is Int -> {
                return if ((left[i] as Int) < (right[i] as Int)) {
                    1
                } else if ((left[i] as Int) > (right[i] as Int)) {
                    -1
                } else continue
            }
            left[i] is List<*> && right[i] is List<*> -> {
                val x = inOrder(Pair(left[i] as List<Any>, right[i] as List<Any> ))
                if (x != 0) return x else continue
            }
            left[i] is List<*> && right[i] is Int -> {
                return inOrder(Pair(left[i] as List<Any>, listOf(right[i])))
            }
            left[i] is Int && right[i] is List<*> -> {
                return inOrder(Pair(listOf(left[i]), right[i] as List<Any> ))
            }
        }
    }
    return 0

}

fun part1(): Int {

    val input = input()

    return input.mapIndexedNotNull { index, pair ->
        if (inOrder(pair) > 0) {
            index + 1
        } else {
            null
        }
    }.sum()
}


fun part2(): Int {
    val test  = input().map { pair ->
        listOf(pair.first, pair.second)
    }.flatten().toMutableList()

    val add1 = listOf(listOf(2))
    val add2 = listOf(listOf(6))
    test.add(add1)
    test.add(add2)


    val myComparator = Comparator<List<Any>> { element1, element2 ->
        inOrder(Pair(element1, element2))
    }

    val sorted = test.sortedWith(myComparator).reversed()

    val index1 = sorted.indexOf(add1) + 1
    val index2 = sorted.indexOf(add2) + 1


    return index1 * index2
}

