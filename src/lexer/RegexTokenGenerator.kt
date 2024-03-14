package lexer
import Token.DataType
import Token.Token

class RegexTokenGenerator(private val pattern : String, private val TokenType: DataType, private val isPatternLiteral : Boolean) {
    fun generateToken(line: String, numberLine : Int): Token? {
        val pattern = Regex(pattern)
        val matches = pattern.findAll(line)
           matches.forEach { matchResult ->
                val match = matchResult.value
                val start = matchResult.range.first
                val end = matchResult.range.last
               return Token(TokenType, if (isPatternLiteral) match else "", Pair(start, numberLine), end - start + 1)
           }
        return null
    }
}