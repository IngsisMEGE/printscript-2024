package interpreter

import java.util.Optional

class Value(private val type: VariableType, private val result: Optional<String>) {
    fun isEmpty(): Boolean {
        return result.isEmpty
    }

    fun getValue(): String {
        return if (result.isEmpty) {
            ""
        } else {
            result.get()
        }
    }

    fun getType(): VariableType {
        return type
    }
}

enum class VariableType {
    NUMBER,
    STRING,
}
