package lexer.token

import token.DataType
import token.Token

class MethodCallRule : TokenRule {
    override fun generateToken(type : DataType, value: String, initialPosition: Pair<Int, Int>, finalPosition: Pair<Int, Int>): Token? {
        val methodPattern = Regex("\\w+?(?=\\()")

        val match = methodPattern.find(value)

        match?.let {
            val methodStartIndex = it.range.first
            val methodEndIndex = it.range.last
            val updatedInitialPosition = Pair(initialPosition.first, initialPosition.second)
            val updatedFinalPosition = Pair(initialPosition.first + methodEndIndex , finalPosition.second)

            val tokenValue = value.substring(methodStartIndex, methodEndIndex + 1)

            return Token(type, tokenValue, updatedInitialPosition, updatedFinalPosition)
        }

        return null
    }

    override fun getTypes(): List<DataType> {
        return listOf(DataType.METHOD_CALL)
    }


}