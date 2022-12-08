package day08

import getInputLines
import printAndMeasureResults

fun main() {
    printAndMeasureResults(
        part1 = { part1() },
        part2 = { part2() }
    )
}

data class Tree(
    val height: Int,
    var counted: Boolean,
    var scenicScore: Int = 0,
)

fun input(): List<List<Tree>> {
    return getInputLines("/day08/input.txt").map { row ->
        row.map { char -> Tree(char.digitToInt(), false) }
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

fun transpose(matrix: List<List<Tree>>): List<List<Tree>> {
    return List(matrix[0].size) { col ->
        List(matrix.size) { row ->
            matrix[row][col]
        }.toList()
    }.toList()
}

fun calculateScenicScore(tree: Tree, x: Int, y: Int, forest: List<List<Tree>> ) {
    var leftScore = 0
    var upScore = 0
    var rightScore = 0
    var downScore = 0


    // look left
    var previousHighest = tree.height
    for (i in x-1 downTo 0 ) {
        leftScore++

        if (forest[y][i].height >= previousHighest) {
            break
        }

        if (forest[y][i].height <= previousHighest) {
            previousHighest = forest[y][i].height
        }




    }

    // look right
    previousHighest = tree.height
    for (i in x+1 until forest[x].size) {
        rightScore++

        if (forest[y][i].height >= previousHighest) {
            break
        }

        if (forest[y][i].height <= previousHighest) {
            previousHighest = forest[y][i].height
        }


    }

    // look up
    previousHighest = tree.height
    for (i in y-1 downTo  0 ) {
        upScore++

        if (forest[i][x].height >= previousHighest) {
            break
        }

        if (forest[i][x].height <= previousHighest) {
            previousHighest = forest[i][x].height
        }


    }

    // look down
    previousHighest = tree.height
    for (i in y+1 until forest.size) {
        downScore++

        if (forest[i][x].height >= previousHighest) {
            break
        }

        if (forest[i][x].height <= previousHighest) {
            previousHighest = forest[i][x].height
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

    val transposed = transpose(input)
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


    return  input.map { row -> row.count { tree -> tree.counted } }.sum()

}


fun part2(): Int {
    val forest = input()

    forest.forEachIndexed { indexRow, trees ->
        trees.forEachIndexed { indexColumn, tree ->
            calculateScenicScore(tree, indexColumn, indexRow, forest)
        }
    }

    val test = forest.flatten().maxOf { it.scenicScore }



    return 3

}