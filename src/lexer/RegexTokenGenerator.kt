package lexer

import lexer.token.TokenRule
import token.DataType
import token.Token


class RegexTokenGenerator(
    private val pattern: String,
    private val tokenType: DataType,
    private val isPatternLiteral: Boolean,
    private val tokenCreationException: TokenRule?
) {
    constructor(
        pattern: String,
        tokenType: DataType,
        isPatternLiteral: Boolean
    ) : this(pattern, tokenType, isPatternLiteral, null)

    fun generateToken(line: String, numberLine: Int): List<Token> {
        val tokens = mutableListOf<Token>()
        val pattern = Regex(pattern)
        val matches = pattern.findAll(line)
        matches.forEach { matchResult ->
            val match = matchResult.value
            val start = matchResult.range.first
            val end = matchResult.range.last

            if (tokenCreationException != null) {
                tokenCreationException.generateToken(tokenType, match, Pair(start, numberLine), Pair(end, numberLine))
                    ?.let { tokens.add(it) }
            } else {
                tokens.add(Token(tokenType, if (!isPatternLiteral) match else "", Pair(start, numberLine), Pair(end, numberLine)))
            }
        }
        return tokens
    }
}