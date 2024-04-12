package interpreter.executors

import astn.Assignation
import interpreter.Value

class AssignationExecution : Executor<Assignation> {
    private val binaryOperator = BinaryOperatorReader()

    override fun execute(
        ast: Assignation,
        variables: MutableMap<String, Value>,
    ): String {
        val varName = ast.assignation.getValue()
        val type = variables[ast.assignation.getValue()]?.getType()
        val value = binaryOperator.evaluate(ast.value, variables)
        if (variables.containsKey(varName)) {
            if (type != null && value.getType() == type) {
                variables[varName] = value
                return ""
            }
            throw Exception("Variable type mismatch at Line ${ast.assignation.getInitialPosition().second}")
        } else {
            throw Exception("Variable not found at Line ${ast.assignation.getInitialPosition().second}")
        }
    }
}
