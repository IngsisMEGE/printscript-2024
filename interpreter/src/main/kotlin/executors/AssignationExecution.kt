package interpreter.executors

import astn.Assignation
import interpreter.Value

class AssignationExecution(private val loadInput: (String) -> String) : Executor<Assignation> {
    private val binaryOperator = BinaryOperatorReader()

    override fun execute(
        ast: Assignation,
        variables: MutableMap<String, Value>,
    ): String {
        val varName = ast.assignation.getValue()
        val existingValue = variables[varName]

        if (existingValue == null) {
            throw Exception("Variable '$varName' not found at Line ${ast.assignation.getInitialPosition().second}")
        }

        val newValue = binaryOperator.evaluate(ast.value, variables, existingValue.getType(), loadInput)

        if (!existingValue.isMutable()) {
            throw Exception("Cannot assign new value to constant '$varName'.")
        }

        if (existingValue.getType() != newValue.getType()) {
            throw Exception(
                "Variable type mismatch at Line ${ast.assignation.getInitialPosition().second}" +
                    " between ${existingValue.getType()} and ${newValue.getType()}",
            )
        }

        variables[varName] = newValue
        return ""
    }
}
