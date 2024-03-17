package Interpreter.Executors

import ASTN.AST
import Interpreter.Value
import java.util.Optional

interface Executor<T> where T: AST{

    fun execute(ast: T, variables: MutableMap<String, Value>): String
}

