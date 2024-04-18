package interpreter.executors

import astn.OpTree
import astn.OperationBoolean
import astn.OperationHead
import astn.OperationInput
import astn.OperationNumber
import astn.OperationString
import astn.OperationVariable
import interpreter.Value
import interpreter.VariableType
import token.DataType
import token.Token
import java.util.Optional

/**
 * This class is responsible for evaluating binary operations in the PrintScript application.
 * It provides methods to evaluate different types of operations, including number operations, string operations, and variable operations.
 *
 * The evaluate function takes an OpTree and a map of variables as input. It uses a when expression to determine the type of the OpTree and calls the appropriate function to evaluate the operation.
 * The functions for evaluating number, string, and variable operations return a Value object that contains the result of the operation.
 *
 * The getVariable function retrieves the value of a variable from the variables map. It throws an Exception if the variable does not exist or if the variable's value is empty.
 *
 * The evaluateHead function evaluates an OperationHead, which represents a binary operation with a left operand, a right operand, and an operator. It calls the appropriate function to calculate the result based on the types of the operands and the operator.
 *
 * The calculateString function calculates the result of a string operation. It currently only supports the plus operator, which concatenates two strings.
 *
 * The calculateNumber function calculates the result of a number operation. It supports the plus, minus, multiply, and divide operators.
 *
 * @throws Exception If the OpTree is of an unexpected type, if a variable does not exist or its value is empty, if an unsupported operator is used, or if the operands are of incompatible types.
 */
class BinaryOperatorReader() {
    fun evaluate(
        binary: OpTree,
        variables: MutableMap<String, Value>,
        type: VariableType,
        loadInput: (String) -> String,
    ): Value {
        return when (binary) {
            is OperationNumber -> Value(VariableType.NUMBER, Optional.of(binary.value.getValue()), true)
            is OperationString -> Value(VariableType.STRING, Optional.of(binary.value.getValue()), true)
            is OperationBoolean -> Value(VariableType.BOOLEAN, Optional.of(binary.value.getValue()), true)
            is OperationVariable -> getVariable(binary.value.getValue(), variables)
            is OperationHead -> evaluateHead(binary, variables, type, loadInput)
            is OperationInput -> {
                val value = evaluate(binary.value, variables, type, loadInput)
                if (value.getType() != VariableType.STRING) {
                    throw Exception("Input value must be a string")
                }
                parseValue(loadInput(value.getValue()), type)
            }
            else -> throw Exception("Operation not found")
        }
    }

    private fun getVariable(
        name: String,
        variables: MutableMap<String, Value>,
    ): Value {
        if (variables.containsKey(name)) {
            if (!variables[name]!!.isEmpty()) {
                return Value(variables[name]!!.getType(), Optional.of(variables[name]!!.getValue()), variables[name]!!.isMutable())
            } else {
                throw Exception("Variable \"${name}\" not initialized")
            }
        } else {
            throw Exception("Variable \"${name}\" not found")
        }
    }

    private fun evaluateHead(
        binary: OperationHead,
        variables: MutableMap<String, Value>,
        type: VariableType,
        loadInput: (String) -> String,
    ): Value {
        val left = evaluate(binary.left, variables, type, loadInput)
        val right = evaluate(binary.right, variables, type, loadInput)
        return when {
            left.getType() == VariableType.BOOLEAN || right.getType() == VariableType.BOOLEAN -> throw Exception(
                "Operation between ${left.getType()} and ${right.getType()} not supported",
            )
            left.getType() == VariableType.STRING || right.getType() == VariableType.STRING -> calculateString(left, right, binary.operator)
            left.getType() == VariableType.NUMBER && right.getType() == VariableType.NUMBER -> calculateNumber(left, right, binary.operator)
            else -> throw Exception("Operation between ${left.getType()} and ${right.getType()} not supported")
        }
    }

    private fun calculateString(
        left: Value,
        right: Value,
        operator: Token,
    ): Value {
        return when (operator.getType()) {
            DataType.OPERATOR_PLUS -> Value(VariableType.STRING, Optional.of(left.getValue() + right.getValue()), false)
            else -> throw Exception("Operator for String not found")
        }
    }

    private fun calculateNumber(
        left: Value,
        right: Value,
        operator: Token,
    ): Value {
        val leftNumber = left.getValue().toDouble()
        val rightNumber = right.getValue().toDouble()
        return when (operator.getType()) {
            DataType.OPERATOR_PLUS -> {
                val result = leftNumber + rightNumber
                Value(
                    VariableType.NUMBER,
                    Optional.of(if (result % 1 == 0.0) result.toString().removeSuffix(".0") else result.toString()),
                    false,
                )
            }
            DataType.OPERATOR_MINUS -> {
                val result = leftNumber - rightNumber
                Value(
                    VariableType.NUMBER,
                    Optional.of(if (result % 1 == 0.0) result.toString().removeSuffix(".0") else result.toString()),
                    false,
                )
            }
            DataType.OPERATOR_MULTIPLY -> {
                val result = leftNumber * rightNumber
                Value(
                    VariableType.NUMBER,
                    Optional.of(if (result % 1 == 0.0) result.toString().removeSuffix(".0") else result.toString()),
                    false,
                )
            }
            DataType.OPERATOR_DIVIDE -> {
                val result = leftNumber / rightNumber
                Value(
                    VariableType.NUMBER,
                    Optional.of(if (result % 1 == 0.0) result.toString().removeSuffix(".0") else result.toString()),
                    false,
                )
            }
            else -> throw Exception("Operator for number not found")
        }
    }

    private fun parseValue(
        value: String,
        type: VariableType,
    ): Value {
        return when (type) {
            VariableType.STRING -> Value(type, Optional.of(value), true)
            VariableType.NUMBER -> {
                val number = value.toIntOrNull()
                if (number === null) {
                    throw IllegalArgumentException("El valor $value no es un número válido.")
                } else {
                    Value(type, Optional.of(number.toString()), true)
                }
            }
            VariableType.BOOLEAN -> {
                val booleanValue = value.toBooleanStrictOrNull()
                if (booleanValue === null) {
                    throw IllegalArgumentException("El valor $value no es un número válido.")
                } else {
                    Value(type, Optional.of(booleanValue.toString()), true)
                }
            }
        }
    }
}
