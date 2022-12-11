package y2022.day11

fun main() {
    AoCGenerics.printAndMeasureResults(
        part1 = { part1() },
        part2 = { part2() }
    )
}

data class Monkey(
    val items: MutableList<Long>,
    val inspectionOperation: String,
    val inspectionChangeValue: Int?, // null means old value
    val testDivision: Int,
    val testTrueTargetMonkeyId: Int,
    val testFalseTargetMonkeyId: Int,
    var itemsInspected: Long = 0,
)

fun input(): List<Monkey> {
    val monkeys = mutableListOf<Monkey>()

    AoCGenerics.getInputLines("/y2022/day11/input.txt")
        .chunked(7).forEachIndexed { index, monkeyInput ->
            monkeys.add(
                Monkey(
                    items = monkeyInput[1]
                        .split(":")[1]
                        .split(',')
                        .map { it.trim().toLong() }
                        .toMutableList(),
                    inspectionOperation = when {
                        monkeyInput[2].contains("*") -> "*"
                        else -> "+"
                    },
                    inspectionChangeValue = when {
                        monkeyInput[2].split(" ").last() == "old" -> null
                        else -> monkeyInput[2].split(" ").last().toInt()
                    },
                    testDivision = monkeyInput[3].split(" ").last().toInt(),
                    testTrueTargetMonkeyId = monkeyInput[4].split(" ").last().toInt(),
                    testFalseTargetMonkeyId = monkeyInput[5].split(" ").last().toInt(),
                ))
        }
    return monkeys.toList()
}

fun doMonkeyBusiness(monkeys: List<Monkey>, easyRelief: Boolean, leastCommonMultiple: Int) {
    monkeys.forEach { monkey ->

        val itemIterator = monkey.items.iterator()

        while (itemIterator.hasNext()) {
            var item = itemIterator.next()

            // inspect Item
            item = when (monkey.inspectionOperation) {
                "*" ->
                    if (monkey.inspectionChangeValue == null) item * item else item * monkey.inspectionChangeValue
                else ->
                    if (monkey.inspectionChangeValue == null) item * item else item + monkey.inspectionChangeValue
            }
            monkey.itemsInspected++

            // relief
            item = if (easyRelief) {
                item / 3
            } else {
                item % leastCommonMultiple
            }

            // test stress level and throw item to another monkey

            if (item % monkey.testDivision.toLong() == 0L) {
                monkeys[monkey.testTrueTargetMonkeyId].items.add(item)
            } else {
                monkeys[monkey.testFalseTargetMonkeyId].items.add(item)
            }
            itemIterator.remove()
        }
    }


}


fun part1(): Long {

    val monkeys = input()
    val leastCommonMultiple = monkeys.map { it.testDivision }.reduce{ acc, element -> acc * element}
    val numberIfRounds = 20

    for (i in 1..numberIfRounds) {
        println("Starting round $i")
        doMonkeyBusiness(monkeys, true, leastCommonMultiple)
    }

    val activeMonkeys = monkeys.sortedBy { it.itemsInspected }.takeLast(2)
    return activeMonkeys[0].itemsInspected * activeMonkeys[1].itemsInspected
}


fun part2(): Long {
    val monkeys = input()
    val leastCommonMultiple = monkeys.map { it.testDivision }.reduce{ acc, element -> acc * element}
    val numberIfRounds = 10000

    for (i in 1..numberIfRounds) {
        doMonkeyBusiness(monkeys, false, leastCommonMultiple)
    }

    val activeMonkeys = monkeys.sortedBy { it.itemsInspected }.takeLast(2)
    return activeMonkeys[0].itemsInspected * activeMonkeys[1].itemsInspected

}

