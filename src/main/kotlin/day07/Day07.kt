package day07

import getInputLines
import kotlin.system.measureTimeMillis

fun main() {
    val timeInMillisPart1 = measureTimeMillis {
        println("Solution Part 1: ${part1()}")
    }

    val timeInMillisPart2 = measureTimeMillis {
        println("Solution Part 2: ${part2()}")
    }

    println("(The part1 operation took $timeInMillisPart1 ms)")
    println("(The part2 operation took $timeInMillisPart2 ms)")
}

data class Directory(
    val name: String,
    val subDirs: MutableList<Directory>,
    val parentDir: Directory?,
    var size: Int
)

fun input(): Directory {
    val root = Directory("/", mutableListOf(), null, 0)
    var currentDir = root
    getInputLines("/day07/input.txt")
//    getInputLines("/day07/test-input.txt")
        .forEach { line ->
            when {
                line.startsWith("$ cd /") || line == "$ ls" -> {}
                line.startsWith("$ cd ..") -> currentDir = currentDir.parentDir!!
                line.startsWith("$ cd ") -> currentDir = currentDir.subDirs.find { dir -> dir.name == line.split(" ")[2] }!!
                line.startsWith("dir") -> currentDir.subDirs.add(
                    Directory(
                        line.split(" ")[1],
                        mutableListOf(),
                        currentDir,
                        0
                    )
                )
                else -> currentDir.addFilesize(line.split(" ")[0].toInt())
            }
        }

    return root
}

fun Directory.addFilesize(size: Int) {
    this.size += size
    if (parentDir != null) {
        this.parentDir.addFilesize(size)
    }
}

fun getDirSizes(dir: Directory, resultList: MutableList<Int>) {
    resultList.add(dir.size)
    dir.subDirs.forEach {
        getDirSizes(it, resultList)
    }
}

fun part1(): Int {
    val root = input()
    val flatDirSizes = mutableListOf<Int>()
    getDirSizes(root, flatDirSizes)
    return flatDirSizes.filter { dirSize -> dirSize <= 100000 }.sum()
}


fun part2(): Int {
    val root = input()

    val totalSpace = 70_000_000
    val freeSpaceNeeded = 30_000_000
    val unusedSpace = totalSpace - root.size
    val spaceToBeDeleted = freeSpaceNeeded - unusedSpace

    val flatDirSizes = mutableListOf<Int>()
    getDirSizes(root, flatDirSizes)

    return flatDirSizes.filter { dirSize -> dirSize >= spaceToBeDeleted }.minOf{ it }
}