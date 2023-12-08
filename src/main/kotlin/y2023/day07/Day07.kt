package y2023.day07

fun main() {
    AoCGenerics.printAndMeasureResults(
        part1 = { part1() },
        part2 = { part2() }
    )
}

typealias Hand = String

data class Game(
    val bidValue: Int,
    val hand: Hand
)

enum class HandType {
    FIVE,
    FOUR,
    FULL_HOUSE,
    THREE,
    TWO_PAIR,
    PAIR,
    HIGH_CARD
}

fun input(): List<Game> {
    return AoCGenerics.getInputLines("/y2023/day07/input.txt").map {
        Game(
            hand = it.split(" ")[0].trim(),
            bidValue = it.split(" ")[1].trim().toInt()
        )
    }
}

fun Hand.getType1(): HandType {
    val occurrencesMap = mutableMapOf<Char, Int>()
    for (c in this) {
        occurrencesMap.putIfAbsent(c, 0)
        occurrencesMap[c] = occurrencesMap[c]!! + 1
    }

    return when {
        occurrencesMap.values.contains(5) -> HandType.FIVE
        occurrencesMap.values.contains(4) -> HandType.FOUR
        occurrencesMap.values.containsAll(listOf(3,2)) -> HandType.FULL_HOUSE
        occurrencesMap.values.contains(3) -> HandType.THREE
        occurrencesMap.values.count { it == 2 } == 2 -> HandType.TWO_PAIR
        occurrencesMap.values.count { it == 2 } == 1 -> HandType.PAIR
        else -> HandType.HIGH_CARD
    }
}

fun Hand.getType2(): HandType {

    if (this.contains('J')) {
        val numberOfJokers = count{it == 'J'}
        if (numberOfJokers == 5) return HandType.FIVE
        val stringWithoutJokers = filter { it != 'J' }

        val occurrencesMapWithoutJokers = mutableMapOf<Char, Int>()
        stringWithoutJokers.forEach { c ->
            occurrencesMapWithoutJokers.putIfAbsent(c, 0)
            occurrencesMapWithoutJokers[c] = occurrencesMapWithoutJokers[c]!! + 1
        }

        return when {
            occurrencesMapWithoutJokers.values.maxOrNull()!! + numberOfJokers == 5 -> HandType.FIVE
            occurrencesMapWithoutJokers.values.maxOrNull()!! + numberOfJokers == 4 -> HandType.FOUR
            occurrencesMapWithoutJokers.values.count { it == 2 } == 2              -> HandType.FULL_HOUSE
            occurrencesMapWithoutJokers.values.maxOrNull()!! + numberOfJokers == 3 -> HandType.THREE
            else -> HandType.PAIR
        }


    } else {
        val occurrencesMap = mutableMapOf<Char, Int>()
        forEach { c ->
            occurrencesMap.putIfAbsent(c, 0)
            occurrencesMap[c] = occurrencesMap[c]!! + 1
        }

        return when {
            occurrencesMap.values.contains(5) -> HandType.FIVE
            occurrencesMap.values.contains(4) -> HandType.FOUR
            occurrencesMap.values.containsAll(listOf(3,2)) -> HandType.FULL_HOUSE
            occurrencesMap.values.contains(3) -> HandType.THREE
            occurrencesMap.values.count { it == 2 } == 2 -> HandType.TWO_PAIR
            occurrencesMap.values.count { it == 2 } == 1 -> HandType.PAIR
            else -> HandType.HIGH_CARD
        }
    }

}


fun Game.compareToOther1(other: Game): Int {
    val thisType = this.hand.getType1()
    val otherType  = other.hand.getType1()
    if (thisType < otherType) return 1
    if (thisType > otherType) return -1

    this.hand.forEachIndexed { index, thisChar ->
        val otherChar = other.hand[index]

        if (thisChar == otherChar) return@forEachIndexed

        if (thisChar.toValue1() > otherChar.toValue1()) return 1
        if (thisChar.toValue1() < otherChar.toValue1()) return -1
    }

    return 0
}

fun Game.compareToOther2(other: Game): Int {
    val thisType = this.hand.getType2()
    val otherType  = other.hand.getType2()
    if (thisType < otherType) return 1
    if (thisType > otherType) return -1

    this.hand.forEachIndexed { index, thisChar ->
        val otherChar = other.hand[index]

        if (thisChar == otherChar) return@forEachIndexed

        if (thisChar.toValue2() > otherChar.toValue2()) return 1
        if (thisChar.toValue2() < otherChar.toValue2()) return -1
    }

    return 0
}

fun Char.toValue1(): Int {
    return when {
        this.isDigit() -> this.digitToInt()
        this == 'T' -> 10
        this == 'J' -> 11
        this == 'Q' -> 12
        this == 'K' -> 13
        else -> 14
    }
}

fun Char.toValue2(): Int {
    return when {
        this.isDigit() -> this.digitToInt()
        this == 'T' -> 10
        this == 'J' -> 1
        this == 'Q' -> 11
        this == 'K' -> 12
        else -> 13
    }
}


fun part1(): Int {

    val games = input()
    val sortedGames = games.sortedWith { game1, game2 ->
        game1.compareToOther1(game2)
    }

    var result = 0
    sortedGames.forEachIndexed { index, game ->
        result += (index+1) * game.bidValue
    }
    return result
}


fun part2(): Int {
    val games = input()
    val sortedGames = games.sortedWith { game1, game2 ->
        game1.compareToOther2(game2)
    }

    var result = 0
    sortedGames.forEachIndexed { index, game ->
        result += (index+1) * game.bidValue
    }
    return result
}

