package interpreter.executors

import astn.Method
import astn.OperationInput
import interpreter.Value
import interpreter.VariableType

/**
 * This class is responsible for executing methods. It implements the Executor interface for the Method type.
 *
 * @property binaryOperator A private property of type BinaryOperatorReader. It is used to evaluate the value of the Method object.
 */
class MethodExecutor(private val loadInput: (String) -> String) : Executor<Method> {
    private val binaryOperator = BinaryOperatorReader()

    /**
     * This method is responsible for executing the Method object.
     *
     * @param ast The Method object to be executed.
     * @param variables A MutableMap containing variable names as keys and their corresponding values.
     * @return The result of the execution as a String.
     * @throws Exception If the methodName of the ast object is not "println", an exception is thrown with the message "Method not found".
     */
    override fun execute(
        ast: Method,
        variables: MutableMap<String, Value>,
    ): String {
        if (ast.methodName.getValue() == "println") {
            return when (ast.value) {
                is OperationInput ->
                    binaryOperator.evaluate((ast.value as OperationInput).value, variables, VariableType.STRING, loadInput).getValue() +
                        "\n" + binaryOperator.evaluate(ast.value, variables, VariableType.STRING, loadInput).getValue() + "\n"
                else -> binaryOperator.evaluate(ast.value, variables, VariableType.STRING, loadInput).getValue() + "\n"
            }
        } else {
            throw Exception("Method not found")
        }
    }
}
