package interpreter.executors

import astn.OpTree
import astn.OperationBoolean
import astn.OperationHead
import astn.OperationMethod
import astn.OperationNumber
import astn.OperationString
import astn.OperationVariable
import interpreter.Value
import interpreter.VariableType
import interpreter.executors.operationMethod.OperationMethodProvider
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
    private val operationMethodProvider = OperationMethodProvider()

    fun evaluate(
        binary: OpTree,
        variables: MutableMap<String, Value>,
        type: VariableType,
    ): Value {
        return when (binary) {
            is OperationNumber -> Value(VariableType.NUMBER, Optional.of(binary.value.getValue()))
            is OperationString -> Value(VariableType.STRING, Optional.of(binary.value.getValue()))
            is OperationBoolean -> Value(VariableType.BOOLEAN, Optional.of(binary.value.getValue()))
            is OperationVariable -> getVariable(binary.varName, variables)
            is OperationHead -> evaluateHead(binary, variables, type)
            is OperationMethod -> {
                val value = evaluate(binary.value, variables, type)
                val operation = operationMethodProvider.getOperationMethod(binary.methodName)
                operation.execute(binary.methodName, listOf(value), type)
            }
            else -> throw Exception("Operation not found")
        }
    }

    private fun getVariable(
        varName: Token,
        variables: MutableMap<String, Value>,
    ): Value {
        val name = varName.getValue()
        if (variables.containsKey(name)) {
            if (!variables[name]!!.isEmpty()) {
                return Value(variables[name]!!.getType(), Optional.of(variables[name]!!.getValue()), variables[name]!!.isMutable())
            } else {
                throw Exception(
                    "Variable $name not initialized at Line ${varName.getInitialPosition().first} " +
                        ": ${varName.getInitialPosition().second} ",
                )
            }
        } else {
            throw Exception(
                "Variable $name not found at Line ${varName.getInitialPosition().first} " +
                    ": ${varName.getInitialPosition().second} ",
            )
        }
    }

    private fun evaluateHead(
        binary: OperationHead,
        variables: MutableMap<String, Value>,
        type: VariableType,
    ): Value {
        val left = evaluate(binary.left, variables, type)
        val right = evaluate(binary.right, variables, type)
        return when {
            left.getType() == VariableType.BOOLEAN || right.getType() == VariableType.BOOLEAN -> throw Exception(
                "Operation between ${left.getType()} and ${right.getType()} not supported",
            )
            left.getType() == VariableType.STRING || right.getType() == VariableType.STRING -> calculateString(left, right, binary.operator)
            left.getType() == VariableType.NUMBER && right.getType() == VariableType.NUMBER -> calculateNumber(left, right, binary.operator)
            else -> throw Exception(
                "Operation between ${left.getType()} and ${right.getType()} " +
                    "not supported at Line ${binary.operator.getInitialPosition().second}\",",
            )
        }
    }

    private fun calculateString(
        left: Value,
        right: Value,
        operator: Token,
    ): Value {
        return when (operator.getType()) {
            DataType.OPERATOR_PLUS -> Value(VariableType.STRING, Optional.of(left.getValue() + right.getValue()))
            else -> throw Exception(
                "Operator ${operator.getValue()} for String not found at Line  " +
                    "${operator.getInitialPosition().first} : ${operator.getInitialPosition().second}",
            )
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
                    getValueOfOperation(result),
                )
            }
            DataType.OPERATOR_MINUS -> {
                val result = leftNumber - rightNumber
                Value(
                    VariableType.NUMBER,
                    getValueOfOperation(result),
                )
            }
            DataType.OPERATOR_MULTIPLY -> {
                val result = leftNumber * rightNumber
                Value(
                    VariableType.NUMBER,
                    getValueOfOperation(result),
                )
            }
            DataType.OPERATOR_DIVIDE -> {
                val result = leftNumber / rightNumber
                Value(
                    VariableType.NUMBER,
                    getValueOfOperation(result),
                )
            }
            else -> throw Exception(
                "Operator ${operator.getValue()} for number not found at Line ${operator.getInitialPosition().first} " +
                    ": ${operator.getInitialPosition().second}",
            )
        }
    }

    private fun getValueOfOperation(result: Double): Optional<String> {
        return Optional.of(if (result % 1 == 0.0) result.toString().removeSuffix(".0") else result.toString())
    }
}
