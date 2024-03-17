package lexer.token

import token.DataType
import token.Token

interface TokenRule {
    fun generateToken(type : DataType, value: String, initialPosition: Pair<Int,Int>, finalPosition: Pair<Int,Int> ) : Token?

    fun getTypes() : List<DataType>
}