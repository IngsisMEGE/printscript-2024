package Interpreter.Executors

import ASTN.AST
import ASTN.VarDeclaration
import ASTN.VarDeclarationAssignation
import Interpreter.Value
import Interpreter.VariableType
import Token.DataType

class DeclarationAssignationExecution: Executor<VarDeclarationAssignation> {
    private val binaryOperator = BinaryOperatorReader()
    override fun validate(ast: AST): Boolean {
        return ast is VarDeclaration
    }

    override fun execute(ast: VarDeclarationAssignation, variables: MutableMap<String, Value>): String {
        val varName = ast.varDeclaration.assignation.value
        val type = getValueType(ast.varDeclaration.type.type)
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