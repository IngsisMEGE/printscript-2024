package interpreter

import astn.AST

interface Interpreter {
    fun readAST(
        ast: AST,
        storedVariables: MutableMap<String, Value>,
    ): String
}
