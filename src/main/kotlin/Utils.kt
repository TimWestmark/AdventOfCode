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



typealias Matrix<T> = List<List<T>>

data class Coord(
    val x: Int,
    val y: Int,
)

fun Coord.left() = Coord(x - 1, y)
fun Coord.right() = Coord(x + 1, y)
fun Coord.up() = Coord(x, y + 1)
fun Coord.down() = Coord(x, y - 1)

fun Coord.go(move: MatrixUtils.SimpleDirection) = when (move) {
    MatrixUtils.SimpleDirection.UP -> this.up()
    MatrixUtils.SimpleDirection.DOWN -> this.down()
    MatrixUtils.SimpleDirection.LEFT -> this.left()
    MatrixUtils.SimpleDirection.RIGHT -> this.right()
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

    fun <T> Matrix<Coordinated<T>>.move(from: Coordinated<T>, simpleDirection: SimpleDirection): Coordinated<T>? {
        val targetCoord = from.coord.go(simpleDirection)
        return this.flatten().find { element -> element.coord == targetCoord }
    }

    fun <T> Matrix<T>.forEachMatrixElement(transform: (element: T) -> Unit) {
        this.forEach { row -> row.forEach { transform(it) } }
    }

    fun <T> Matrix<T>.filterMatrixElement(predicate: (element: T) -> Boolean): List<T> {
        return this.flatten().filter{ predicate(it) }
    }
}

