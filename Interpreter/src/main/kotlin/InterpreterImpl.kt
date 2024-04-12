package interpreter

import astn.AST
import astn.Assignation
import astn.EmptyAST
import astn.Method
import astn.VarDeclaration
import astn.VarDeclarationAssignation
import executors.DeclarationAssignationExecution
import executors.DeclarationExecution
import interpreter.executors.AssignationExecution
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
class InterpreterImpl : Interpreter {
    override fun readAST(
        ast: AST,
        storedVariables: MutableMap<String, Value>,
    ): String {
        return when (ast) {
            is EmptyAST -> ""
            is Assignation -> AssignationExecution().execute(ast, storedVariables)
            is VarDeclaration -> DeclarationExecution().execute(ast, storedVariables)
            is VarDeclarationAssignation -> DeclarationAssignationExecution().execute(ast, storedVariables)
            is Method -> MethodExecutor().execute(ast, storedVariables)
            else -> throw Exception("Unexpected structure")
        }
    }
}
