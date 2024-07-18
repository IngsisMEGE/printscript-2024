package interpreter

import astn.AST

interface Interpreter {
    fun readAST(
        ast: AST,
        storedVariables: MutableMap<String, Value>,
    ): String

    fun readASTWithEnv(
        ast: AST,
        storedVariables: MutableMap<String, Value>,
        envs: Map<String, String>,
    ): String
}
