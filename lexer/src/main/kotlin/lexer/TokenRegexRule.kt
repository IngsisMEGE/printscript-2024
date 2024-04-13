package lexer

import token.DataType

data class TokenRegexRule(private val pattern: String, private val type: DataType) {
    fun getPattern(): String {
        return pattern
    }

    fun getType(): DataType {
        return type
    }
}
