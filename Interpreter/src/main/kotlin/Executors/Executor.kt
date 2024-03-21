package Interpreter.Executors

import ASTN.AST
import Interpreter.Value

interface Executor<T> where T: AST{

    fun execute(ast: T, variables: MutableMap<String, Value>): String
}

