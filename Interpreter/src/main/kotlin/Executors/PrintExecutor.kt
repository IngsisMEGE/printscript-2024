package interpreter.executors

import astn.Method
import interpreter.Value

/**
 * This class is responsible for executing the print method in the PrintScript application.
 * It implements the Executor interface and overrides the execute function.
 *
 * The execute function checks if the method name is "println". If it is, it evaluates the value of the method using a BinaryOperatorReader and returns the result as a string.
 * If the method name is not "println", it throws an Exception.
 *
 * @throws Exception If the method name is not "println".
 */
class PrintExecutor : Executor<Method> {
    private val binaryOperator = BinaryOperatorReader()

    override fun execute(
        ast: Method,
        variables: MutableMap<String, Value>,
    ): String {
        if (ast.methodName.getValue() == "println") {
            return binaryOperator.evaluate(ast.value, variables).getValue().toString()
        } else {
            throw Exception("Method not found")
        }
    }
}
