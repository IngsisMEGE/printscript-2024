package Formatter

interface Formatter {
    val rules : Map<String , Any>
    fun format(code: String): String
}