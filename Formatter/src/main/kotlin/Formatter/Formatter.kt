package Formatter

import ASTN.AST

interface Formatter {
    val rules : Map<String , Any>
    fun format(code: AST): String
}