package org.example.lexer

import token.DataType
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
            val generatedTokens = tokenGenerator.generateToken(line, numberLine)
            if (generatedTokens.isNotEmpty()) {
                tokens.addAll(generatedTokens)
            }
        }

        if (tokens.isEmpty()) {
            tokens.add(Token(DataType.ERROR, line, Pair(0, numberLine), Pair(line.length - 1, numberLine)))
        }
        return tokens
    }
}
