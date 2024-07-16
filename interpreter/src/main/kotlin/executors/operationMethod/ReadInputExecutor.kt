package interpreter.executors.operationMethod

import interpreter.Value
import interpreter.VariableType
import token.Token

class ReadInputExecutor(val parseValue: (String, VariableType) -> Value) : OperationMethod {
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
}

object LoadInputHolder {
    lateinit var loadInput: (String) -> String
}
