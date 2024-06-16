package interpreter.executors.operationMethod

import interpreter.Value
import interpreter.VariableType
import token.Token
import java.util.*

class OperationMethodProvider {
    private val operationMethods =
        listOf(
            ReadInputExecutor(::parseValue),
            ReadEnvExecutor(::parseValue),
        )

    fun getOperationMethod(methodName: Token): OperationMethod {
        return operationMethods.firstOrNull { it.canExecute(methodName.getValue()) }
            ?: throw IllegalArgumentException(
                "Method $methodName not found at" +
                    " Line ${methodName.getInitialPosition().first} ${methodName.getInitialPosition().second}.",
            )
    }

    private fun parseValue(
        value: String,
        type: VariableType,
    ): Value {
        return when (type) {
            VariableType.STRING -> Value(type, Optional.of(value))
            VariableType.NUMBER -> {
                val number = value.toIntOrNull()
                if (number === null) {
                    throw IllegalArgumentException("El valor $value no es un número válido.")
                } else {
                    Value(type, Optional.of(number.toString()))
                }
            }
            VariableType.BOOLEAN -> {
                val booleanValue = value.toBooleanStrictOrNull()
                if (booleanValue === null) {
                    throw IllegalArgumentException("El valor $value no es un número válido.")
                } else {
                    Value(type, Optional.of(booleanValue.toString()))
                }
            }
        }
    }
}
