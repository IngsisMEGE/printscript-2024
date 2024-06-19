package interpreter.executors.utils.exceptions

class MethodNotFoundException(private val position: Pair<Int, Int>, private val methodName: String) : Exception() {
    override val message: String
        get() = "Method $methodName not found at position ${position.first} line ${position.second}"
}
