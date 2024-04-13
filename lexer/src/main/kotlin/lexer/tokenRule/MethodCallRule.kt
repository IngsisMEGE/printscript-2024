package org.example.lexer.token

import lexer.tokenRule.TokenRule
import token.DataType
import token.Token

/**
 * A token generation rule for identifying and tokenizing method calls in the source code.
 * This rule matches patterns that represent method invocations and generates tokens accordingly.
 */
class MethodCallRule : TokenRule {
    private val methodPattern = Regex("\\w+?(?=\\()")

    override fun generateToken(
        type: DataType,
        value: String,
        initialPosition: Pair<Int, Int>,
        finalPosition: Pair<Int, Int>,
    ): Token? {
        val match = methodPattern.find(value) ?: return null
        return createTokenFromMatch(type, value, initialPosition, finalPosition, match)
    }

    private fun createTokenFromMatch(
        type: DataType,
        value: String,
        initialPosition: Pair<Int, Int>,
        finalPosition: Pair<Int, Int>,
        match: MatchResult,
    ): Token {
        val (methodStartIndex, methodEndIndex) = match.range.first to match.range.last
        val updatedInitialPosition = initialPosition
        val updatedFinalPosition = Pair(initialPosition.first + methodEndIndex, finalPosition.second)
        val tokenValue = value.substring(methodStartIndex, methodEndIndex + 1)
        return Token(type, tokenValue, updatedInitialPosition, updatedFinalPosition)
    }

    override fun getTypes(): List<DataType> {
        return listOf(DataType.METHOD_CALL)
    }
}
