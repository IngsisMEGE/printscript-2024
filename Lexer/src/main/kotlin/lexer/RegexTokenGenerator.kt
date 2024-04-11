package org.example.lexer

import lexer.TokenRegexRule
import lexer.tokenRule.TokenRule
import token.Token

/**
 * A token generator that uses regular expressions to identify and tokenize patterns in the source code.
 *
 * @property tokenRegexRule The regular expression rule used to generate tokens.
 * @property tokenCreationException An optional token rule used to generate tokens that do not match the regular expression pattern.
 */

class RegexTokenGenerator(
    private val tokenRegexRule: TokenRegexRule,
    private val tokenCreationException: TokenRule?,
) {
    constructor(
        tokenRegexRule: TokenRegexRule,
    ) : this(tokenRegexRule, null)

    private val pattern = Regex(tokenRegexRule.getPattern())

    /**
     * Generates tokens based on the regular expression rule.
     *
     * @param line The line of source code to tokenize.
     * @param numberLine The line number of the source code.
     * @return A list of tokens generated from the regular expression rule.
     */
    fun generateToken(
        line: String,
        numberLine: Int,
    ): List<Token> {
        val tokens = mutableListOf<Token>()
        val matches = pattern.findAll(line)
        matches.forEach { matchResult ->
            val match = matchResult.value
            val start = matchResult.range.first
            val end = matchResult.range.last

            tokenCreationException?.generateToken(tokenRegexRule.getType(), match, Pair(start, numberLine), Pair(end, numberLine))?.let {
                tokens.add(it)
            }
                ?: tokens.add(
                    Token(
                        tokenRegexRule.getType(),
                        if (!tokenRegexRule.isPatternLiteral()) match else "",
                        Pair(start, numberLine),
                        Pair(end, numberLine),
                    ),
                )
        }
        return tokens
    }

    fun doesItMatch(line: String): Boolean {
        return pattern.containsMatchIn(line)
    }
}
