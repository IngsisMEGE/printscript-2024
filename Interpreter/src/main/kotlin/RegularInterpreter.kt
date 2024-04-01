package interpreter

import astn.AST
import astn.Assignation
import astn.Method
import astn.VarDeclaration
import astn.VarDeclarationAssignation
import interpreter.executors.AssignationExecution
import interpreter.executors.DeclarationAssignationExecution
import interpreter.executors.DeclarationExecution
import interpreter.executors.ExecutorsProvider
import interpreter.executors.PrintExecutor

class RegularInterpreter {
    val variables = mutableMapOf<String, Value>()
    private val executorsProvider = ExecutorsProvider()

    fun readAST(ast: AST): String {
        val executors = executorsProvider.getBuilderList()

        return when (ast) {
            is Assignation -> AssignationExecution().execute(ast, variables)
            is VarDeclaration -> DeclarationExecution().execute(ast, variables)
            is VarDeclarationAssignation -> DeclarationAssignationExecution().execute(ast, variables)
            is Method -> MethodExecutor().execute(ast, variables)
            else -> throw Exception("Unexpected structure")
        }
    }
}
