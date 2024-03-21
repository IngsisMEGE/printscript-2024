package Interpreter.Executors


import ASTN.VarDeclarationAssignation
import Interpreter.Value
import Interpreter.VariableType
import token.DataType

class DeclarationAssignationExecution: Executor<VarDeclarationAssignation> {
    private val binaryOperator = BinaryOperatorReader()

    override fun execute(ast: VarDeclarationAssignation, variables: MutableMap<String, Value>): String {
        val varName = ast.varDeclaration.assignation.getValue()
        val type = getValueType(ast.varDeclaration.type.getType())
        val value = binaryOperator.evaluate(ast.value, variables)
        if (!variables.containsKey(varName)){
            if (value.getType() == type){
                variables[varName] = value
                return ""
            }
            throw Exception("mal")
        }else{
            throw Exception("mal")
        }
    }

    private fun getValueType(dataType: DataType): VariableType{
        return when(dataType){
            DataType.NUMBER_KEYWORD -> VariableType.NUMBER
            DataType.STRING_KEYWORD -> VariableType.STRING
            else -> throw Exception("mal")
        }
    }
}