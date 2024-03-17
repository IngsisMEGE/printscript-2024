package lexer.TokenGenerationRules

import token.DataType
import token.Token

class MethodCallRule : TokenRule {
    override fun generateToken(type : DataType, value: String, initialPosition: Pair<Int, Int>, finalPosition: Pair<Int, Int>): Token? {
        // Regular expression pattern to detect method calls
        val methodPattern = Regex("\\w+?(?=\\()")

        // Find the method call in the value
        val match = methodPattern.find(value)

        // If a method call is found
        match?.let {
            // Update the initial and final positions based on the match
            val methodStartIndex = it.range.first
            val methodEndIndex = it.range.last
            val updatedInitialPosition = Pair(initialPosition.first, methodStartIndex)
            val updatedFinalPosition = Pair(methodEndIndex, finalPosition.second)

            // Cut the value
            val tokenValue = value.substring(methodStartIndex, methodEndIndex + 1)

            // Return the token with updated positions
            return Token(type, tokenValue, updatedInitialPosition, updatedFinalPosition)
        }

        // If no method call is found, return null
        return null
    }

    override fun getTypes(): List<DataType> {
        return listOf(DataType.METHOD_CALL)
    }


}