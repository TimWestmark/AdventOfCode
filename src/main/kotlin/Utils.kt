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

object MatrixUtils {

    /**
     * Creates a Matrix for each line of the input x transform function for the input
     *
     * @param input     list of input lines of type [I]
     * @param transform the function applied to each line to create each cell of type [T] in the matrix
     * @return 2D-List of target type [T]
     */

    fun <T, I> createMatrix(input: List<I>, transform: (line: I) -> List<T>): Matrix<T> {
        return input.map {
            transform(it)
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
}

