package org.example.lexer

import token.Token

/**
 * The `Lexer` class is responsible for converting a sequence of characters into a sequence of tokens.
 * It analyzes the input source code to identify lexical components such as keywords, identifiers, literals, and operators.
 *
 * @param rules A list of token generation rules that define how to recognize different tokens.
 */
class Lexer(private val tokenGenerator: List<RegexTokenGenerator>) : LexerInterface {
    override fun lex(
        line: String,
        numberLine: Int,
    ): List<Token> {
        val tokens = mutableListOf<Token>()
        tokenGenerator.forEach { tokenGenerator ->
            val token = tokenGenerator.generateToken(line, numberLine)
            tokens.addAll(token)
        }
        return tokens
    }
}
