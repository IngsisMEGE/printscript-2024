package interpreter.executors

import astn.AST
import executors.DeclarationExecution

/**
 * This class is responsible for executing variable declaration assignations in the PrintScript application.
 * It implements the Executor interface and overrides the execute function.
 *
 * The execute function takes a VarDeclarationAssignation AST and a map of variables as input. It retrieves the variable name and type from the AST,
 * and checks if the variable already exists in the map. If it does not, it evaluates the value of the variable using a BinaryOperatorReader and checks if the evaluated value's type matches the declared type.
 * If the types match, it adds the variable to the map and returns an empty string. If the types do not match or if the variable already exists, it throws an Exception.
 *
 * The getValueType function takes a DataType as input and returns the corresponding VariableType. It uses a when expression to determine the VariableType.
 *
 * @throws Exception If the variable already exists, if the declared type does not match the evaluated value's type, or if the DataType is not NUMBER_TYPE or STRING_TYPE.
 */
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
