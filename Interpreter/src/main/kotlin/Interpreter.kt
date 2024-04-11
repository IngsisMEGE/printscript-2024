package interpreter

import astn.AST

interface Interpreter {
    fun readAST(ast: AST): String
}
