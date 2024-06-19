package interpreter.executors

import astn.Assignation
import astn.OperationMethod
import interpreter.Value
import interpreter.VariableType

class AssignationExecution : Executor<Assignation> {
    private val binaryOperator = BinaryOperatorReader()

    override fun execute(
        ast: Assignation,
        variables: MutableMap<String, Value>,
    ): String {
        val varName = ast.varName.getValue()
        val existingValue =
            variables[varName]
                ?: throw Exception("Variable '$varName' not found at Line ${ast.varName.getInitialPosition().second}")

        if (!existingValue.isMutable()) {
            throw Exception(
                "Cannot assign new value to constant '$varName' at Line" +
                    " ${ast.varName.getInitialPosition().first} : ${ast.varName.getInitialPosition().second}.",
            )
        }

        val newValue = binaryOperator.evaluate(ast.value, variables, existingValue.getType())

        if (existingValue.getType() != newValue.getType()) {
            throw Exception(
                "Variable type mismatch at Line ${ast.varName.getInitialPosition().second}" +
                    " between ${existingValue.getType()} and ${newValue.getType()}",
            )
        }

        variables[varName] = newValue
        return when (ast.value) {
            is OperationMethod -> {
                if ((ast.value as OperationMethod).methodName.getValue() == "readInput") {
                    binaryOperator.evaluate(
                        (ast.value as OperationMethod).value,
                        variables,
                        VariableType.STRING,
                    ).getValue() + "\n"
                } else {
                    ""
                }
            }
            else -> ""
        }
    }
}
