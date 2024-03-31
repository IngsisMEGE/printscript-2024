package formatter

import astn.AST

interface Formatter {
    val property: Map<String, Any>

    fun format(ast: AST): String
}

// poner \n antes de devolver la linea
