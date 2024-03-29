package Interpreter.Executors

import ASTN.Method
import Interpreter.Value

class MethodExecutor: Executor<Method> {
    private val binaryOperator = BinaryOperatorReader()

    override fun execute(ast: Method, variables: MutableMap<String, Value>): String {
        if(ast.methodName.getValue() == "println"){
            return binaryOperator.evaluate(ast.value, variables).getValue()
        }else{
            throw Exception("Method not found")
        }
    }
}