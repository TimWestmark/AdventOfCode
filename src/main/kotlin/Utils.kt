
fun getInputLines(resource: String): List<String> {
    return object {}.javaClass.getResourceAsStream(resource)?.bufferedReader()!!.readLines()
}
