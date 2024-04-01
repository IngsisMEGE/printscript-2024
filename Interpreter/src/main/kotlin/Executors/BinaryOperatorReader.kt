package interpreter.executors

import astn.OpTree
import astn.OperationHead
import astn.OperationNumber
import astn.OperationString
import astn.OperationVariable
import interpreter.Value
import interpreter.VariableType
import token.DataType
import token.Token
import java.util.Optional

class BinaryOperatorReader() {
    fun evaluate(
        binary: OpTree,
        variables: MutableMap<String, Value>,
    ): Value {
        return when (binary) {
            is OperationNumber -> Value(VariableType.NUMBER, Optional.of(binary.value.getValue()))
            is OperationString -> Value(VariableType.STRING, Optional.of(binary.value.getValue()))
            is OperationVariable -> getVariable(binary.value.getValue(), variables)
            is OperationHead -> evaluateHead(binary, variables)
            else -> throw Exception("Operation not found")
        }
    }

    private fun getVariable(
        name: String,
        variables: MutableMap<String, Value>,
    ): Value {
        if (variables.containsKey(name)) {
            if (!variables[name]!!.isEmpty()) {
                return Value(variables[name]!!.getType(), Optional.of(variables[name]!!.getValue()))
            } else {
                throw Exception("Variable not initialized")
            }
        } else {
            throw Exception("Variable not found")
        }
    }

    private fun evaluateHead(
        binary: OperationHead,
        variables: MutableMap<String, Value>,
    ): Value {
        val left = evaluate(binary.left, variables)
        val right = evaluate(binary.right, variables)
        return when {
            left.getType() == VariableType.STRING || right.getType() == VariableType.STRING -> calculateString(left, right, binary.operator)
            left.getType() == VariableType.NUMBER && right.getType() == VariableType.NUMBER -> calculateNumber(left, right, binary.operator)
            else -> throw Exception("Type Mismatch")
        }
    }

    private fun calculateString(
        left: Value,
        right: Value,
        operator: Token,
    ): Value {
        return when (operator.getType()) {
            DataType.OPERATOR_PLUS -> Value(VariableType.STRING, Optional.of(left.getValue() + right.getValue()))
            else -> throw Exception("Operator for String not found")
        }
    }

    private fun calculateNumber(
        left: Value,
        right: Value,
        operator: Token,
    ): Value {
        val leftNumber = left.getValue().toInt()
        val rightNumber = right.getValue().toInt()
        return when (operator.getType()) {
            DataType.OPERATOR_PLUS -> Value(VariableType.NUMBER, Optional.of((leftNumber + rightNumber).toString()))
            DataType.OPERATOR_MINUS -> Value(VariableType.NUMBER, Optional.of((leftNumber - rightNumber).toString()))
            DataType.OPERATOR_MULTIPLY -> Value(VariableType.NUMBER, Optional.of((leftNumber * rightNumber).toString()))
            DataType.OPERATOR_DIVIDE -> Value(VariableType.NUMBER, Optional.of((leftNumber / rightNumber).toString()))
            else -> throw Exception("Operator for number not found")
        }
    }
}
