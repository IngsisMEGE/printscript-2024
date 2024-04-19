package interpreter.executors

import astn.IfStatement
import astn.OperationBoolean
import astn.OperationVariable
import interpreter.Value
import interpreter.VariableType
import token.Token

class IfExecutor() : Executor<IfStatement> {
    override fun execute(
        ast: IfStatement,
        variables: MutableMap<String, Value>,
    ): String {
        when (ast.condition) {
            is OperationVariable -> {
                val opVariable = ast.condition as OperationVariable
                evaluateVariable(opVariable.varName, variables)
                return variables[opVariable.varName.getValue()]!!.getValue()
            }

            is OperationBoolean -> {
                val opBoolean = ast.condition as OperationBoolean
                evaluateBoolean(opBoolean.value)
                return opBoolean.value.getValue()
            }

            else -> throw Exception("Unexpected structure")
        }
    }

    private fun evaluateVariable(
        variable: Token,
        variables: MutableMap<String, Value>,
    ) {
        if (!variables.containsKey(
                variable.getValue(),
            )
        ) {
            throw Exception(
                "Variable ${variable.getValue()} not found at ${variable.getInitialPosition().first} " +
                    ": ${variable.getInitialPosition().second}",
            )
        }

        val value = variables[variable.getValue()]

        if (value != null) {
            if (value.getType() != VariableType.BOOLEAN) {
                throw Exception(
                    "Variable ${variable.getValue()} is not a boolean at ${variable.getInitialPosition().first} " +
                        ": ${variable.getInitialPosition().second}",
                )
            }
        } else {
            throw Exception(
                "Variable ${variable.getValue()} does not have a value, at ${variable.getInitialPosition().first} " +
                    ": ${variable.getInitialPosition().second}",
            )
        }
    }

    private fun evaluateBoolean(bool: Token) {
        if (bool.getValue() == "true") return
    }

    fun addCondition(
        conditions: List<Boolean>,
        condition: Boolean,
    ): List<Boolean> {
        val newConditions = conditions.toMutableList()
        newConditions.add(condition)
        return newConditions
    }
}
