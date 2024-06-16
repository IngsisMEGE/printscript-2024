
import astn.OpTree
import astn.OperationMethod
import interpreter.Value
import interpreter.VariableType
import interpreter.executors.BinaryOperatorReader
import interpreter.executors.methodExecutors.Methods

class PrintLnMethod : Methods {
    private val binaryOperator = BinaryOperatorReader()

    override fun canExecute(methodName: String): Boolean {
        return methodName == "println"
    }

    override fun execute(
        value: OpTree,
        variables: MutableMap<String, Value>,
    ): String {
        return when (value) {
            is OperationMethod -> {
                if (value.methodName.getValue() == "readInput") {
                    binaryOperator.evaluate((value).value, variables, VariableType.STRING).getValue() +
                        "\n" + binaryOperator.evaluate(value, variables, VariableType.STRING).getValue() + "\n"
                } else {
                    binaryOperator.evaluate(value, variables, VariableType.STRING).getValue() + "\n"
                }
            }
            else -> binaryOperator.evaluate(value, variables, VariableType.STRING).getValue() + "\n"
        }
    }
}
