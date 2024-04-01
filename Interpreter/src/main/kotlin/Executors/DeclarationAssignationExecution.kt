package interpreter.executors

import astn.VarDeclarationAssignation
import interpreter.Value
import interpreter.VariableType
import token.DataType

class DeclarationAssignationExecution : Executor<VarDeclarationAssignation> {
    private val binaryOperator = BinaryOperatorReader()

    override fun execute(
        ast: VarDeclarationAssignation,
        variables: MutableMap<String, Value>,
    ): String {
        val varName = ast.varDeclaration.assignation.getValue()
        val type = getValueType(ast.varDeclaration.type.getType())
        val value = binaryOperator.evaluate(ast.value, variables)
        if (!variables.containsKey(varName)) {
            if (value.getType() == type) {
                variables[varName] = value
                return ""
            }
            throw Exception("Type Mismatch")
        } else {
            throw Exception("Variable Already Exists")
        }
    }

    private fun getValueType(dataType: DataType): VariableType {
        return when (dataType) {
            DataType.NUMBER_TYPE -> VariableType.NUMBER
            DataType.STRING_TYPE -> VariableType.STRING
            else -> throw Exception("Unexpected Type")
        }
    }
}
