package interpreter.executors.operationMethod

import interpreter.Value
import interpreter.VariableType
import token.Token
import java.util.*

class ReadEnvExecutor(val parseValue: (String, VariableType) -> Value) : OperationMethod {
    override fun canExecute(methodName: String): Boolean {
        return methodName == "readEnv"
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

        if (values[0].getType() != VariableType.STRING) {
            throw IllegalArgumentException(
                "${methodName.getValue()} only takes String as argument but ${values[0].getType()} was provided" +
                    " Line ${methodName.getInitialPosition().first} : ${methodName.getInitialPosition().second}.",
            )
        }

        val envVariable = System.getProperty(values[0].getValue())

        if (envVariable === null) {
            throw IllegalArgumentException(
                "The environment variable ${values[0].getValue()} does not exist" +
                    " Line ${methodName.getInitialPosition().first} : ${methodName.getInitialPosition().second}.",
            )
        }

        return parseValue(envVariable, type)
    }
}
