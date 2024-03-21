package Interpreter.Executors


import ASTN.Assignation
import Interpreter.Value
import Interpreter.VariableType
import token.DataType

class AssignationExecution: Executor<Assignation> {
    private val binaryOperator = BinaryOperatorReader()

    override fun execute(ast: Assignation, variables: MutableMap<String, Value>): String {
        val varName = ast.assignation.getValue()
        val type = getValueType(ast.assignation.getType())
        val value = binaryOperator.evaluate(ast.value, variables)
        if (variables.containsKey(varName)){
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