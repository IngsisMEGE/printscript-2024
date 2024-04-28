package interpreter.executors.operationMethod

import interpreter.Value
import interpreter.VariableType
import token.Token

interface OperationMethod {
    fun canExecute(methodName: String): Boolean

    fun execute(
        methodName: Token,
        values: List<Value>,
        type: VariableType,
    ): Value
}
