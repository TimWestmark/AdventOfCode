#!/bin/bash

year=$1
day=$2

mkdir -p src/main/resources/y$year/day$day
touch src/main/resources/y$year/day$day/input.txt
touch src/main/resources/y$year/day$day/test-input.txt

mkdir -p src/main/kotlin/y$year/day$day
touch src/main/kotlin/y$year/day$day/Day$day.kt

cat << EOF > src/main/kotlin/y$year/day$day/Day$day.kt
package y$year.day$day

fun main() {
    AoCGenerics.printAndMeasureResults(
        part1 = { part1() },
        part2 = { part2() }
    )
}

fun input(): List<String> {
    return AoCGenerics.getInputLines("/y$year/day$day/input.txt")
}

fun part1(): Int {
    return 1
}


fun part2(): Int {
   return 2
}

EOF

git add .