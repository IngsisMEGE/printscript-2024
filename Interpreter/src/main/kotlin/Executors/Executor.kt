package interpreter.executors

import astn.AST
import interpreter.Value

interface Executor<T> where T : AST {
    fun execute(
        ast: T,
        variables: MutableMap<String, Value>,
    ): String
}
