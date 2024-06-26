package interpreter

import java.util.Optional

class Value(private val type: VariableType, private val result: Optional<String>, private val isMutable: Boolean = true) {
    fun isEmpty(): Boolean {
        return result.isEmpty
    }

    fun isMutable(): Boolean {
        return isMutable
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
    BOOLEAN,
}
