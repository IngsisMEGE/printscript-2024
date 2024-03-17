package Interpreter.Executors

import ASTN.AST
import ASTN.Method
import Interpreter.Value

class PrintExecutor: Executor<Method> {
    private val binaryOperator = BinaryOperatorReader()
    override fun validate(ast: AST): Boolean {
        return (ast is Method && ast.methodName.value == "println")
    }

    override fun execute(ast: Method, variables: MutableMap<String, Value>): String {
        return binaryOperator.evaluate(ast.value, variables).getValue().toString()
    }
}