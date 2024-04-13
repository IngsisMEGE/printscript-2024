package org.example.lexer

import token.Token

interface Lexer {
    fun lex(
        line: String,
        numberLine: Int,
    ): List<Token>

    fun isLineFinished(): Boolean
}
