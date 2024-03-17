package Interpreter.Executors

import ASTN.AST
import Interpreter.Value
import java.util.Optional

interface Executor<T> where T: AST{

    fun validate(ast: AST): Boolean

    fun execute(ast: T, variables: MutableMap<String, Value>): String
}

