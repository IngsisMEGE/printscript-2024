package Interpreter

import ASTN.*
import Interpreter.Executors.*

class RegularInterpreter {
    val variables = mutableMapOf<String, Value>()
    private val executorsProvider = ExecutorsProvider()
    fun readAST(ast: AST): String{
        val executors = executorsProvider.getBuilderList()

        return when (ast){
            is Assignation -> AssignationExecution().execute(ast, variables)
            is VarDeclaration -> DeclarationExecution().execute(ast, variables)
            is VarDeclarationAssignation -> DeclarationAssignationExecution().execute(ast, variables)
            is Method -> PrintExecutor().execute(ast, variables)
            else -> throw Exception("Unexpected structure")
        }
    }
}