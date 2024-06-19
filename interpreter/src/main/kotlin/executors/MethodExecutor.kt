package interpreter.executors

import astn.Method
import interpreter.Value
import interpreter.executors.methodExecutors.MethodProvider
import interpreter.executors.utils.exceptions.MethodNotFoundException

/**
 * This class is responsible for executing methods. It implements the Executor interface for the Method type.
 *
 * @property binaryOperator A private property of type BinaryOperatorReader. It is used to evaluate the value of the Method object.
 */
class MethodExecutor : Executor<Method> {
    private val methods = MethodProvider()

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
        val methodName = ast.methodName
        val method = methods.getMethod(methodName.getValue())
        if (method.isEmpty) {
            throw MethodNotFoundException(methodName.getInitialPosition(), methodName.getValue())
        }
        return method.get().execute(ast.value, variables)
    }
}
