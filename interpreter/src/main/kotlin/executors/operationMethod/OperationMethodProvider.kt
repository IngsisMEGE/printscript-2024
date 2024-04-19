package interpreter.executors.operationMethod

import token.Token

class OperationMethodProvider {
    private val operationMethods =
        listOf(
            ReadInputExecutor(),
        )

    fun getOperationMethod(methodName: Token): OperationMethod {
        return operationMethods.firstOrNull { it.canExecute(methodName.getValue()) }
            ?: throw IllegalArgumentException(
                "Method $methodName not found at" +
                    " Line ${methodName.getInitialPosition().first} ${methodName.getInitialPosition().second}.",
            )
    }
}
