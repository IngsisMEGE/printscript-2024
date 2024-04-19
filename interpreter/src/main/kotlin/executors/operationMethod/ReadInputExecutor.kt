package interpreter.executors.operationMethod

import interpreter.Value
import interpreter.VariableType
import token.Token
import java.util.Optional

class ReadInputExecutor : OperationMethod {
    private val loadInput = LoadInputHolder.loadInput

    override fun canExecute(methodName: String): Boolean {
        return methodName == "readInput"
    }

    override fun execute(
        methodName: Token,
        values: List<Value>,
        type: VariableType,
    ): Value {
        if (values.size != 1) {
            throw IllegalArgumentException(
                "${methodName.getValue()} only takes 1 argument but ${values.size} were provided" +
                    " Line ${methodName.getInitialPosition().first} : ${methodName.getInitialPosition().second}.",
            )
        }
        val value = values[0]
        if (value.getType() != VariableType.STRING) {
            throw IllegalArgumentException(
                "${methodName.getValue()} only takes String as argument but ${value.getType()} was provided" +
                    " Line ${methodName.getInitialPosition().first} : ${methodName.getInitialPosition().second}.",
            )
        }
        return parseValue(loadInput(value.getValue()), type)
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

object LoadInputHolder {
    lateinit var loadInput: (String) -> String
}
