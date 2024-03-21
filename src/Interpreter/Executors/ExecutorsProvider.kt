package Interpreter.Executors

import ASTN.AST

class ExecutorsProvider {
    private val executors: List<Executor<out AST>> = listOf(
        AssignationExecution(),
        DeclarationAssignationExecution(),
        DeclarationExecution(),
        PrintExecutor()
    )

    fun getBuilderList() = executors
}