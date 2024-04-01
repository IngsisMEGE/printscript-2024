package interpreter.executors

import astn.Assignation
import interpreter.Value
import interpreter.VariableType
import token.DataType

/**
 * This class is responsible for executing variable assignations in the PrintScript application.
 * It implements the Executor interface and overrides the execute function.
 *
 * The execute function takes an Assignation AST and a map of variables as input. It retrieves the variable name and type from the AST,
 * and checks if the variable already exists in the map. If it does, it evaluates the value of the variable using a BinaryOperatorReader and checks if the evaluated value's type matches the declared type.
 * If the types match, it updates the variable in the map and returns an empty string. If the types do not match or if the variable does not exist, it throws an Exception.
 *
 * The getValueType function takes a DataType as input and returns the corresponding VariableType. It uses a when expression to determine the VariableType.
 *
 * @throws Exception If the variable does not exist, if the declared type does not match the evaluated value's type, or if the DataType is not NUMBER_TYPE or STRING_TYPE.
 */
class AssignationExecution : Executor<Assignation> {
    private val binaryOperator = BinaryOperatorReader()

    override fun execute(
        ast: Assignation,
        variables: MutableMap<String, Value>,
    ): String {
        val varName = ast.assignation.getValue()
        val type = getValueType(ast.assignation.getType())
        val value = binaryOperator.evaluate(ast.value, variables)
        if (variables.containsKey(varName)) {
            if (value.getType() == type) {
                variables[varName] = value
                return ""
            }
            throw Exception("Variable type mismatch")
        } else {
            throw Exception("Variable not found")
        }
    }

    private fun getValueType(dataType: DataType): VariableType {
        return when (dataType) {
            DataType.NUMBER_TYPE -> VariableType.NUMBER
            DataType.STRING_TYPE -> VariableType.STRING
            else -> throw Exception("Unexpected type")
        }
    }
}
