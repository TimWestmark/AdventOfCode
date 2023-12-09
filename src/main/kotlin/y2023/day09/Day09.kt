package y2023.day09

fun main() {
    AoCGenerics.printAndMeasureResults(
        part1 = { part1() },
        part2 = { part2() }
    )
}

fun input(): List<List<Int>> {
    return AoCGenerics.getInputLines("/y2023/day09/input.txt").map { line ->
        line.split(" ").map { it.toInt() }
    }
}

fun getSubSequence(sequence: List<Int>): MutableList<Int> {
    val diffs = mutableListOf<Int>()
    repeat(sequence.size-1) {
        diffs.add(sequence[it+1]-sequence[it])
    }

    return diffs
}

fun getSubSequenceReversed(sequence: List<Int>): MutableList<Int> {
    var diffs = mutableListOf<Int>()

    repeat(sequence.size-1) {
        val diff = sequence[sequence.size-it-1] - sequence[sequence.size-it-2]

        diffs = (listOf(diff) + diffs).toMutableList()
    }

    return diffs
}

fun part1(): Int {
    val sequences = input()

    return sequences.sumOf { sequence ->
        val subsequences = mutableListOf(sequence.toMutableList())

        var currentSubsequence = sequence.toMutableList()
        do {
            currentSubsequence = getSubSequence(currentSubsequence)
            subsequences.add(currentSubsequence)
        } while (currentSubsequence.any { it != 0 })

        // start extrapolating
        val revSequences = subsequences.reversed()

        // We add the first zero to the last calculated sequence
        revSequences[0].add(0)

        revSequences.forEachIndexed{ index, subsequence ->
            if (index == 0) return@forEachIndexed
            subsequence.add(subsequence.last() + revSequences[index-1].last())
        }

        return@sumOf revSequences.last().last()
    }
}


fun part2(): Int {
    val sequences = input()

    return sequences.sumOf { sequence ->
        val subsequences = mutableListOf(sequence.toMutableList())

        var currentSubsequence = sequence.toMutableList()
        do {
            currentSubsequence = getSubSequenceReversed(currentSubsequence)
            subsequences.add(currentSubsequence)
        } while (currentSubsequence.any { it != 0 })

        // start extrapolating
        val revSequences = subsequences.reversed().toMutableList()

        // We add the first zero to the last calculated sequence
        revSequences[0] = (mutableListOf(0) + revSequences[0]).toMutableList()

        revSequences.forEachIndexed{ index, subsequence ->
            if (index == 0) return@forEachIndexed
            revSequences[index] = (listOf(subsequence.first() - revSequences[index-1].first())).toMutableList()
        }

        return@sumOf revSequences.last().first()
    }
}
