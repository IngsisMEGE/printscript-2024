package interpreter

import astn.AST
import astn.Assignation
import astn.CloseIfStatement
import astn.EmptyAST
import astn.IfStatement
import astn.Method
import astn.VarDeclaration
import astn.VarDeclarationAssignation
import executors.DeclarationAssignationExecution
import executors.DeclarationExecution
import interpreter.executors.AssignationExecution
import interpreter.executors.IfExecutor
import interpreter.executors.MethodExecutor
import interpreter.executors.operationMethod.LoadInputHolder

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
class InterpreterImpl(
    private val loadInput: () -> String,
    private val enterIfScope: () -> Unit = {},
    private val mergeScope: () -> Unit = {},
) : Interpreter {
    private var conditionsIfScopes: List<Boolean> = listOf()
    private var insideIf = false
    private var insideElse = false

    init {
        LoadInputHolder.loadInput = loadInput
    }

    override fun readAST(
        ast: AST,
        storedVariables: MutableMap<String, Value>,
    ): String {
        if (!shouldProcessLine(ast)) {
            return ""
        }
        return when (ast) {
            is EmptyAST -> ""
            is Assignation -> AssignationExecution().execute(ast, storedVariables)
            is VarDeclaration -> DeclarationExecution().execute(ast, storedVariables)
            is VarDeclarationAssignation -> DeclarationAssignationExecution().execute(ast, storedVariables)
            is Method -> MethodExecutor().execute(ast, storedVariables)
            is IfStatement -> {
                val ifExecutor = IfExecutor()
                val result = ifExecutor.execute(ast, storedVariables)
                conditionsIfScopes = ifExecutor.addCondition(conditionsIfScopes, result.toBoolean())
                insideIf = true
                enterIfScope()
                ""
            }
            is CloseIfStatement -> {
                if (!ast.isElse) {
                    conditionsIfScopes = dropLastCondition(conditionsIfScopes)
                    mergeScope()
                }
                insideIf = false
                insideElse = ast.isElse
                ""
            }
            else -> throw Exception("Unexpected structure ${ast::class.simpleName} is not supported")
        }
    }

    private fun shouldProcessLine(ast: AST): Boolean {
        return if (insideIf) {
            conditionsIfScopes.isNotEmpty() && conditionsIfScopes.last() || ast is CloseIfStatement
        } else if (insideElse) {
            conditionsIfScopes.isNotEmpty() && !conditionsIfScopes.last() || ast is CloseIfStatement
        } else {
            true
        }
    }

    private fun dropLastCondition(conditions: List<Boolean>): List<Boolean> {
        val newConditions = conditions.toMutableList()
        newConditions.removeLast()
        return newConditions
    }
}
