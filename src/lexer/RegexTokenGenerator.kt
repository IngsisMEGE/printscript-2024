package lexer

import lexer.TokenGenerationRules.TokenRule
import token.DataType
import token.Token


class RegexTokenGenerator(
    private val pattern: String,
    private val TokenType: DataType,
    private val isPatternLiteral: Boolean,
    private val TokenCreationExceptions: TokenRule?
) {
    constructor(
        pattern: String,
        TokenType: DataType,
        isPatternLiteral: Boolean
    ) : this(pattern, TokenType, isPatternLiteral, null)

    fun generateTokens(line: String, numberLine: Int): List<Token> {
        val tokens = mutableListOf<Token>()
        val pattern = Regex(this.pattern)
        val matches = pattern.findAll(line)

        matches.forEach { matchResult ->
            val match = matchResult.value
            val start = matchResult.range.first
            val end = matchResult.range.last

            val token = if (TokenCreationExceptions != null) {
                TokenCreationExceptions.generateToken(TokenType, match, Pair(start, numberLine), Pair(end, numberLine))
            } else {
                Token(TokenType, if (!isPatternLiteral) match else "", Pair(start, numberLine), Pair(end, numberLine))
            }

            if (token != null) {
                tokens.add(token)
                ListTokenManager.removeTokenFromString(line, token)
            }
        }

        return tokens
    }





}
