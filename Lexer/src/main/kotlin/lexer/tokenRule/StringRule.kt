package lexer.tokenRule

import token.DataType
import token.Token

class StringRule() : TokenRule {
    override fun generateToken(
        type: DataType,
        value: String,
        initialPosition: Pair<Int, Int>,
        finalPosition: Pair<Int, Int>,
    ): Token? {
        val newValue: String = value.substring(1, value.length - 1)

        return Token(type, newValue, initialPosition, finalPosition)
    }

    override fun getTypes(): List<DataType> {
        return listOf(DataType.STRING_VALUE)
    }
}
