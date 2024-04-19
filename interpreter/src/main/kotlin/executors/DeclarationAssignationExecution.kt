package executors

import astn.VarDeclaration
import astn.VarDeclarationAssignation
import interpreter.Value
import interpreter.VariableType
import interpreter.executors.BinaryOperatorReader
import interpreter.executors.Executor
import token.DataType

class DeclarationAssignationExecution : Executor<VarDeclarationAssignation> {
    private val binaryOperator = BinaryOperatorReader()

    override fun execute(
        ast: VarDeclarationAssignation,
        variables: MutableMap<String, Value>,
    ): String {
        val varName = ast.varDeclaration.varName.getValue()
        val type = getValueType(ast.varDeclaration)
        val value = binaryOperator.evaluate(ast.value, variables)
        if (!variables.containsKey(varName)) {
            if (value.getType() == type) {
                variables[varName] = value
                return ""
            }
            throw Exception("Type Mismatch at Line ${ast.varDeclaration.type.getInitialPosition().second}")
        } else {
            throw Exception("Variable Already Exists at Line ${ast.varDeclaration.varName.getInitialPosition().second}")
        }
    }

    private fun getValueType(ast: VarDeclaration): VariableType {
        return when (ast.type.getType()) {
            DataType.NUMBER_TYPE -> VariableType.NUMBER
            DataType.STRING_TYPE -> VariableType.STRING
            else -> throw Exception("Unexpected Type at Line ${ast.type.getInitialPosition().second}")
        }
    }
}
