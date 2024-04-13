package analyzers

import astn.AST

interface Analyzer {
    fun analyze(ast: AST): String
}
