package exceptions

class MustEndWithSeparator(private val position: Pair<Int, Int>) : Exception() {
    override val message: String
        get() = "Must end with separator at position ${position.first} line ${position.second}"
}
