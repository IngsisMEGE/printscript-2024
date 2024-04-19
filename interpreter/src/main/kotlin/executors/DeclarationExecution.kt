package executors

import astn.VarDeclaration
import interpreter.Value
import interpreter.VariableType
import interpreter.executors.Executor
import token.DataType
import java.util.Optional

/**
 * This class is responsible for executing variable declarations in the PrintScript application.
 * It implements the Executor interface and overrides the execute function.
 *
 * The execute function takes a VarDeclaration AST and a map of variables as input. It retrieves the variable name and type from the AST,
 * and checks if the variable already exists in the map. If it does not, it adds the variable to the map with an empty value and returns an empty string.
 * If the variable already exists, it throws an Exception.
 *
 * The getValueType function takes a DataType as input and returns the corresponding VariableType. It uses a when expression to determine the VariableType.
 *
 * @throws Exception If the variable already exists or if the DataType is not NUMBER_TYPE or STRING_TYPE.
 */
class DeclarationExecution : Executor<VarDeclaration> {
    override fun execute(
        ast: VarDeclaration,
        variables: MutableMap<String, Value>,
    ): String {
        val varName = ast.varName.getValue()
        val type = getValueType(ast)
        if (!variables.containsKey(varName)) {
            variables[varName] = Value(type, Optional.empty())
            return ""
        } else {
            throw Exception("Variable Already Exists at Line ${ast.varName.getInitialPosition().second}")
        }
    }

    private fun getValueType(ast: VarDeclaration): VariableType {
        return when (ast.type.getType()) {
            DataType.NUMBER_TYPE -> VariableType.NUMBER
            DataType.STRING_TYPE -> VariableType.STRING
            else -> throw Exception("Unexpected type at Line ${ast.type.getInitialPosition().second}")
        }
    }
}
