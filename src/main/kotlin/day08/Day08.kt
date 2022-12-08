package day08

import Matrix
import MatrixUtils

fun main() {
    AoCGenerics.printAndMeasureResults(
        part1 = { part1() },
        part2 = { part2() }
    )
}

data class Tree(
    val height: Int,
    var counted: Boolean,
    var scenicScore: Int = 0,
)

fun input(): Matrix<Tree> {
    return MatrixUtils.createMatrix(AoCGenerics.getInputLines("/day08/input.txt")) {
        it.map { char ->
            Tree(char.digitToInt(), false)
        }
    }

}

fun List<Tree>.countVisibleTrees() {
    var curHighestTree = this.first().height

    this.forEach { tree ->
        if (tree.height > curHighestTree) {
            tree.counted = true
            curHighestTree = tree.height
        }
    }


}

fun calculateScenicScore(tree: Tree, spalte: Int, zeile: Int, forest: List<List<Tree>>) {
    var leftScore = 0
    var upScore = 0
    var rightScore = 0
    var downScore = 0


    // look left
    for (i in spalte - 1 downTo 0) {
        leftScore++

        if (forest[zeile][i].height >= tree.height) {
            break
        }
    }

    // look right
    for (i in spalte + 1 until forest[spalte].size) {
        rightScore++

        if (forest[zeile][i].height >= tree.height) {
            break
        }
    }

    // look up
    for (i in zeile - 1 downTo 0) {
        upScore++

        if (forest[i][spalte].height >= tree.height) {
            break
        }
    }

    // look down
    for (i in zeile + 1 until forest.size) {
        downScore++

        if (forest[i][spalte].height >= tree.height) {
            break
        }
    }


    tree.scenicScore = leftScore * rightScore * downScore * upScore
}

fun part1(): Int {
    val input = input()


    input.subList(1, input.size - 1)
        .forEach { treeLine ->
            treeLine.countVisibleTrees()
            treeLine.reversed().countVisibleTrees()

        }

    val transposed = MatrixUtils.transposeMatrix(input)
    transposed.subList(1, input.size - 1)
        .forEach { treeLine ->
            treeLine.countVisibleTrees()
            treeLine.reversed().countVisibleTrees()
        }


    input.first().forEach { tree -> tree.counted = true }
    input.last().forEach { tree -> tree.counted = true }
    input.forEach { row ->
        row.first().counted = true
        row.last().counted = true
    }


    return input.map { row -> row.count { tree -> tree.counted } }.sum()

}


fun part2(): Int {
    val forest = input()

    forest.forEachIndexed { zeile, trees ->
        trees.forEachIndexed { spalte, tree ->
            calculateScenicScore(tree, spalte, zeile, forest)
        }
    }

    return forest.flatten().maxOf { it.scenicScore }


}
