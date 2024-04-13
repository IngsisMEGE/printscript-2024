package interpreter.executors.utils

import interpreter.VariableType
import token.DataType

class ValueTypeAdapter {
    companion object {
        fun transformDataTypeToValueType(
            type: DataType,
            initialPosition: Pair<Int, Int>,
        ): VariableType {
            return when (type) {
                DataType.STRING_TYPE -> VariableType.STRING
                DataType.NUMBER_TYPE -> VariableType.NUMBER
                DataType.BOOLEAN_TYPE -> VariableType.BOOLEAN
                else -> throw Exception("Unexpected type at Line ${initialPosition.second}")
            }
        }
    }
}
