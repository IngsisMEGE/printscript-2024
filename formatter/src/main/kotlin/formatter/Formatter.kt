package formatter

import astn.AST

interface Formatter {
    val property: Map<String, Any>

    fun format(ast: AST): String

    fun changeProperty(property: Map<String, Any>): Formatter
}
