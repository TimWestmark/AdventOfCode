import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking
import kotlin.math.abs
import kotlin.system.measureTimeMillis


object AoCGenerics {

    fun getInputLines(resource: String): List<String> {
        return object {}.javaClass.getResourceAsStream(resource)?.bufferedReader()!!.readLines()
    }

    fun printAndMeasureResults(part1: () -> Any, part2: () -> Any) {
        val timeInMillisPart1 = measureTimeMillis {
            println("Solution Part 1: ${part1()}")
        }

        val timeInMillisPart2 = measureTimeMillis {
            println("Solution Part 2: ${part2()}")
        }

        println("(The part1 operation took $timeInMillisPart1 ms)")
        println("(The part2 operation took $timeInMillisPart2 ms)")
    }
}

object NumberStuff {

    // Thanks https://www.baeldung.com/kotlin/lcm
    fun findLCMOfListOfNumbers(numbers: List<Long>): Long {
        var result = numbers[0]
        for (i in 1 until numbers.size) {
            result = findLCM(result, numbers[i])
        }
        return result
    }

    fun findLCM(a: Long, b: Long): Long {
        val larger = if (a > b) a else b
        val maxLcm = a * b
        var lcm = larger
        while (lcm <= maxLcm) {
            if ((lcm % a).toInt() == 0 && (lcm % b).toInt() == 0) {
                return lcm
            }
            lcm += larger
        }
        return maxLcm
    }
}

object StringStuff {

    fun generateStringCombinations(elements: List<String>, length: Int): List<List<String>> {
        if (length == 0) return listOf(emptyList())
        val smallerCombinations = generateStringCombinations(elements, length - 1)
        return smallerCombinations.flatMap { combo ->
            elements.map { operator -> combo + operator }
        }
    }
}

object Coroutines {
    fun <A, B>List<A>.parallelMap(f: suspend (A) -> B): List<B> = runBlocking {
        map { async(Dispatchers.Default) { f(it) } }.awaitAll()
    }
}

typealias Matrix<T> = List<List<T>>

data class Coord(
    val x: Int,
    val y: Int,
)

fun Coord.distance(other: Coord): Long =
    (abs(other.x - x) + abs(other.y - y) ).toLong()

fun Coord.left() = Coord(x - 1, y)
fun Coord.right() = Coord(x + 1, y)
fun Coord.up() = Coord(x, y - 1)
fun Coord.down() = Coord(x, y + 1)
fun Coord.leftUp() = Coord(x-1, y - 1)
fun Coord.leftDown() = Coord(x-1, y + 1)
fun Coord.rightUp() = Coord(x+1, y - 1)
fun Coord.rightDown() = Coord(x+1, y + 1)


fun Coord.go(move: MatrixUtils.SimpleDirection) = when (move) {
    MatrixUtils.SimpleDirection.UP -> this.up()
    MatrixUtils.SimpleDirection.DOWN -> this.down()
    MatrixUtils.SimpleDirection.LEFT -> this.left()
    MatrixUtils.SimpleDirection.RIGHT -> this.right()
}

fun Coord.go(move: MatrixUtils.AdvancedDirection) = when (move) {
    MatrixUtils.AdvancedDirection.UP -> this.up()
    MatrixUtils.AdvancedDirection.DOWN -> this.down()
    MatrixUtils.AdvancedDirection.LEFT -> this.left()
    MatrixUtils.AdvancedDirection.RIGHT -> this.right()
    MatrixUtils.AdvancedDirection.LEFT_UP -> this.leftUp()
    MatrixUtils.AdvancedDirection.LEFT_DOWN -> this.leftDown()
    MatrixUtils.AdvancedDirection.RIGHT_UP -> this.rightUp()
    MatrixUtils.AdvancedDirection.RIGHT_DOWN -> this.rightDown()
}

data class Coordinated<T> (
    val coord: Coord,
    val data: T
)

object MatrixUtils {

    /**
     * Creates a Matrix for each line of the input x transform function for the input
     *
     * @param input     list of input lines of type [I]
     * @param transform the function applied to each line to create each cell of type [T] in the matrix
     * @return 2D-List of target type [T]
     */

    fun <T, I> createMatrix(input: List<I>, transform: (index: Int, line: I) -> List<T>): Matrix<T> {
        return input.mapIndexed { index, it ->
            transform(index, it)
        }
    }


    /**
     * Switches all columns and rows of a 2D-Array
     *
     * @param matrix Matrix of type [T]
     * @return transposed Matrix of Type [T]
     */
    fun <T> transposeMatrix(matrix: Matrix<T>): Matrix<T> {
        return List(matrix[0].size) { col ->
            List(matrix.size) { row ->
                matrix[row][col]
            }.toList()
        }.toList()
    }

    enum class SimpleDirection {
        UP,
        DOWN,
        LEFT,
        RIGHT
    }

    enum class AdvancedDirection {
        UP,
        DOWN,
        LEFT,
        RIGHT,
        LEFT_UP,
        LEFT_DOWN,
        RIGHT_UP,
        RIGHT_DOWN
    }

    fun <T> Matrix<Coordinated<T>>.move(from: Coordinated<T>, simpleDirection: SimpleDirection): Coordinated<T>? {
        val targetCoord = from.coord.go(simpleDirection)
        return this.flatten().find { element -> element.coord == targetCoord }
    }

    fun <T> Matrix<Coordinated<T>>.move(from: Coordinated<T>, advancedDirection: AdvancedDirection): Coordinated<T>? {
        val targetCoord = from.coord.go(advancedDirection)
        return this.flatten().find { element -> element.coord == targetCoord }
    }

    fun <T> Matrix<T>.forEachMatrixElement(transform: (element: T) -> Unit) {
        this.forEach { row -> row.forEach { transform(it) } }
    }

    fun <T> Matrix<T>.forEachMatrixElementParallel(transform: (element: T) -> Unit) {
        this.parallelStream().forEach { row -> row.parallelStream().forEach { transform(it) } }
    }

    fun <T> Matrix<T>.filterMatrixElement(predicate: (element: T) -> Boolean): List<T> {
        return this.flatten().filter{ predicate(it) }
    }

    fun <T> Matrix<T>.createDeepCopy(copyFunction: (T) -> T): Matrix<T> {
        return this.map { innerList -> innerList.map(copyFunction) }
    }

    fun <T> Matrix<T>.print(print: (T) -> Unit) {
        return this.forEach { row -> row.forEach(print)
            println() }
    }
}

