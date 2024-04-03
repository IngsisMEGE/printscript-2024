package lexer

import token.DataType

data class TokenRegexRule(private val pattern: String, private val type: DataType, private val isPatternLiteral: Boolean) {
    fun getPattern(): String {
        return pattern
    }

    fun getType(): DataType {
        return type
    }

    fun isPatternLiteral(): Boolean {
        return isPatternLiteral
    }
}
