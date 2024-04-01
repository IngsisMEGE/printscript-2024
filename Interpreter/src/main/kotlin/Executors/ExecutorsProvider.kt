package interpreter.executors

import astn.AST

class ExecutorsProvider {
    private val executors: List<Executor<out AST>> =
        listOf(
            AssignationExecution(),
            DeclarationAssignationExecution(),
            DeclarationExecution(),
            MethodExecutor(),
        )

    fun getBuilderList() = executors
}
