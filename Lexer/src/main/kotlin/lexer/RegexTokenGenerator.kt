package org.example.lexer

import lexer.tokenRule.TokenRule
import token.DataType
import token.Token

/**
 * Generates tokens based on regular expressions. This class can handle both simple pattern matching and more complex scenarios
 * where additional processing is required to determine the token type or value.
 *
 * @param pattern The regular expression pattern used for matching tokens in the input string.
 * @param tokenType The type of token to generate when a match is found.
 * @param isPatternLiteral A flag indicating whether the matched pattern should be used as the literal token value.
 * @param tokenCreationException An optional rule for cases where token creation requires special handling beyond simple pattern matching.
 */

class RegexTokenGenerator(
    private val pattern: String,
    private val tokenType: DataType,
    private val isPatternLiteral: Boolean,
    private val tokenCreationException: TokenRule?,
) {
    constructor(
        pattern: String,
        tokenType: DataType,
        isPatternLiteral: Boolean,
    ) : this(pattern, tokenType, isPatternLiteral, null)

    /**
     * Parses the given input line and generates a list of tokens according to the defined regular expression patterns.
     *
     * @param line The input line of code to tokenize.
     * @param numberLine The line number in the source code, used for position tracking.
     * @return A list of tokens identified in the input line.
     */
    fun generateToken(
        line: String,
        numberLine: Int,
    ): List<Token> {
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
