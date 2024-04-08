package interpreter

import astn.AST
import astn.Assignation
import astn.EmptyAST
import astn.Method
import astn.VarDeclaration
import astn.VarDeclarationAssignation
import executors.DeclarationExecution
import interpreter.executors.AssignationExecution
import interpreter.executors.DeclarationAssignationExecution
import interpreter.executors.ExecutorsProvider
import interpreter.executors.MethodExecutor

/**
 * This class is responsible for interpreting the Abstract Syntax Tree (AST) in the PrintScript application.
 * It maintains a map of variables and their values, and uses a provider to get the list of executors.
 *
 * The readAST function takes an AST as input and returns the result of executing the corresponding executor on the AST.
 * It uses a when expression to determine the type of the AST and calls the appropriate executor's execute function.
 * The executors modify the variables map and return a string result.
 *
 * @throws Exception If the AST is of an unexpected type.
 */
class RegularInterpreter {
    private val variables = mutableMapOf<String, Value>()
    private val executorsProvider = ExecutorsProvider()

    fun readAST(ast: AST): String {
        return when (ast) {
            is EmptyAST -> ""
            is Assignation -> AssignationExecution().execute(ast, variables)
            is VarDeclaration -> DeclarationExecution().execute(ast, variables)
            is VarDeclarationAssignation -> DeclarationAssignationExecution().execute(ast, variables)
            is Method -> MethodExecutor().execute(ast, variables)
            else -> throw Exception("Unexpected structure")
        }
    }
}
